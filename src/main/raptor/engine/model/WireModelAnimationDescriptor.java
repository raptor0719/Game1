package raptor.engine.model;

public class WireModelAnimationDescriptor {
	private final int id;
	private final int[] frames;
	private final int[] portions;
	private final int totalPortions;

	public WireModelAnimationDescriptor(final int id, final int[] frameMappings, final int[] framePortions) {
		if (frameMappings.length != framePortions.length)
			throw new IllegalArgumentException("Length mismatch in mappings and proportions.");

		this.id = id;
		this.frames = frameMappings;
		this.portions = framePortions;
		this.totalPortions = calculateTotalPortions(framePortions);
	}

	public int getId() {
		return id;
	}

	public int[] getFrames() {
		return frames;
	}

	public int[] getFrameProportions() {
		return portions;
	}

	public int totalProportions() {
		return totalPortions;
	}

	private int calculateTotalPortions(final int[] portions) {
		int total = 0;
		for (final int i : portions)
			total += i;
		return total;
	}
}
