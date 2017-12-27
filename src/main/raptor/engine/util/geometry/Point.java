package raptor.engine.util.geometry;

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

	public boolean isOnLineSegment(final LineSegment ls) {
		return pointIsOnLineSegment(this, ls);
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

	/* INTERNALS */

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
