package util.geometry;

import util.structures.ValuePair;

public class LineSegment {
	private final Point a;
	private final Point b;

	public LineSegment(final Point a, final Point b) {
		this.a = a;
		this.b = b;
	}

	public ValuePair<Point, Point> getPoints() {
		return new ValuePair<Point, Point>(a, b);
	}

	public double getLength() {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)

		final int xDiff = b.getX() - a.getX();
		final int yDiff = b.getY() - a.getY();

		final int xDiffSquared = xDiff * xDiff;
		final int yDiffSquared = yDiff * yDiff;

		final double distance = Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
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
}
