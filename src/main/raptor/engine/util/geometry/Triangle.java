package raptor.engine.util.geometry;

public class Triangle {
	private final Point[] points;
	private final LineSegment[] lines;
	private final double area;

	public Triangle(final Point p0, final Point p1, final Point p2) {
		points = new Point[3];
		points[0] = p0;
		points[1] = p1;
		points[2] = p2;

		lines = new LineSegment[3];
		lines[0] = new LineSegment(points[0], points[1]);
		lines[1] = new LineSegment(points[1], points[2]);
		lines[2] = new LineSegment(points[2], points[0]);

		this.area = calculateArea(p0, p1, p2);
	}

	public Point[] getPoints() {
		final Point[] tmp = new Point[3];
		System.arraycopy(points, 0, tmp, 0, 3);
		return tmp;
	}

	public LineSegment[] getLines() {
		final LineSegment[] tmp = new LineSegment[3];
		System.arraycopy(lines, 0, tmp, 0, 3);
		return tmp;
	}

	public double getArea() {
		return area;
	}

	public boolean isIntersectedByLine(final LineSegment ls) {
		for (final LineSegment line : getLines())
			if (line.intersectsWith(ls))
				return true;
		return false;
	}

	public boolean containsPoint(final Point p) {
		final double area1 = Triangle.calculateArea(getPoints()[0], getPoints()[1], p);
		final double area2 = Triangle.calculateArea(getPoints()[1], getPoints()[2], p);
		final double area3 = Triangle.calculateArea(getPoints()[2], getPoints()[0], p);
		final double area = area1 + area2 + area3;
		return getArea() == area;
	}

	@Override
	public String toString() {
		return "Triangle:[p0=" + points[0] + ",p1=" + points[1] + ",p2=" + points[2] + "]";
	}

	/* STATIC */

	public static boolean containsPoint(final int t1x, final int t1y, final int t2x, final int t2y, final int t3x, final int t3y, final int px, final int py) {
		final double area1 = Triangle.calculateArea(t1x, t1y, t2x, t2y, px, py);
		final double area2 = Triangle.calculateArea(t2x, t2y, t3x, t3y, px, py);
		final double area3 = Triangle.calculateArea(t3x, t3y, t1x, t1y, px, py);
		return Triangle.calculateArea(t1x, t1y, t2x, t2y, t3x, t3y) == (area1 + area2 + area3);
	}

	public static double calculateArea(final Point a, final Point b, final Point c) {
		final int term1 = a.getX() * (b.getY() - c.getY());
		final int term2 = b.getX() * (c.getY() - a.getY());
		final int term3 = c.getX() * (a.getY() - b.getY());
		return Math.abs((term1 + term2 + term3)/2D);
	}

	public static double calculateArea(final int ax, final int ay, final int bx, final int by, final int cx, final int cy) {
		final int term1 = ax * (by - cy);
		final int term2 = bx * (cy - ay);
		final int term3 = cx * (ay - by);
		return Math.abs((term1 + term2 + term3)/2D);
	}
}
