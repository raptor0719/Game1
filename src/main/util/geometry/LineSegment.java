package util.geometry;

import util.structures.ValuePair;

public class LineSegment {
	private final Point a;
	private final Point b;
	private final float length;

	private final Vector aToB;

	public LineSegment(final Point a, final Point b) {
		this.a = a;
		this.b = b;
		this.length = calculateLength(a, b);
		this.aToB = calculateAToB(a, b);
	}

	public ValuePair<Point, Point> getPoints() {
		return new ValuePair<Point, Point>(a, b);
	}

	public float getLength() {
		return length;
	}

	/**
	 * @param ls - the intersecting line segment
	 * @return The single point of intersection between the calling object and parameter ls or
	 * null if there is no such single point. (Single point is clarified here because multiple
	 * points of intersection will occur if the 2 segments are collinear and overlap)
	 */
	public Point getIntersectionPoint(final LineSegment ls) {
		/*
		 * p = this starting point
		 * q = ls starting point
		 *
		 * r = p.aToB
		 * s = q.aToB
		 *
		 * t = scalar of r
		 * u = scalar of s
		 */
		final Vector p = Vector.toVector(a);
		final Vector q = Vector.toVector(ls.a);
		final Vector r = aToB;
		final Vector s = ls.aToB;

		final int rCrossS = r.cross(s);
		final Vector qMinusP = q.minus(p);
		final int qMinPCrossR = (qMinusP).cross(r);

		if (rCrossS == 0 && qMinPCrossR == 0) // is collinear
			return null;
		else if (rCrossS == 0 && qMinPCrossR != 0) // is parallel
			return null;
		else if (rCrossS != 0) { // POSSIBLY intersects
			final float t = (float)qMinusP.cross(s) / rCrossS;
			final float u = (float)qMinPCrossR / rCrossS;

			if ((t >= 0 && t <= 1) && (u >= 0 && u <= 1)) { // intersects!
				final int intersectX = p.getX() + round(t * r.getX());
				final int intersectY = p.getY() + round(t * r.getY());

				return new Point(intersectX, intersectY);
			}
		}

		// Non-collinear, non-parallel, and does not intersect
		return null;
	}

	/**
	 * This is the same as calling {@link #getIntersectionPoint(LineSegment)} != null
	 *
	 * @param ls - the line segment with which to check intersection
	 * @return True if the calling object and the parameter ls have a point of intersection,
	 * false if they do not have a point of intersection.
	 */
	public boolean intersectsWith(final LineSegment ls) {
		return getIntersectionPoint(ls) != null;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof LineSegment))
			return false;

		final LineSegment ls = (LineSegment)o;

		// case where points match up
		if (a.equals(ls.a)) {
			if (b.equals(ls.b))
				return true;
			return false;
		}

		// case where points are reversed
		if (a.equals(ls.b)) {
			if (b.equals(ls.a))
				return true;
			return false;
		}

		return false;
	}

	@Override
	public String toString() {
		return "LineSegment:[a=" + a + ",b=" + b + "]";
	}

	/* INTERNALS */

	private float calculateLength(final Point a, final Point b) {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)

		final int xDiff = b.getX() - a.getX();
		final int yDiff = b.getY() - a.getY();

		final int xDiffSquared = xDiff * xDiff;
		final int yDiffSquared = yDiff * yDiff;

		final float distance = (float)Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
	}

	private Vector calculateAToB(final Point a, final Point b) {
		return new Vector(b.getX() - a.getX(), b.getY() - a.getY());
	}

	private int round(final float f) {
		return Math.round(f);
	}
}
