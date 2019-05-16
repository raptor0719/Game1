package raptor.engine.test.main;

import org.junit.Assert;
import org.junit.Test;

import raptor.engine.model.FrameCountCalculator;
import raptor.engine.model.WireModelAnimationFrames;

public class TempTest {
	@Test
	public void WireModelAnimationFramesTest() {
		final WireModelAnimationFrames frames = new WireModelAnimationFrames(new int[] {1, 2, 3}, new int[] {3, 2, 3});

		final int[] fullFrames = new int[frames.size()];

		for (int i = 0; i < fullFrames.length; i++)
			fullFrames[i] = frames.get(i);

		final int[] expected = new int[] {1, 1, 1, 2, 2, 3, 3, 3};
		Assert.assertArrayEquals(expected, fullFrames);
	}

	@Test
	public void FrameCountCalcualtorTest() {
		final FrameCountCalculator calc = new FrameCountCalculator();

		System.out.println(calc.calculateFrameCount(300, 2, 7));
		System.out.println(calc.calculateFrameCount(214, 1, 5));
		System.out.println(calc.calculateFrameCount(171, 1, 4));
		System.out.println(calc.calculateFrameCount(128, 1, 3));
		System.out.println(calc.calculateFrameCount(85, 2, 2));
	}
}
