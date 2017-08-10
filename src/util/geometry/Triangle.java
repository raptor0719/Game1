package util.geometry;

public class Triangle {

	private final Point[] points;

	public Triangle(final Point p0, final Point p1, final Point p2) {
		points = new Point[3];

		points[0] = p0;
		points[1] = p1;
		points[2] = p2;
	}

	public Point[] getPoints() {
		final Point[] tmp = new Point[3];
		System.arraycopy(points, 0, tmp, 0, 3);
		return tmp;
	}
}
