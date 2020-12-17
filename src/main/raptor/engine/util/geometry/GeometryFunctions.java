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

	public static List<Triangle> fillPolygonWithTriangles(final Polygon parent, final List<Polygon> holes) {
		// TODO: Add some checks here

		final List<Point> points = new ArrayList<>(parent.getPoints());

		// Connect the holes to the parent to form a single circular list of line segments
		final List<LineSegment> allSegments = compileLineSegments(parent, holes);
		for (final Polygon hole : holes) {
			Point suitableParentPoint = null;
			Point suitableHolePoint = null;

			// Create a possible segment from all points of the parent to all points of the hole.
			// If the possible segment intersects with any other line segment, it is not suitable.
			for (final Point parentPoint : points) {
				for (final Point holePoint : hole.getPoints()) {
					final LineSegment possibleConnectingSegment = new LineSegment(parentPoint, holePoint);

					boolean intersects = false;
					for (final LineSegment existingSegment : allSegments) {
						if (possibleConnectingSegment.intersectsWithExcludingEndpoints(existingSegment)) {
							intersects = true;
							break;
						}
					}

					// A suitable segment was found, so set the points, and add it to the list.
					if (!intersects) {
						suitableParentPoint = parentPoint;
						suitableHolePoint = holePoint;
						allSegments.add(possibleConnectingSegment);
					}
				}

				// We've found a suitable segment, so continue on.
				if (suitableParentPoint != null && suitableHolePoint != null)
					break;
			}

			// We've found a suitable connection, so add the hole's points to
			// the parent points. The connecting segment needs to be added twice, once
			// at the beginning and once at the end of the hole's points. If no
			// suitable points were found, than something went wrong or an invalid
			// set of polygons were given.
			if (suitableParentPoint != null && suitableHolePoint != null) {
				final int insertAtThisIndex = points.indexOf(suitableParentPoint);

				// Insertion shifts to the right, so we insert the suitable hole point
				// first so that it forms a segment with the parent point of the original list.
				// We then insert the points of the hole, and finally the suitable parent point to
				// form the same line segment again. (The connecting segment will be
				// used TWICE in the formation of the triangles.)
				points.add(insertAtThisIndex, suitableHolePoint);
				points.addAll(insertAtThisIndex, hole.getPoints());
				points.add(insertAtThisIndex, suitableParentPoint);
			} else {
				throw new IllegalArgumentException("Invalid setup given for routine.");
			}
		}

		// Now we can create the triangles
		final List<Triangle> triangles = new ArrayList<>();

		// Use a tracker object to track the permutations we've tried already.
		final PermutationTrackingPointList tracker = new PermutationTrackingPointList(points);
		List<Point> currentWorkingPermutation = points;
		boolean filled = false;

		while (!filled) {
			int index = -1;
			boolean hasFoundTriangle = false;

			while (true) {
				// Get 3 points along the list
				final Point start = currentWorkingPermutation.get(index % currentWorkingPermutation.size());
				final Point middle = currentWorkingPermutation.get((index + 1) % currentWorkingPermutation.size());
				final Point end = currentWorkingPermutation.get((index + 2) % currentWorkingPermutation.size());

				// Create a segment from the start to the end
				final LineSegment potentialSegment = new LineSegment(start, end);

				// Check if this new segments intersects any others
				boolean intersects = false;
				for (final LineSegment segment : allSegments) {
					if (potentialSegment.intersectsWithExcludingEndpoints(segment)) {
						intersects = true;
						break;
					}
				}

				// If it doesn't, than a new triangle was found! Set the hasFoundTriangle flag
				// to keep looking on this permutation, create the new triangle, add the new
				// triangle to the list of triangles, add the new segment to the list of segments,
				// and remove the middle point from the list (and the tracker).
				// If it does intersect, than this isn't valid and keep looking.
				if (!intersects) {
					hasFoundTriangle = true;
					triangles.add(new Triangle(start, middle, end));
					allSegments.add(potentialSegment);
					currentWorkingPermutation.remove((index + 1) % currentWorkingPermutation.size());
					tracker.remove((index + 1) % currentWorkingPermutation.size());
				}

				// We just added a triangle and now we only have 2 points left,
				// that means we're done! (Because, weird how this works, triangles
				// need 3 points to work)
				if (currentWorkingPermutation.size() < 3) {
					filled = true;
					break;
				}

				// If we've done a full rotation and we haven't found a triangle, than
				// it's time to try another permutation.
				if (index % currentWorkingPermutation.size() == 0 && !hasFoundTriangle)
					break;

				index += 1;
			}

			// We haven't filled the triangle yet and we've exhausted the current
			// permutation so go ahead and query the tracker for another one.
			if (!filled) {
				currentWorkingPermutation = tracker.getNextPermutation();

				// We've exhausted all permutations and cannot proceed.
				if (currentWorkingPermutation == null)
					throw new IllegalArgumentException("An invalid collection of polygons was given and triangles could not be generated.");
			}
		}

		return triangles;
	}

	// INTERNAL

	// Sometimes triangles cannot be found when traversing point lists
	// in a certain order. However, because holes form loops, we can
	// reverse sections of the ordering to look for triangles in a
	// different order. Ordering can only be reversed between repeat
	// points, as these indicate a loop.
	// EX: Points 0, 1, 2, 3, 0, 4, 5, 6 could be reordered to 0, 3, 2, 1, 0, 4, 5, 6
	private static class PermutationTrackingPointList {
		private List<Point> current;
		private List<List<Point>> potentialPermutations;

		public PermutationTrackingPointList(final List<Point> points) {
			current = new ArrayList<>(points);
			potentialPermutations = new ArrayList<>();
		}

		// Get the next permutation that we could try. If there are none
		// left, it means there are no more to try and we've run into a
		// scenario where the polygon cannot be triangulized. As far as
		// I know, this would only be the case if we were in an invalid
		// state (which should be caught by pre-checks).
		public List<Point> getNextPermutation() {
			return (potentialPermutations.isEmpty()) ? null : potentialPermutations.remove(0);
		}

		// Caller needs to remove elements from the current here as it
		// does its own removes to recalculate the potential permutations
		public void remove(final int index) {
			current.remove(index);
			potentialPermutations = calculatePermutations(current);
		}

		// TODO: Implement this
		private List<List<Point>> calculatePermutations(final List<Point> list) {
			return null;
		}
	}

	private static List<LineSegment> compileLineSegments(final Polygon parent, final List<Polygon> holes) {
		final List<LineSegment> segments = new ArrayList<>(parent.getLines());

		for (final Polygon hole : holes)
			segments.addAll(hole.getLines());

		return segments;
	}

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
