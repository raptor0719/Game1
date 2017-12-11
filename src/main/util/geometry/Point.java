package util.geometry;

public class Point {
	private final int x;
	private final int y;

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
}
