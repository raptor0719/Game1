package raptor.modelLibrary.model;

public class FrameTiming {
	private final int scale;
	private final int priority;

	public FrameTiming(final int scale, final int priority) {
		this.scale = scale;
		this.priority = priority;
	}

	public int getScale() {
		return scale;
	}

	public int getPriority() {
		return priority;
	}
}
