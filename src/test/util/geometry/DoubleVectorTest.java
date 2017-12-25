package util.geometry;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

public class DoubleVectorTest {

	@Test
	public void unitVector() {
		final DoubleVector v1 = getVector(3.0, 4.0);

		final DoubleVector actual = getVector(0.6, 0.8);

		Assert.assertThat(v1.unitVector(), equalTo(actual));
	}

	@Test
	public void unitVector_oppositeDirection() {
		final DoubleVector v1 = getVector(-3.0, -4.0);

		final DoubleVector actual = getVector(-0.6, -0.8);

		Assert.assertThat(v1.unitVector(), equalTo(actual));
	}

	@Test
	public void unitVector_horizontal() {
		final DoubleVector v1 = getVector(5.0, 0.0);

		final DoubleVector actual = getVector(1.0, 0.0);

		Assert.assertThat(v1.unitVector(), equalTo(actual));
	}

	@Test
	public void unitVector_horizontal_oppositeDirection() {
		final DoubleVector v1 = getVector(-5.0, 0.0);

		final DoubleVector actual = getVector(-1.0, 0.0);

		Assert.assertThat(v1.unitVector(), equalTo(actual));
	}

	@Test
	public void unitVector_vertical() {
		final DoubleVector v1 = getVector(0.0, 90.0);

		final DoubleVector actual = getVector(0.0, 1.0);

		Assert.assertThat(v1.unitVector(), equalTo(actual));
	}

	@Test
	public void unitVector_vertical_oppositeDirection() {
		final DoubleVector v1 = getVector(0.0, -90.0);

		final DoubleVector actual = getVector(0.0, -1.0);

		Assert.assertThat(v1.unitVector(), equalTo(actual));
	}

	@Test
	public void unitVector_zeroLength() {
		final DoubleVector v1 = getVector(0.0, 0.0);

		final DoubleVector actual = getVector(0.0, 0.0);

		Assert.assertThat(v1.unitVector(), equalTo(actual));
	}

	@Test
	public void unitVectorTowardPoint() {
		final Point p1 = new Point(2, 2);
		final Point p2 = new Point(5, 6);

		final DoubleVector actual = getVector(0.6, 0.8);

		Assert.assertThat(DoubleVector.unitVectorTowardPoint(p1, p2), equalTo(actual));
	}

	@Test
	public void unitVectorTowardPoint_horizontal() {
		final Point p1 = new Point(5, 7);
		final Point p2 = new Point(10, 7);

		final DoubleVector actual = getVector(1.0, 0.0);

		Assert.assertThat(DoubleVector.unitVectorTowardPoint(p1, p2), equalTo(actual));
	}

	@Test
	public void unitVectorTowardPoint_horizntal_oppositeDirection() {
		final Point p1 = new Point(5, 7);
		final Point p2 = new Point(10, 7);

		final DoubleVector actual = getVector(-1.0, 0.0);

		Assert.assertThat(DoubleVector.unitVectorTowardPoint(p2, p1), equalTo(actual));
	}

	@Test
	public void unitVectorTowardPoint_vertical() {
		final Point p1 = new Point(-3, -11);
		final Point p2 = new Point(-3, 50);

		final DoubleVector actual = getVector(0.0, 1.0);

		Assert.assertThat(DoubleVector.unitVectorTowardPoint(p1, p2), equalTo(actual));
	}

	@Test
	public void unitVectorTowardPoint_vertical_oppositeDirection() {
		final Point p1 = new Point(-3, -11);
		final Point p2 = new Point(-3, 50);

		final DoubleVector actual = getVector(0.0, -1.0);

		Assert.assertThat(DoubleVector.unitVectorTowardPoint(p2, p1), equalTo(actual));
	}

	@Test
	public void unitVectorTowardPoint_zeroLength() {
		final Point p1 = new Point(15, -20);

		final DoubleVector actual = getVector(0.0, 0.0);

		Assert.assertThat(DoubleVector.unitVectorTowardPoint(p1, p1), equalTo(actual));
	}

	/* HELPER METHODS */

	private DoubleVector getVector(final double x, final double y) {
		return new DoubleVector(x, y);
	}

	private <T> Matcher<T> equalTo(final T obj) {
		return CoreMatchers.equalTo(obj);
	}
}
