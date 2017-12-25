package util.geometry;

public class Vector {
	private final int x;
	private final int y;

	private final double magnitude;

	public Vector (final Point p) {
		this(p.getX(), p.getY());
	}

	public Vector(final int x, final int y) {
		this.x = x;
		this.y = y;
		this.magnitude = calculateMagnitude(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public int cross(final Vector b) {
		return x*b.getY() - y*b.getX();
	}

	public Vector minus(final Vector b) {
		return new Vector(x - b.getX(), y - b.getY());
	}

	public DoubleVector unitVector() {
		return new DoubleVector(x, y).unitVector();
	}

	@Override
	public String toString() {
		return "Vector:[x=" + x + ",y=" + y + "]";
	}

	/* STATIC METHODS */

	public static Vector toVector(final Point p) {
		return new Vector(p);
	}

	/* INTERNALS */

	private static double calculateMagnitude(final int x, final int y) {
		final int xSquared = x * x;
		final int ySquared = y * y;

		return Math.sqrt(xSquared + ySquared);
	}
}
