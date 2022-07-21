package raptor.engine.util.geometry;

import java.util.Arrays;
import java.util.Collection;

import raptor.engine.util.geometry.api.IPoint;
import raptor.engine.util.geometry.api.IRectangle;

public class Rectangle implements IRectangle {
	private final IPoint[] points;
	private final LineSegment[] lineSegments;

	private Point min;
	private Point max;

	public Rectangle(final int x1, final int y1, final int x2, final int y2, final int x3, final int y3, final int x4, final int y4) {
		points = new Point[4];
		points[0] = new Point(x1, y1);
		points[1] = new Point(x2, y2);
		points[2] = new Point(x3, y3);
		points[3] = new Point(x4, y4);

		lineSegments = new LineSegment[4];
		calculateInternals();
	}

	public Rectangle(final IPoint point1, final IPoint point2, final IPoint point3, final IPoint point4) {
		points = new Point[4];
		points[0] = point1;
		points[1] = point2;
		points[2] = point3;
		points[3] = point4;

		lineSegments = new LineSegment[4];
		calculateInternals();
	}

	private void calculateInternals() {
		lineSegments[0] = new LineSegment(points[0], points[1]);
		lineSegments[1] = new LineSegment(points[1], points[2]);
		lineSegments[2] = new LineSegment(points[2], points[3]);
		lineSegments[3] = new LineSegment(points[3], points[0]);

		int tempMinX = Integer.MAX_VALUE;
		int tempMinY = Integer.MAX_VALUE;
		int tempMaxX = Integer.MIN_VALUE;
		int tempMaxY = Integer.MIN_VALUE;
		for (final IPoint p : points) {
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

	@Override
	public int getArea() {
		final int length = max.getX() - min.getX();
		final int width = max.getY() - min.getY();

		return length * width;
	}
}
