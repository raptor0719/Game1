package raptor.engine.util.geometry;

import java.util.Arrays;
import java.util.Collection;

import raptor.engine.util.geometry.api.IPoint;
import raptor.engine.util.geometry.api.IRectangle;

public class Rectangle implements IRectangle {
	private final Point[] points;
	private final LineSegment[] lineSegments;

	private final Point min;
	private final Point max;

	public Rectangle(final int x1, final int y1, final int x2, final int y2, final int x3, final int y3, final int x4, final int y4) {
		points = new Point[4];
		points[0] = new Point(x1, y1);
		points[1] = new Point(x2, y2);
		points[2] = new Point(x3, y3);
		points[3] = new Point(x4, y4);

		lineSegments = new LineSegment[4];
		lineSegments[0] = new LineSegment(points[0], points[1]);
		lineSegments[1] = new LineSegment(points[1], points[2]);
		lineSegments[2] = new LineSegment(points[2], points[3]);
		lineSegments[3] = new LineSegment(points[3], points[0]);

		int tempMinX = Integer.MAX_VALUE;
		int tempMinY = Integer.MAX_VALUE;
		int tempMaxX = Integer.MIN_VALUE;
		int tempMaxY = Integer.MIN_VALUE;
		for (final Point p : points) {
			tempMinX = Math.min(tempMinX, p.getX());
			tempMinY = Math.min(tempMinY, p.getY());
			tempMaxX = Math.max(tempMaxX, p.getX());
			tempMaxY = Math.max(tempMaxY, p.getY());
		}

		min = new Point(tempMinX, tempMinY);
		max = new Point(tempMaxX, tempMaxY);
	}

	@Override
	public Collection<LineSegment> getLineSegments() {
		return Arrays.asList(lineSegments);
	}

	@Override
	public Collection<IPoint> getPoints() {
		return Arrays.asList(points);
	}

	@Override
	public boolean containsPoint(final IPoint point) {
		return point.getX() >= min.getX() && point.getX() <= max.getX() && point.getY() >= min.getY() && point.getY() <= max.getY();
	}
}
