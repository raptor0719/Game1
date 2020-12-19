package raptor.engine.util.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class GeometryFunctionsTest {
	@Test
	public void getBoundingSegmentsTest_basic() {
		final Point p1 = new Point(0, 0);
		final Point p2 = new Point(400, 300);
		final Point p3 = new Point(0, 300);
		final Point p4 = new Point(300, 300);
		final Point p5 = new Point(0, 500);

		final Triangle t1 = new Triangle(p1, p2, p3);
		final Triangle t2 = new Triangle(p5, p4, p3);

		final List<LineSegment> boundingSegments = GeometryFunctions.getBoundingSegments(Arrays.asList(t1, t2));

		final List<LineSegment> expected = new ArrayList<LineSegment>();
		expected.add(new LineSegment(p1, p2));
		expected.add(new LineSegment(p2, p4));
		expected.add(new LineSegment(p4, p5));
		expected.add(new LineSegment(p5, p3));
		expected.add(new LineSegment(p3, p1));

		Assert.assertTrue(expected.containsAll(boundingSegments));
	}

	@Test
	public void fillPolygonWithTrianglesTest_basic() {
		final Point parent1 = new Point(50, 50);
		final Point parent2 = new Point(100, 50);
		final Point parent3 = new Point(100, 100);
		final Point parent4 = new Point(40, 100);

		final Point hole1 = new Point(60, 70);
		final Point hole2 = new Point(90, 70);
		final Point hole3 = new Point(75, 90);

		final List<Point> parentPoints = Arrays.asList(new Point[] {parent1, parent2, parent3, parent4});
		final List<Point> holePoints = Arrays.asList(new Point[] {hole1, hole2, hole3});

		final Polygon parent = new Polygon(parentPoints);
		final Polygon hole = new Polygon(holePoints);

		final List<Polygon> holes = new ArrayList<>();
		holes.add(hole);

		final List<Triangle> triangles = GeometryFunctions.fillPolygonWithTriangles(parent, holes);

		final List<Triangle> expected = new ArrayList<>();
		expected.add(new Triangle(parent1, hole1, hole2));
		expected.add(new Triangle(parent1, hole2, parent2));
		expected.add(new Triangle(parent1, parent4, hole1));
		expected.add(new Triangle(parent2, hole2, parent3));
		expected.add(new Triangle(parent3, hole2, hole3));
		expected.add(new Triangle(parent3, hole3, parent4));
		expected.add(new Triangle(parent4, hole1, hole3));

		Assert.assertTrue(triangles.containsAll(expected));
	}
}
