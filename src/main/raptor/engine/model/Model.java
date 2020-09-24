package raptor.engine.model;

import java.util.AbstractList;

public class Model {
	private final WireModel wireModel;

	private int direction;

	private AbstractList<Integer> currentAnimation;
	private int currentFrame;

	public Model(final WireModel wireModel) {
		this.wireModel = wireModel;
		this.direction = 0;
		this.currentAnimation = null;
		this.currentFrame = -1;
	}

	public void setAnimation(final int animId, final int totalFrames) {
		currentAnimation = wireModel.getAnimationFrames(animId, totalFrames);
		currentFrame = 0;
	}

	public void setDirection(final int direction) {
		this.direction = direction;
	}

	public int advanceFrame() {
		if (currentFrame < 0)
			return 0;
		currentFrame++;
		return currentAnimation.size() - currentFrame - 1;
	}

	public HardpointPosition getHardpointPosition(final int hardpoint) {
		return wireModel.getFrame(currentAnimation.get(currentFrame), direction).getHardpointPositions()[hardpoint];
	}

	public int getHardpointCount() {
		return wireModel.getHardpointCount();
	}
}
