package raptor.engine.model;

import java.util.AbstractList;

public class WireModelAnimationFrames extends AbstractList<Integer> {
	private final int[] frames;

	public WireModelAnimationFrames(final int[] frameIds, final int[] counts) {
		if (frameIds.length != counts.length)
			throw new IllegalArgumentException("Length mismatch in given arguments.");
		if (counts[0] < 0)
			throw new IllegalArgumentException("Negative frame count encountered.");

		this.frames = buildFrames(frameIds, counts);
	}

	@Override
	public Integer get(final int index) {
		return frames[index];
	}

	@Override
	public int size() {
		return frames.length;
	}

	private int[] buildFrames(final int[] frameIds, final int[] counts) {
		final int[] frames = new int[calculateSize(counts)];
		int current = 0;
		for (int i = 0; i < counts.length; i++) {
			final int count = counts[i];
			for (int j = 0; j < count; j++) {
				frames[current] = frameIds[i];
				current++;
			}
		}
		return frames;
	}

	private int calculateSize(final int[] counts) {
		int total = 0;
		for (final int i : counts)
			total += i;
		return total;
	}
}
