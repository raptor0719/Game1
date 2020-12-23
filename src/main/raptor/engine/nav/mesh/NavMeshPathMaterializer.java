package raptor.engine.nav.mesh;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.nav.api.IPathMaterializer;
import raptor.engine.nav.mesh.graph.NavMeshEdge;
import raptor.engine.nav.mesh.graph.NavMeshNode;
import raptor.engine.util.ValuePair;
import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Polygon;
import raptor.engine.util.geometry.Triangle;

public class NavMeshPathMaterializer implements IPathMaterializer<NavMeshNode> {
	private final List<Polygon> areaBounds;

	public NavMeshPathMaterializer(final List<Polygon> areaBounds) {
		this.areaBounds = areaBounds;
	}

	@Override
	public List<Point> materialize(final List<NavMeshNode> nodePath, final Point start, final Point end) {
		if (nodePath == null || nodePath.size() < 2)
			throw new RuntimeException("Path materialization requires a path with at least 2 nodes.");

		if (!pointsIsInTriangle(nodePath.get(0).getData(), start))
			throw new RuntimeException("Start point is NOT within the head node of the path.");
		else if (!pointsIsInTriangle(nodePath.get(nodePath.size()-1).getData(), end))
			throw new RuntimeException("End point is NOT within the tail node of the path.");

		if (nodePath.size() == 2)
			return materializePathWith2Nodes(nodePath.get(0), nodePath.get(1), start, end);

		/*	a) Create a line segment from the start point to the end point called S
				i) As a finish fast catch, if S does NOT intersect any blocking nodes, simply add the starting and ending points to the path and return the path
			b) Add the starting point to the path
			c) Remove the head of the path and set it to previous
			d) Remove the head of the path and set it to current
			e) Until there are no more nodes in the path
				i) Remove the head of the path and set it to next
				ii) Create a line segment from the tail of the current path to end, if this segment does not intersect any out of bounds area, than break the loop
				iii) Set A1 to the adj edge between previous and current
				iv) If a point along A1 matches the tail of the current path, use this point
				v) Set A2 to the adj edge between current and next
				vi) Set H to the point along A2 that minimizes the distance to end (either endpoints or the midpoint)
				vii) Get the point along A1 that minimizes the total distance from the tail of the current to H
					- Create line L from the tail of the current path to H, if this line does not intersect any out of bounds areas, give the intersection of L and A1
					- Else, choose between: either endpoint or the midpoint
			f) If we still have 2 nodes to work with and we dont have a straight shot to the end, then compute the last point along the path
			g) Add the ending point to the path
			h) Return the path
		 */

		// If there is no unpassable terrain between the start and end, then it is a straight shot
		//  and we can finish fast here
		final LineSegment s = new LineSegment(start, end);
		boolean intersectsOOB = false;
		for (final Polygon p : areaBounds) {
			for (final LineSegment pSeg : p.getLines()) {
				if (pSeg.intersectsWith(s)) {
					intersectsOOB = true;
					break;
				}
			}
			if (intersectsOOB)
				break;
		}

		if (!intersectsOOB) {
			final List<Point> path = new ArrayList<Point>(2);
			path.add(start);
			path.add(end);
			return path;
		}

		final List<Point> path = new ArrayList<Point>(nodePath.size()+2);
		path.add(start);

		NavMeshNode previous = nodePath.remove(0);
		NavMeshNode current = nodePath.remove(0);
		while (!nodePath.isEmpty()) {
			final NavMeshNode next = nodePath.remove(0);

			{
				final LineSegment lineToEnd = new LineSegment(path.get(path.size()-1), end);

				if (!lineGoesOutOfBounds(lineToEnd)) {
					previous = null;
					current = null;
					break;
				}
			}

			final LineSegment A1 = getAdjLineSegment(previous, current);
			if (path.get(path.size()-1).isOnLineSegment(A1)) {
				previous = current;
				current = next;
				continue;
			}

			final LineSegment A2 = getAdjLineSegment(current, next);
			final Point H = getPointOnLineThatMinimizesDistance(A2, end, null);

			final LineSegment L = new LineSegment(path.get(path.size()-1), H);
			Point newPoint;
			if (!lineGoesOutOfBounds(L)) {
				newPoint = L.getIntersectionPoint(A1);

				if (newPoint == null)
					throw new RuntimeException("An intersection should exists between these lines: " + L + " and " + A1);
			} else {
				newPoint = getPointOnLineThatMinimizesDistance(A1, path.get(path.size()-1), H);
			}

			path.add(newPoint);

			if (lastTwoPointsAreEqual(path))
				path.remove(path.size()-1);

			previous = current;
			current = next;
		}

		// if we still have 2 more nodes to process AND we don't have a straight shot to the end
		if (previous != null && current != null && lineGoesOutOfBounds(new LineSegment(path.get(path.size()-1), end)))
			path.add(getPointOnLineThatMinimizesDistance(getAdjLineSegment(previous, current), end, path.get(path.size()-1)));

		path.add(end);

		return path;
	}

	private LineSegment getAdjLineSegment(final NavMeshNode node1, final NavMeshNode node2) {
		final ValuePair<LineSegment, LineSegment> adjEdges = findAdjacentEdges(node1, node2);
		final LineSegment adjLine = findSegment(adjEdges.getValue1(), adjEdges.getValue2());
		return adjLine;
	}

