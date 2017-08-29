package logical.nav;

import util.geometry.Triangle;

public class NavNodeTriangle {
	private final NavNodeTriangle[] navNodes;
	private final Triangle triangle;

	protected NavNodeTriangle(final NavNodeTriangle[] navNodes, final Triangle triangle) {
		this.navNodes = navNodes;
		this.triangle = triangle;
	}

	public Triangle getTriangle() {
		return triangle;
	}

	/**
	 * Create a copy of the internal array so it is read only
	 */
	public NavNodeTriangle[] getNavNode(final int index) {
		final NavNodeTriangle[] navNodesCpy = new NavNodeTriangle[navNodes.length];
		System.arraycopy(navNodes, 0, navNodesCpy, 0, navNodes.length);
		return navNodesCpy;
	}

	public static class NavNodeTriangleBuilder {
		private NavNodeTriangle[] navNodes;
		private Triangle triangle;

		public NavNodeTriangleBuilder navNodes(final NavNodeTriangle... navNodes) {
			this.navNodes = navNodes;
			return this;
		}

		public NavNodeTriangleBuilder triangle(final Triangle triangle) {
			this.triangle = triangle;
			return this;
		}

		public NavNodeTriangle build() {
			return new NavNodeTriangle(navNodes, triangle);
		}
	}
}
