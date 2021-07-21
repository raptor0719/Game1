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
		final DoubleVector lineVector = new DoubleVector(ls.getEnd().getX() - ls.getStart().getX(), ls.getEnd().getY() - ls.getStart().getY());
		final DoubleVector vectorToPoint = new DoubleVector(this.getX() - ls.getStart().getX(), this.getY() - ls.getStart().getY());

		double dotProduct = lineVector.unitVector().dot(vectorToPoint.scale(1.0/lineVector.getMagnitude()));
		if (dotProduct < 0.0)
			dotProduct = 0.0;
		else if (dotProduct > 1.0)
			dotProduct = 1.0;

		final DoubleVector nearest = lineVector.scale(dotProduct);
		return Point.distanceTo(nearest.getX(), nearest.getY(), vectorToPoint.getX(), vectorToPoint.getY());
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

	public static double distanceTo(final double px1, final double py1, final double px2, final double py2) {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)
		final double xDiff = px2 - px1;
		final double yDiff = py2 - py1;

		final double xDiffSquared = xDiff * xDiff;
		final double yDiffSquared = yDiff * yDiff;

		final double distance = Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
	}

	public static double distanceTo(final int px, final int py, final int l1x, final int l1y, final int l2x, final int l2y) {
		return (new Point(px, py)).distanceTo(new LineSegment(new Point(l1x, l1y), new Point(l2x, l2y)));
	}

	public static boolean pointIsOnLineSegment(final Point c, final ILineSegment l) {
		final IPoint a = l.getStart();
		final IPoint b = l.getEnd();

		if (a.equals(c) || b.equals(c))
			return true;

		final double lLengthSquared = l.getLength() * l.getLength();

		final int cross = crossProduct(a, b, c);
		final int dot = dotProduct(a, b, c);

		return (cross == 0 && dot > 0 && dot < lLengthSquared);
	}

	public static int crossProduct(final IPoint a, final IPoint b, final IPoint c) {
		return ((c.getY() - a.getY()) * (b.getX() - a.getX())) - ((c.getX() - a.getX()) * (b.getY() - a.getY()));
	}

	public static int dotProduct(final IPoint a, final IPoint b, final IPoint c) {
		return ((c.getX() - a.getX()) * (b.getX() - a.getX())) + ((c.getY() - a.getY()) * (b.getY() - a.getY()));
	}
}
