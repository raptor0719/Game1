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

	public void advanceFrame() {
		currentFrame++;
	}

	public int remainingFramesInAnimation() {
		if (currentFrame < 0)
			return 0;
		return currentAnimation.size() - currentFrame - 1;
	}

	public int getCurrentFrame() {
		return currentAnimation.get(currentFrame);
	}

	public boolean isFirstFrameOfCurrentFrame() {
		if (currentAnimation == null || currentAnimation.size() <= 0)
			return false;
		if (currentFrame == 0)
			return true;
		return currentAnimation.get(currentFrame - 1) != currentFrame;
	}

	public boolean isLastFrameOfCurrentFrame() {
		if (currentAnimation == null || currentAnimation.size() <= 0)
			return false;
		if (remainingFramesInAnimation() <= 0)
			return true;
		return currentAnimation.get(currentFrame + 1) != currentFrame;
	}

	public int remainingFramesForCurrentFrame() {
		if (remainingFramesInAnimation() <= 0)
			return 0;

		int remaining = 0;
		for (int i = currentFrame + 1; i < currentAnimation.size() && currentAnimation.get(i) != currentFrame; i++)
			remaining++;

		return remaining;
	}

	public HardpointPosition getHardpointPosition(final int hardpoint) {
		return wireModel.getFrame(currentAnimation.get(currentFrame), direction).getHardpointPositions()[hardpoint];
	}

	public int getHardpointCount() {
		return wireModel.getHardpointCount();
	}
}
