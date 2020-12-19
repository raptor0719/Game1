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

					boolean isValid = isValidConnection(possibleConnectingSegment, allSegments);

					// A suitable segment was found, so set the points, and add it to the list.
					if (isValid) {
						suitableParentPoint = parentPoint;
						suitableHolePoint = holePoint;
						allSegments.add(possibleConnectingSegment);
						break;
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
			int index = 0;
			boolean hasFoundTriangle = false;
			int foundTriangleIndex = 0;

			while (true) {
				// Get 3 points along the list
				final Point start = currentWorkingPermutation.get(index % currentWorkingPermutation.size());
				final Point middle = currentWorkingPermutation.get((index + 1) % currentWorkingPermutation.size());
				final Point end = currentWorkingPermutation.get((index + 2) % currentWorkingPermutation.size());

				// Create a segment from the start to the end
				final LineSegment potentialSegment = new LineSegment(start, end);

				// Check if this new segments intersects any others
				boolean isValid = isValidConnection(potentialSegment, allSegments);

				// If it doesn't, than a new triangle was found! Set the hasFoundTriangle flag
				// to keep looking on this permutation, create the new triangle, add the new
				// triangle to the list of triangles, add the new segment to the list of segments,
				// and remove the middle point from the list (and the tracker).
				// If it does intersect, than this isn't valid and keep looking.
				if (isValid) {
					triangles.add(new Triangle(start, middle, end));
					allSegments.add(potentialSegment);
					tracker.remove((index + 1) % currentWorkingPermutation.size());
					currentWorkingPermutation.remove((index + 1) % currentWorkingPermutation.size());
					hasFoundTriangle = true;
					foundTriangleIndex = index % currentWorkingPermutation.size();
				}

				// We just added a triangle and now we only have 3 points left,
				// that means we just gotta add the last one and we're done!
				if (currentWorkingPermutation.size() <= 3) {
					triangles.add(new Triangle(currentWorkingPermutation.get(0), currentWorkingPermutation.get(1), currentWorkingPermutation.get(2)));
					filled = true;
					break;
				}

				index += 1;

				// If we've done a full rotation and we haven't found a triangle, than
				// it's time to try another permutation.
				if (index % currentWorkingPermutation.size() == foundTriangleIndex) {
					if (hasFoundTriangle)
						hasFoundTriangle = false;
					else
						break;
				}
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
	// The reason re-ordering works is because sometimes traversal goes
	// at awkward directions through the shape where a triangle could
	// not be formed due to the way we construct the loops. Long story
	// short this could be solved IF this scenario could be detected and
	// traversing the loops went in a more logical way.
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
			return (potentialPermutations.isEmpty()) ? null : new ArrayList<>(potentialPermutations.remove(0));
		}

		// Caller needs to remove elements from the current here as it
		// does its own removes to recalculate the potential permutations
		public void remove(final int index) {
			current.remove(index);
			potentialPermutations = calculatePermutations(current);
		}

		// Permutations are done by finding repeated points in the list. This is
		// a loop, which means we can traverse the loop in reverse order, so we
		// reorder those points in reverse.
		private List<List<Point>> calculatePermutations(final List<Point> list) {
			// Find all repeated points
			final List<Point> repeats = new ArrayList<>();

			for (int i = 0; i < list.size(); i++) {
				final Point pointLookingFor = list.get(i);
				for (int j = 0; j < list.size(); j++) {
					if (i == j)
						continue;
					final Point compare = list.get(j);
					if (compare.equals(pointLookingFor) && !repeats.contains(pointLookingFor))
						repeats.add(pointLookingFor);
				}
			}

			// Reverse both sections between the repeated points.
			// This is essentially reversing the order in which we
			// travel through the loops relative to the connections
			// between them.
			final List<List<Point>> permutations = new ArrayList<>();

			for (final Point repeat : repeats) {
				final int index1 = list.indexOf(repeat);
				final int index2 = list.lastIndexOf(repeat);

				// Grab the pieces between indices.
				final List<Point> between = new ArrayList<>(list.subList(index1 + 1, index2));
				final List<Point> edges = new ArrayList<>(list.subList(index2 + 1, list.size()));
				edges.addAll(list.subList(0, index1));

				// Create reversed versions
				final List<Point> betweenReversed = reverseList(between);
				final List<Point> edgesReversed = reverseList(edges);

				// Now put together the 2 lists.
				final List<Point> permutation1 = new ArrayList<>();
				permutation1.add(repeat);
				permutation1.addAll(between);
				permutation1.add(repeat);
				permutation1.addAll(edgesReversed);

				final List<Point> permutation2 = new ArrayList<>();
				permutation2.add(repeat);
				permutation2.addAll(betweenReversed);
				permutation2.add(repeat);
				permutation2.addAll(edges);

				permutations.add(permutation1);
				permutations.add(permutation2);
			}

			return permutations;
		}

		private List<Point> reverseList(final List<Point> list) {
			final List<Point> reversed = new ArrayList<>();

			for (int i = 0; i < list.size(); i++)
				reversed.add(0, list.get(i));

			return reversed;
		}
	}

	private static boolean isValidConnection(final LineSegment check, final List<LineSegment> geometry) {
		for (final LineSegment segment : geometry) {
			if (check.intersectsWithExcludingEndpoints(segment))
				return false;
			if (check.equals(segment))
				return false;
		}
		return true;
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
