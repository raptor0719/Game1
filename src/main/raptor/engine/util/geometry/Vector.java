package raptor.engine.util.geometry;

public class Vector {
	private int x;
	private int y;

	public Vector (final Point p) {
		this(p.getX(), p.getY());
	}

	public Vector(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(final int newX) {
		x = newX;
	}

	public void setY(final int newY) {
		y = newY;
	}

	public double getMagnitude() {
		return calculateMagnitude(x, y);
	}

	public int cross(final Vector b) {
		return crossProduct(this, b);
	}

	public int dot(final Vector b) {
		return dotProduct(this, b);
	}

	public Vector minus(final Vector b) {
		return minus(this, b);
	}

	public DoubleVector unitVector() {
		return new DoubleVector(x, y).unitVector();
	}

	public double getAngleBetween(final Vector v) {
		final double magnitude = calculateMagnitude(x, y);
		return Math.acos(dot(v) / (magnitude*v.getMagnitude()));
	}

	@Override
	public String toString() {
		return "Vector:[x=" + x + ",y=" + y + "]";
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (!(o instanceof Vector))
			return false;
		final Vector o2 = (Vector)o;
		if (o2.getX() != getX())
			return false;
		if (o2.getY() != getY())
			return false;
		return true;
	}

	/* STATIC METHODS */

	public static Vector toVector(final Point p) {
		return new Vector(p);
	}

	public static int crossProduct(final Vector a, final Vector b) {
		return a.getX()*b.getY() - a.getY()*b.getX();
	}

	public static int dotProduct(final Vector a, final Vector b) {
		return (a.getX()*b.getX()) + (a.getY()*b.getY());
	}

	public static Vector minus(final Vector a, final Vector b) {
		return new Vector(a.getX() - b.getX(), a.getY() - b.getY());
	}

	/* INTERNALS */

	private static double calculateMagnitude(final int x, final int y) {
		final int xSquared = x * x;
		final int ySquared = y * y;

		return Math.sqrt(xSquared + ySquared);
	}
}
