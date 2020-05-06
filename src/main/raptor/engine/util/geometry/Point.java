package raptor.engine.util.geometry;

import raptor.engine.util.geometry.api.ILineSegment;
import raptor.engine.util.geometry.api.IPoint;

public class Point implements IPoint {
	private int x;
	private int y;

	public Point(final Vector v) {
		this(v.getX(), v.getY());
	}

	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setX(final int newX) {
		x = newX;
	}

	public void setY(final int newY) {
		y = newY;
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

	public double distanceTo(final double bX, final double bY) {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)
		final double xDiff = bX - x;
		final double yDiff = bY - y;

		final double xDiffSquared = xDiff * xDiff;
		final double yDiffSquared = yDiff * yDiff;

		final double distance = Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
	}

	public double distanceTo(final LineSegment ls) {
		final float lsLength = ls.getLength();

		if (lsLength <= 0)
			return distanceTo(ls.getPoints().getValue1());

		final Point lsStart = ls.getPoints().getValue1();
		final Point lsEnd = ls.getPoints().getValue2();
		final float t = ((x - lsStart.getX())*(lsEnd.getX() - lsStart.getX()) + (y - lsStart.getY())*(lsEnd.getY() - lsStart.getY())) / lsLength;
		final float tConstrained = Math.max(0 , Math.min(1, t));

		return Math.sqrt(distanceTo(lsStart.getX() + tConstrained*(lsEnd.getX() - lsStart.getX()), lsStart.getY() + tConstrained*(lsEnd.getY() - lsStart.getY())));
	}

	@Override
	public boolean isOnLineSegment(final ILineSegment ls) {
		return pointIsOnLineSegment(this, ls);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof IPoint))
			return false;

		final IPoint p = (IPoint)o;

		if (p.getX() == x && p.getY() == y)
			return true;

		return false;
	}

	@Override
	public String toString() {
		return "Point:[x=" + x + ",y=" + y + "]";
	}

	/* STATIC */

	public static double distanceTo(final int px, final int py, final int l1x, final int l1y, final int l2x, final int l2y) {
		final float lsLength = LineSegment.length(l1x, l1y, l2x, l2y);

		if (lsLength <= 0)
			return LineSegment.length(l1x, l1y, px, py);

		final float t = ((px - l1x)*(l2x - l1x) + (py - l1y)*(l2y - l1y)) / lsLength;
		final float tConstrained = Math.max(0 , Math.min(1, t));

		return Math.sqrt(LineSegment.length(l1x + tConstrained*(l2x - l1x), l1y + tConstrained*(l2y - l1y), px, py));
	}

	/* INTERNALS */

	private boolean pointIsOnLineSegment(final Point c, final ILineSegment l) {
		final IPoint a = l.getStart();
		final IPoint b = l.getEnd();

		if (a.equals(c) || b.equals(c))
			return true;

		final double lLengthSquared = l.getLength() * l.getLength();

		final int cross = crossProduct(a, b, c);
		final int dot = dotProduct(a, b, c);

		return (cross == 0 && dot > 0 && dot < lLengthSquared);
	}

	private int crossProduct(final IPoint a, final IPoint b, final IPoint c) {
		return ((c.getY() - a.getY()) * (b.getX() - a.getX())) - ((c.getX() - a.getX()) * (b.getY() - a.getY()));
	}

	private int dotProduct(final IPoint a, final IPoint b, final IPoint c) {
		return ((c.getX() - a.getX()) * (b.getX() - a.getX())) + ((c.getY() - a.getY()) * (b.getY() - a.getY()));
	}
}
