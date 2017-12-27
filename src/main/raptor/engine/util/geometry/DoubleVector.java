package raptor.engine.util.geometry;

public class DoubleVector {
	// TODO: This is a vector that uses doubles instead of integers
	// The standard vector should honestly do this, but this would
	// require a LOT of testing on how it is being used currently.
	// Lets stick with this for now.

	private final double x;
	private final double y;

	public DoubleVector(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public DoubleVector unitVector() {
		return getUnitVector(this);
	}

	public DoubleVector scale(final double scale) {
		return new DoubleVector(x * scale, y * scale);
	}

	@Override
	public String toString() {
		return "DoubleVector:[x=" + x + ", y=" + y + "]";
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (!(o instanceof DoubleVector))
			return false;
		final DoubleVector o2 = (DoubleVector)o;
		if (o2.getX() != getX())
			return false;
		if (o2.getY() != getY())
			return false;
		return true;
	}

	/* STATIC METHODS */

	/**
	 * @param start - the reference point of the vector
	 * @param end - the direction the vector is to face
	 * @return a unit vector pointing toward end from start
	 */
	public static DoubleVector unitVectorTowardPoint(final Point start, final Point end) {
		final int x = end.getX() - start.getX();
		final int y = end.getY() - start.getY();

		return getUnitVector(new DoubleVector(x, y));
	}

	/* INTERNALS */

	private static DoubleVector getUnitVector(final DoubleVector v) {
		final double magnitude = calculateMagnitude(v);

		if (magnitude == 0.0)
			return new DoubleVector(0, 0);

		final double unitX = v.getX() / magnitude;
		final double unitY = v.getY() / magnitude;

		return new DoubleVector(unitX, unitY);
	}

	private static double calculateMagnitude(final DoubleVector v) {
		final double xSquared = v.getX() * v.getX();
		final double ySquared = v.getY() * v.getY();

		return Math.sqrt(xSquared + ySquared);
	}
}
