package util.geometry;

public class Vector {
	private final int x;
	private final int y;

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

	public int cross(final Vector b) {
		return x*b.getY() - y*b.getX();
	}

	public Vector minus(final Vector b) {
		return new Vector(x - b.getX(), y - b.getY());
	}

	@Override
	public String toString() {
		return "Vector:[x=" + x + ",y=" + y + "]";
	}

	/* STATIC METHODS */

	public static Vector toVector(final Point p) {
		return new Vector(p);
	}
}
