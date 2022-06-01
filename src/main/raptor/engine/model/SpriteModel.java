package raptor.engine.model;

import java.util.Map;

public class SpriteModel  {
	private final Map<String, SpriteCollection> sprites;

	public SpriteModel(final Map<String, SpriteCollection> sprites) {
		this.sprites = sprites;
	}

	public boolean isMapped(final String hardpointName) {
		return sprites.containsKey(hardpointName);
	}

	public SpriteCollection getSpriteCollection(final String hardpointName) {
		return sprites.get(hardpointName);
	}
}
