package util.geometry;

public class Point {
	private final int x;
	private final int y;

	public Point(final Vector v) {
		this(v.getX(), v.getY());
	}

	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float distanceTo(final Point b) {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)
		final int xDiff = b.getX() - x;
		final int yDiff = b.getY() - y;

		final int xDiffSquared = xDiff * xDiff;
		final int yDiffSquared = yDiff * yDiff;

		final float distance = (float)Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof Point))
			return false;

		final Point p = (Point)o;

		if (p.getX() == this.x && p.getY() == this.y)
			return true;

		return false;
	}

	@Override
	public String toString() {
		return "Point:[x=" + x + ",y=" + y + "]";
	}

	/* STATIC METHODS */
	public static Point toPoint(final Vector v) {
		return new Point(v);
	}
}
