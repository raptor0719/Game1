package raptor.engine.util.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Polygon {
	private static int BIG_NUMBER = 1000000;

	private final List<Point> points;
	private final List<LineSegment> lines;

	public Polygon(final List<Point> points) {
		if (points.size() < 3)
			throw new IllegalArgumentException("A polygon must have at least 3 points.");

		this.points = Collections.unmodifiableList(points);
		this.lines = buildLineSegments(this.points.iterator());
	}

	public boolean containsPoint(final int x, final int y) {
		/*
		* We detect if the point is in the segment if it:
		* - Is on one of the line segments of the polygon
		* - Or we create a segment from the point to infinity and it intercepts
		*   the segments of the polygon an odd number of times.
		*/
		final Point pointInQuestion = new Point(x, y);
		final Point pointOffInNowhere = new Point(BIG_NUMBER, y);
		final LineSegment segmentThroughPolygon = new LineSegment(pointInQuestion, pointOffInNowhere);

		int intersections = 0;
		for (final LineSegment segment : lines) {
			if (pointInQuestion.isOnLineSegment(segment))
				return true;
			if (segment.intersectsWith(segmentThroughPolygon))
				intersections += 1;
		}

		return intersections % 2 == 1;
	}

	/* INTERNALS */

	private List<LineSegment> buildLineSegments(final Iterator<Point> points) {
		final List<LineSegment> lineSegments = new ArrayList<LineSegment>();

		Point current = points.next();
		while (points.hasNext()) {
			final Point next = points.next();
			lineSegments.add(new LineSegment(current, next));
			current = next;
		}

		return Collections.unmodifiableList(lineSegments);
	}
}
