package raptor.engine.util.geometry;

import raptor.engine.util.geometry.api.ICircle;
import raptor.engine.util.geometry.api.IPoint;

public class Circle implements ICircle {
	private IPoint origin;
	private int radius;

	public Circle(final int originX, final int originY, final int radius) {
		origin = new Point(originX, originY);
		this.radius = radius;
	}

	public Circle(final IPoint origin, final int radius) {
		this.origin = origin;
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
		origin = new Point(x, y);
	}

	public void setOrigin(final IPoint origin) {
		this.origin = origin;
	}

	public void setRadius(final int radius) {
		this.radius = radius;
	}

	@Override
	public IPoint getOrigin() {
		return origin;
	}

	@Override
	public int getRadius() {
		return radius;
	}

	/* STATIC */

	public static boolean isIntersectedByLine(final int originX, final int originY, final int radius, final int l1x, final int l1y, final int l2x, final int l2y) {
		return Point.distanceTo(originX, originY, l1x, l1y, l2x, l2y) < radius;
	}

	public static boolean containsPoint(final int originX, final int originY, final int radius, final int px, final int py) {
		return LineSegment.length(originX, originY, px, py) < radius;
	}

	public static boolean containsPoint(final ICircle circle, final IPoint point) {
		return containsPoint(circle.getOrigin().getX(), circle.getOrigin().getY(), circle.getRadius(), point.getX(), point.getY());
	}
}
