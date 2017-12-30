package raptor.engine.util.geometry;

public class Triangle {
	private final Point[] points;
	private final double area;

	public Triangle(final Point p0, final Point p1, final Point p2) {
		points = new Point[3];

		points[0] = p0;
		points[1] = p1;
		points[2] = p2;

		this.area = calcArea(p0, p1, p2);
	}

	public Point[] getPoints() {
		final Point[] tmp = new Point[3];
		System.arraycopy(points, 0, tmp, 0, 3);
		return tmp;
	}

	public LineSegment[] getLines() {
		final LineSegment[] lines = new LineSegment[3];
		lines[0] = new LineSegment(points[0], points[1]);
		lines[1] = new LineSegment(points[1], points[2]);
		lines[2] = new LineSegment(points[2], points[0]);
		return lines;
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
		final Triangle t1 = new Triangle(getPoints()[0], getPoints()[1], p);
		final Triangle t2 = new Triangle(getPoints()[1], getPoints()[2], p);
		final Triangle t3 = new Triangle(getPoints()[2], getPoints()[0], p);
		final double area = t1.getArea() + t2.getArea() + t3.getArea();
		return getArea() == area;
	}

	@Override
	public String toString() {
		return "Triangle:[p0=" + points[0] + ",p1=" + points[1] + ",p2=" + points[2] + "]";
	}

	/* INTERNALS */

	private double calcArea(final Point a, final Point b, final Point c) {
		final int term1 = a.getX() * (b.getY() - c.getY());
		final int term2 = b.getX() * (c.getY() - a.getY());
		final int term3 = c.getX() * (a.getY() - b.getY());
		return Math.abs((term1 + term2 + term3)/2D);
	}
}