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
}