	private boolean lineGoesOutOfBounds(final LineSegment ls) {
		for (final Polygon p : areaBounds)
			for (final LineSegment pSeg : p.getLines())
				if (pSeg.intersectsWithExcludingEndpoints(ls))
					return true;

		return false;
	}

	private List<Point> materializePathWith2Nodes(final NavMeshNode startNode, final NavMeshNode endNode, final Point start, final Point end) {
		final List<Point> path = new ArrayList<Point>(3);

		path.add(start);
		path.add(end);

		final LineSegment s = new LineSegment(start, end);
		final ValuePair<LineSegment, LineSegment> adjEdges = findAdjacentEdges(startNode, endNode);
		final LineSegment adjLine = findSegment(adjEdges.getValue1(), adjEdges.getValue2());

		for (final Polygon p : areaBounds) {
			for (final LineSegment pSeg : p.getLines()) {
				if (pSeg.intersectsWith(s)) {
					path.add(1, getPointOnLineThatMinimizesDistance(adjLine, start, end));
					return path;
				}
			}
		}

		final Point intersection = s.getIntersectionPoint(adjLine);

		path.add(1, intersection);

		return path;
	}

	private boolean lastTwoPointsAreEqual(final List<Point> path) {
		final Point last = path.get(path.size()-1);
		final Point secondLast = path.get(path.size()-2);
		return last.equals(secondLast);
	}

	private Point getPointOnLineThatMinimizesDistance(final LineSegment ls, final Point p1, final Point optional) {
		final Point point1 = ls.getPoints().getValue1();
		final Point point2 = ls.getPoints().getValue2();
		final Point midpoint = getMidpoint(ls);

		final float distFromPoint1 = point1.distanceTo(p1) + ((optional == null) ? 0 : point1.distanceTo(optional));
		final float distFromPoint2 = point2.distanceTo(p1) + ((optional == null) ? 0 : point2.distanceTo(optional));
		final float distFromMidpoint = midpoint.distanceTo(p1) + ((optional == null) ? 0 : midpoint.distanceTo(optional));

		Point pointToAdd = null;
		if (distFromPoint1 <= distFromPoint2) {
			if (distFromPoint1 <= distFromMidpoint) {
				pointToAdd = point1;
			} else {
				pointToAdd = midpoint;
			}
		} else {
			if (distFromPoint2 <= distFromMidpoint) {
				pointToAdd = point2;
			} else {
				pointToAdd = midpoint;
			}
		}

		return pointToAdd;
	}

	private boolean pointsIsInTriangle(final Triangle t, final Point p) {
		final Triangle t1 = new Triangle(t.getPoints()[0], t.getPoints()[1], p);
		final Triangle t2 = new Triangle(t.getPoints()[1], t.getPoints()[2], p);
		final Triangle t3 = new Triangle(t.getPoints()[2], t.getPoints()[0], p);
		final double area = t1.getArea() + t2.getArea() + t3.getArea();
		return t.getArea() == area;
	}

	private ValuePair<LineSegment, LineSegment> findAdjacentEdges(final NavMeshNode a, final NavMeshNode b) {
		for (final NavMeshEdge e : a.getConnections())
			if (e.getDestination().equals(b))
				return e.getAdjacentEdges();
		throw new RuntimeException("Consecutive nodes in path had no adjacent edge.");
	}

	private LineSegment findSegment(final LineSegment l1, final LineSegment l2) {
		// Cases where 1 segment lies completely in the other
		if (pointIsOnLineSegment(l1.getPoints().getValue1(), l2) && pointIsOnLineSegment(l1.getPoints().getValue2(), l2))
			return l1;
		else if (pointIsOnLineSegment(l2.getPoints().getValue1(), l1) && pointIsOnLineSegment(l2.getPoints().getValue2(), l1))
			return l2;

		// else find the 2 inner points
		final Point p1 = (pointIsOnLineSegment(l1.getPoints().getValue1(), l2)) ? l1.getPoints().getValue1() : l1.getPoints().getValue2();
		final Point p2 = (pointIsOnLineSegment(l2.getPoints().getValue1(), l1)) ? l2.getPoints().getValue2() : l2.getPoints().getValue1();

		// those points form the adjacent line segment
		return new LineSegment(p1, p2);
	}

	private boolean pointIsOnLineSegment(final Point c, final LineSegment l) {
		final Point a = l.getPoints().getValue1();
		final Point b = l.getPoints().getValue2();

		if (a.equals(c) || b.equals(c))
			return true;

		final double lLengthSquared = l.getLength() * l.getLength();

		final int cross = crossProduct(a, b, c);
		final int dot = dotProduct(a, b, c);

		return (cross == 0 && dot > 0 && dot < lLengthSquared);
	}

	private int crossProduct(final Point a, final Point b, final Point c) {
		return ((c.getY() - a.getY()) * (b.getX() - a.getX())) - ((c.getX() - a.getX()) * (b.getY() - a.getY()));
	}

	private int dotProduct(final Point a, final Point b, final Point c) {
		return ((c.getX() - a.getX()) * (b.getX() - a.getX())) + ((c.getY() - a.getY()) * (b.getY() - a.getY()));
	}

	private Point getMidpoint(final LineSegment l) {
		final int x1 = l.getPoints().getValue1().getX();
		final int y1 = l.getPoints().getValue1().getY();
		final int x2 = l.getPoints().getValue2().getX();
		final int y2 = l.getPoints().getValue2().getY();

		final int x = (x1 + x2)/2;
		final int y = (y1 + y2)/2;

		return new Point(x, y);
	}
}
