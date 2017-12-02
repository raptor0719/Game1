package util.geometry;

public class Line {
	private final Point a;
	private final Point b;
	
	public Line(final Point a, final Point b) {
		this.a = a;
		this.b = b;
	}
	
	public Point[] getPoints() {
		final Point[] points = new Point[2];
		points[0] = a;
		points[1] = b;
		return points;
	}
}
