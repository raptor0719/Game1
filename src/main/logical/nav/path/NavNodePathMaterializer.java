package logical.nav.path;

import java.util.ArrayList;
import java.util.List;

import logical.nav.graph.NavEdge;
import logical.nav.graph.NavNode;
import logical.nav.path.api.IPathMaterializer;
import util.geometry.LineSegment;
import util.geometry.Point;
import util.geometry.Triangle;
import util.structures.ValuePair;

public class NavNodePathMaterializer implements IPathMaterializer<NavNode> {
	@Override
	public List<Point> materialize(final List<NavNode> nodePath, final Point start, final Point end) {
		/*
		 * At this point we can assume the path has been sanitized.
		 * That means, each node in the path will only have ONE connection
		 * and that connection will lead to the next node in the path.
		 *
		 * We can also assume that a path WILL exist. If one doesnt, it is in error.
		 */

		if (nodePath == null || nodePath.size() < 2)
			throw new RuntimeException("Path materialization requires a path with at least 2 nodes.");

		NavNode current = nodePath.remove(0);

		if (!pointsIsInTriangle(current.getData(), start))
			throw new RuntimeException("Start point is NOT within the starting node of the path.");

		final List<Point> path = new ArrayList<Point>(nodePath.size() + 2);
		path.add(start);

		while (!nodePath.isEmpty()) {
			final NavNode next = nodePath.remove(0);

			final ValuePair<LineSegment, LineSegment> adjEdges = findAdjacentEdges(current, next);
			System.out.println("Adjacent edge is " + adjEdges);
			final LineSegment adjSegment = findSegment(adjEdges.getValue1(), adjEdges.getValue2());
			System.out.println("Resolved segment is " + adjSegment);
			final Point mid = getMidpoint(adjSegment);
			System.out.println("Midpoint is " + mid);

			path.add(mid);

			current = next;
		}

		path.add(end);

		return path;
	}

	private boolean pointsIsInTriangle(final Triangle t, final Point p) {
		final Triangle t1 = new Triangle(t.getPoints()[0], t.getPoints()[1], p);
		final Triangle t2 = new Triangle(t.getPoints()[1], t.getPoints()[2], p);
		final Triangle t3 = new Triangle(t.getPoints()[2], t.getPoints()[0], p);
		final double area = t1.getArea() + t2.getArea() + t3.getArea();
		return t.getArea() == area;
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

	private ValuePair<LineSegment, LineSegment> findAdjacentEdges(final NavNode a, final NavNode b) {
		for (final NavEdge e : a.getConnections())
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
}
