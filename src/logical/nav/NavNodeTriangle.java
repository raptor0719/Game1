package logical.nav;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NavNodeTriangle {
	private final List<NavNodeTriangle> navNodes;
	private final List<Point> points;

	public NavNodeTriangle(final NavNodeTriangle c1, final NavNodeTriangle c2, final NavNodeTriangle c3, final Point p1, final Point p2, final Point p3) {
		navNodes = new LinkedList<NavNodeTriangle>();
		points = new LinkedList<Point>();

		navNodes.addAll(Arrays.asList(c1, c2, c3));
		points.addAll(Arrays.asList(p1, p2, p3));
	}

	public Point getPoint(final int index) {
		if (index >= points.size())
			return null;

		return points.get(index);
	}

	public NavNodeTriangle getNavNode(final int index) {
		if (index >= navNodes.size())
			return null;

		return navNodes.get(index);
	}
}
