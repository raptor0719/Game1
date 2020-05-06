package raptor.engine.util.geometry;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.util.geometry.api.IPoint;

public class GeometryFunctions {
	public static List<LineSegment> getBoundingSegments(final List<Triangle> compositeShape) {
		final List<LineSegment> segments = new ArrayList<LineSegment>();

		for (final Triangle triangle : compositeShape)
			for (final LineSegment triangleSegment : triangle.getLines())
				segments.add(triangleSegment);

		for (int i = 0; i < segments.size(); i++) {
			final LineSegment current = segments.get(i);

			for (int j = 0; j < segments.size(); j++) {
				if (i == j)
					continue;

				final LineSegment compare = segments.get(j);

				if (current.equals(compare)) {
					removeMultiple(segments, i, j);
					i = -1;
					break;
				} else if (current.isConnected(compare)) {
					continue;
				} else if (current.overlaps(compare)) {
					IPoint nonOverlappingSubsegment1_start;
					IPoint nonOverlappingSubsegment1_end;
					IPoint nonOverlappingSubsegment2_start;
					IPoint nonOverlappingSubsegment2_end;

					if (current.getStart().isOnLineSegment(compare)) {
						nonOverlappingSubsegment1_start = current.getStart();

						if (compare.getStart().isOnLineSegment(current)) {
							nonOverlappingSubsegment1_end = compare.getEnd();
							nonOverlappingSubsegment2_start = compare.getStart();
							nonOverlappingSubsegment2_end = current.getEnd();
						} else {
							nonOverlappingSubsegment1_end = compare.getStart();
							nonOverlappingSubsegment2_start = compare.getEnd();
							nonOverlappingSubsegment2_end = current.getEnd();
						}
					} else {
						nonOverlappingSubsegment1_start = current.getEnd();

						if (compare.getStart().isOnLineSegment(current)) {
							nonOverlappingSubsegment1_end = compare.getEnd();
							nonOverlappingSubsegment2_start = compare.getStart();
							nonOverlappingSubsegment2_end = current.getStart();
						} else {
							nonOverlappingSubsegment1_end = compare.getStart();
							nonOverlappingSubsegment2_start = compare.getEnd();
							nonOverlappingSubsegment2_end = current.getStart();
						}
					}

					final LineSegment newSegment1 = new LineSegment(nonOverlappingSubsegment1_start, nonOverlappingSubsegment1_end);
					final LineSegment newSegment2 = new LineSegment(nonOverlappingSubsegment2_start, nonOverlappingSubsegment2_end);

					removeMultiple(segments, i, j);
					if (!newSegment1.getStart().equals(newSegment1.getEnd()))
						segments.add(newSegment1);
					if (!newSegment2.getStart().equals(newSegment2.getEnd()))
						segments.add(newSegment2);

					i = -1;
					break;
				}
			}
		}

		return segments;
	}

	// INTERNAL

	private static void removeMultiple(final List<?> l, final int... indices) {
		for (int i = l.size() - 1; i > -1; i--)
			if (contains(indices, i))
				l.remove(i);
	}

	private static boolean contains(final int[] arr, final int s) {
		for (final int i : arr)
			if (i == s)
				return true;
		return false;
	}
}
