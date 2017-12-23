package util.geometry;

import org.junit.Assert;
import org.junit.Test;

public class TriangleTest {

	@Test
	public void test() {
		final Triangle t = new Triangle(new Point(800,300), new Point(500,300), new Point(800,500));
		final Point p = new Point(525, 350);

		Assert.assertFalse(t.containsPoint(p));
	}
}
