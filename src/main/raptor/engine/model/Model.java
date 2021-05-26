package raptor.engine.model;

public class Model {
	private final WireModel wireModel;
	private final int hardpointCount;

	private int currentFrame;
	private int direction;

	public Model(final WireModel wireModel) {
		this.wireModel = wireModel;
		this.hardpointCount = wireModel.getHardpointCount();
		this.direction = 0;
	}

	public void setFrame(final int frameId) {
		currentFrame = frameId;
	}

	public void setDirection(final int direction) {
		this.direction = direction;
	}

	public WireModelFrame getCurrentFrame() {
		return wireModel.getFrame(currentFrame, direction);
	}

	public int getHardpointCount() {
		return hardpointCount;
	}
}
