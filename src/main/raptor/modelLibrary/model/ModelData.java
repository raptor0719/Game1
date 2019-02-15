package raptor.modelLibrary.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ModelData {
	private final Map<Integer, Animation> animations;
	private final List<Sprite> defaultSprites;
	private final Frame defaultFrame;
	private final int hardpointCount;

	public ModelData(final Map<Integer, Animation> animations, final List<Sprite> defaultSprites, final Frame defaultFrame) {
		this.animations = Collections.unmodifiableMap(animations);
		this.defaultSprites = Collections.unmodifiableList(defaultSprites);
		this.defaultFrame = defaultFrame;
		this.hardpointCount = defaultSprites.size();
	}

	public Map<Integer, Animation> getAnimations() {
		return animations;
	}

	public List<Sprite> getDefaultSprites() {
		return defaultSprites;
	}

	public Frame getDefaultFrame() {
		return defaultFrame;
	}

	public int getHardpointCount() {
		return hardpointCount;
	}
}
