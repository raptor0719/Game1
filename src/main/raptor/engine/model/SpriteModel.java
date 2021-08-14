package raptor.engine.model;

import java.util.HashMap;
import java.util.Map;

public class SpriteModel  {
	private final int frameCount;
	private final Map<String, HardpointSpriteCollection> sprites;

	public SpriteModel(final int frameCount) {
		this.frameCount = frameCount;
		this.sprites = new HashMap<>();
	}

	public boolean isMapped(final String hardpointName) {
		return sprites.containsKey(hardpointName);
	}

	public void addMapping(final String hardpointName) {
		if (sprites.containsKey(hardpointName))
			return;
		sprites.put(hardpointName, new HardpointSpriteCollection(frameCount));
	}

	public HardpointSpriteCollection getSpriteCollection(final String hardpointName) {
		return sprites.get(hardpointName);
	}

	public void unmap(final String hardpointName) {
		sprites.remove(hardpointName);
	}
}
