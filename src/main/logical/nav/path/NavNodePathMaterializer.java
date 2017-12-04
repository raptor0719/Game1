package logical.nav.path;

import java.util.List;

import logical.nav.graph.NavEdge;
import logical.nav.graph.NavNode;
import logical.nav.path.api.IPathMaterializer;
import util.geometry.LineSegment;
import util.geometry.Point;
import util.structures.ValuePair;

public class NavNodePathMaterializer implements IPathMaterializer<NavNode> {
	@Override
	public List<Point> materialize(final List<NavNode> nodePath) {
		/*
		 * TODO
		 * Problem right now is we still have to search through each NavNode's connections
		 * to find out which connection leads to the next node in the path. What we should
		 * do is create a modified NavNode for a completed path that only has 1 connection.
		 */


		return null;
	}

	private ValuePair<LineSegment, LineSegment> findAdjacentEdges(final NavNode a, final NavNode b) {
		for (final NavEdge e : a.getConnections())
			if (e.getDestination().equals(b))
				return e.getAdjacentEdges();
		return null;
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
