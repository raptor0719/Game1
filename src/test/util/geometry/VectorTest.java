package util.geometry;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

	@Test
	public void cross() {
		final Vector v1 = getVector(1, 2);
		final Vector v2 = getVector(3, 4);

		Assert.assertThat(v1.cross(v2), equalTo(-2));
	}

	@Test
	public void cross_reversed() {
		final Vector v1 = getVector(1, 2);
		final Vector v2 = getVector(3, 4);

		Assert.assertThat(v2.cross(v1), equalTo(2));
	}

	@Test
	public void cross_rightAngle() {
		final Vector v1 = getVector(0, 5);
		final Vector v2 = getVector(5, 0);

		Assert.assertThat(v1.cross(v2), equalTo(-25));
	}

	@Test
	public void cross_rightAngle_reversed() {
		final Vector v1 = getVector(0, 5);
		final Vector v2 = getVector(5, 0);

		Assert.assertThat(v2.cross(v1), equalTo(25));
	}

	@Test
	public void cross_parallel() {
		final Vector v1 = getVector(1, 5);
		final Vector v2 = getVector(-1, -5);

		Assert.assertThat(v1.cross(v2), equalTo(0));
	}

	@Test
	public void cross_zeroLength() {
		final Vector v1 = getVector(0, 0);
		final Vector v2 = getVector(-1, -5);

		Assert.assertThat(v1.cross(v2), equalTo(0));
	}

	@Test
	public void cross_zeroLength_both() {
		final Vector v1 = getVector(0, 0);
		final Vector v2 = getVector(0, 0);

		Assert.assertThat(v1.cross(v2), equalTo(0));
	}

	@Test
	public void cross_sameVector() {
		final Vector v1 = getVector(3, 10);

		Assert.assertThat(v1.cross(v1), equalTo(0));
	}

	@Test
	public void unitVector() {
		final Vector v1 = getVector(3, 4);

		final DoubleVector expected = new DoubleVector(0.6, 0.8);

		Assert.assertThat(v1.unitVector(), equalTo(expected));
	}

	@Test
	public void unitVector_vertical() {
		final Vector v1 = getVector(0, 10);

		final DoubleVector expected = new DoubleVector(0, 1);

		Assert.assertThat(v1.unitVector(), equalTo(expected));
	}

	@Test
	public void unitVector_vertical_oppositeDirection() {
		final Vector v1 = getVector(0, -10);

		final DoubleVector expected = new DoubleVector(0, -1);

		Assert.assertThat(v1.unitVector(), equalTo(expected));
	}

	@Test
	public void unitVector_horizontal_oppositeDirection() {
		final Vector v1 = getVector(-7, 0);

		final DoubleVector expected = new DoubleVector(-1, 0);

		Assert.assertThat(v1.unitVector(), equalTo(expected));
	}

	@Test
	public void magnitude() {
		final Vector v1 = getVector(3, 4);

		Assert.assertThat(v1.getMagnitude(), equalTo(5.0));
	}

	@Test
	public void magnitude_horizontal() {
		final Vector v1 = getVector(7, 0);

		Assert.assertThat(v1.getMagnitude(), equalTo(7.0));
	}

	@Test
	public void magnitude_horizontal_oppositeDirection() {
		final Vector v1 = getVector(-7, 0);

		Assert.assertThat(v1.getMagnitude(), equalTo(7.0));
	}

	@Test
	public void magnitude_vertical_oppositeDirection() {
		final Vector v1 = getVector(0, -25);

		Assert.assertThat(v1.getMagnitude(), equalTo(25.0));
	}

	@Test
	public void magnitude_zeroLength() {
		final Vector v1 = getVector(7, 0);

		Assert.assertThat(v1.getMagnitude(), equalTo(7.0));
	}

	/* HELPER METHODS */

	private Vector getVector(final int x, final int y) {
		return new Vector(x, y);
	}

	private <T> Matcher<T> equalTo(final T obj) {
		return CoreMatchers.equalTo(obj);
	}
}
