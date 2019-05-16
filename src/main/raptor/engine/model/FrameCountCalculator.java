package raptor.engine.model;

public class FrameCountCalculator {
	public int calculateFrameCount(final int frameCount, final int portion, final int totalPortions) {
		return (int) Math.ceil(( (float)(portion) / totalPortions) * frameCount);
	}
}
