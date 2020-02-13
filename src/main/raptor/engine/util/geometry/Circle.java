package raptor.engine.util.geometry;

public class Circle {
	private int originX;
	private int originY;
	private float radius;

	public Circle(final int originX, final int originY, final float radius) {
		this.originX = originX;
		this.originY = originY;
		this.radius = radius;
	}

	public boolean isIntersectedByLine(final LineSegment ls) {
		// TODO
		return false;
	}
}
