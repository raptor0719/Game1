package logical.nav;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NavNodeTriangle {
	private final NavNodeTriangle[] navNodes;
	private final Point[] points;

	protected NavNodeTriangle(final NavNodeTriangle[] navNodes, final Point[] points) {
		this.navNodes = navNodes;
		this.points = points;
	}

	public Point[] getPoints() {
		final Point[] pointsCpy = new Point[points.length];
		System.arraycopy(points, 0, pointsCpy, 0, points.length);
		return pointsCpy;
	}

	public NavNodeTriangle[] getNavNode(final int index) {
		final NavNodeTriangle[] navNodesCpy = new NavNodeTriangle[points.length];
		System.arraycopy(navNodes, 0, navNodesCpy, 0, navNodes.length);
		return navNodesCpy;
	}
	
	public static class NavNodeTriangleBuilder {
		private NavNodeTriangle[] navNodes;
		private Point[] points;
		
		public NavNodeTriangleBuilder navNodes(final NavNodeTriangle... navNodes) {
			this.navNodes = navNodes;
			return this;
		}
		
		public NavNodeTriangleBuilder points(final Point... points) {
			this.points = points;
			return this;
		}
		
		public NavNodeTriangle build() {
			return new NavNodeTriangle(navNodes, points);
		}
	}
}
