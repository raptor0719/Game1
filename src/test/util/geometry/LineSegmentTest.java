package util.geometry;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

public class LineSegmentTest {
	@Test
	public void segmentLength() {
		// Using the pythagorean triplet: a=63, b=16, c=65
		final LineSegment ls = createSegment(createPoint(11, 1), createPoint(74, 17));

		Assert.assertThat(ls.getLength(), equalTo(65F));
	}

	@Test
	public void segmentLength_pointsInverted() {
		// Using the pythagorean triplet: a=63, b=16, c=65
		final LineSegment ls = createSegment(createPoint(74, 17), createPoint(11, 1));

		Assert.assertThat(ls.getLength(), equalTo(65F));
	}

	@Test
	public void segmentLength_zero() {
		final LineSegment ls = createSegment(createPoint(10,15), createPoint(10,15));

		Assert.assertThat(ls.getLength(), equalTo(0F));
	}

	@Test
	public void segmentLength_startsAtZero() {
		final LineSegment ls = createSegment(createPoint(0,0), createPoint(15,15));

		Assert.assertThat(ls.getLength(), equalTo(21.213203F));
	}

	@Test
	public void segmentLength_endsAtZero() {
		final LineSegment ls = createSegment(createPoint(15,15), createPoint(0,0));

		Assert.assertThat(ls.getLength(), equalTo(21.213203F));
	}

	@Test
	public void segmentLength_horizontal() {
		final LineSegment ls = createSegment(createPoint(13,13), createPoint(23,13));

		Assert.assertThat(ls.getLength(), equalTo(10F));
	}

	@Test
	public void segmentLength_vertical() {
		final LineSegment ls = createSegment(createPoint(21,65), createPoint(21,50));

		Assert.assertThat(ls.getLength(), equalTo(15F));
	}

	@Test
	public void intersection() {
		final LineSegment seg1 = createSegment(createPoint(0,0), createPoint(10,10));
		final LineSegment seg2 = createSegment(createPoint(0,10), createPoint(10,0));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertThat(intersection, equalTo(createPoint(5,5)));
	}

	@Test
	public void intersection_intersectsAtOneEndPoint() {
		final LineSegment seg1 = createSegment(createPoint(10,10), createPoint(10,0));
		final LineSegment seg2 = createSegment(createPoint(0,0), createPoint(20,20));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertThat(intersection, equalTo(createPoint(10,10)));
	}

	@Test
	public void intersection_endPointsTouch() {
		final LineSegment seg1 = createSegment(createPoint(30,30), createPoint(13,0));
		final LineSegment seg2 = createSegment(createPoint(10,10), createPoint(30,30));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertThat(intersection, equalTo(createPoint(30,30)));
	}

	@Test
	public void intersection_doesNotIntersect() {
		final LineSegment seg1 = createSegment(createPoint(30,30), createPoint(13,0));
		final LineSegment seg2 = createSegment(createPoint(1,3), createPoint(9,15));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertNull(intersection);
	}

	@Test
	public void intersection_collinear_partialOverlap() {
		final LineSegment seg1 = createSegment(createPoint(0,0), createPoint(10,10));
		final LineSegment seg2 = createSegment(createPoint(5,5), createPoint(15,15));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertNull(intersection);
	}

	@Test
	public void intersection_collinear_sameSegment() {
		final LineSegment seg1 = createSegment(createPoint(0,0), createPoint(10,10));
		final LineSegment seg2 = createSegment(createPoint(0,0), createPoint(10,10));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertNull(intersection);
	}

	@Test
	public void intersection_collinear_disjoint() {
		final LineSegment seg1 = createSegment(createPoint(0,0), createPoint(10,10));
		final LineSegment seg2 = createSegment(createPoint(11,11), createPoint(19,19));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertNull(intersection);
	}

	@Test
	public void intersection_parallel() {
		final LineSegment seg1 = createSegment(createPoint(0,0), createPoint(10,10));
		final LineSegment seg2 = createSegment(createPoint(1,0), createPoint(11,10));

		final Point intersection = seg1.getIntersectionPoint(seg2);

		Assert.assertNull(intersection);
	}

	@Test
	public void test() {
		final Triangle t = new Triangle(createPoint(0,0), createPoint(0,10), createPoint(10,0));

		final Point p = createPoint(5,5);

		System.out.println(t.isIntersectedByLine(createSegment(p, createPoint(10,10))));
		System.out.println(t.containsPoint(p));
	}

	/* HELPER METHODS */

	private Point createPoint(final int x, final int y) {
		return new Point(x,y);
	}

	private LineSegment createSegment(final Point a, final Point b) {
		return new LineSegment(a,b);
	}

	private <T> Matcher<T> equalTo(final T obj) {
		return CoreMatchers.equalTo(obj);
	}
}
