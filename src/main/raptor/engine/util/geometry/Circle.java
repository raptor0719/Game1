package raptor.engine.util.geometry;

import raptor.engine.util.geometry.api.IPoint;

public class Circle {
	private final Point origin;
	private int radius;

	public Circle(final int originX, final int originY, final int radius) {
		origin = new Point(originX, originY);
		this.radius = radius;
	}

	public boolean isIntersectedByLine(final LineSegment ls) {
		return Circle.isIntersectedByLine(origin.getX(), origin.getY(), radius,
				ls.getPoints().getValue1().getX(), ls.getPoints().getValue1().getY(),
				ls.getPoints().getValue2().getX(), ls.getPoints().getValue2().getY());
	}

	public boolean containsPoint(final int px, final int py) {
		return Circle.containsPoint(origin.getX(), origin.getY(), radius, px, py);
	}

	public void setOrigin(final int x, final int y) {
		origin.setX(x);
		origin.setY(y);
	}

	public void setRadius(final int radius) {
		this.radius = radius;
	}

	public IPoint getOrigin() {
		return origin;
	}

	/* STATIC */

	public static boolean isIntersectedByLine(final int originX, final int originY, final int radius, final int l1x, final int l1y, final int l2x, final int l2y) {
		return Point.distanceTo(originX, originY, l1x, l1y, l2x, l2y) < radius;
	}

	public static boolean containsPoint(final int originX, final int originY, final int radius, final int px, final int py) {
		return LineSegment.length(originX, originY, px, py) < radius;
	}
}
