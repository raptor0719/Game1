package raptor.modelLibrary.model;

import java.util.Collections;
import java.util.Map;

public class ModelData {
	private final Map<Integer, Animation> animations;
	private final Frame defaultFrame;
	private final int hardpointCount;

	public ModelData(final Map<Integer, Animation> animations, final Frame defaultFrame) {
		this.animations = Collections.unmodifiableMap(animations);
		this.defaultFrame = defaultFrame;
		// FIXME: this looks really bad
		this.hardpointCount = animations.entrySet().iterator().next().getValue().getFrames().get(0).get(0).getHardpointPositions().size();
	}

	public Map<Integer, Animation> getAnimations() {
		return animations;
	}

	public Frame getDefaultFrame() {
		return defaultFrame;
	}

	public int getHardpointCount() {
		return hardpointCount;
	}
}
