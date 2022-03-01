package raptor.engine.model;

import java.util.HashMap;
import java.util.Map;

public class SpriteModel  {
	private final Map<String, SpriteCollection> sprites;

	public SpriteModel() {
		this.sprites = new HashMap<>();
	}

	public boolean isMapped(final String hardpointName) {
		return sprites.containsKey(hardpointName);
	}

	public void addMapping(final String hardpointName) {
		if (sprites.containsKey(hardpointName))
			return;
		sprites.put(hardpointName, new SpriteCollection());
	}

	public SpriteCollection getSpriteCollection(final String hardpointName) {
		return sprites.get(hardpointName);
	}

	public void unmap(final String hardpointName) {
		sprites.remove(hardpointName);
	}
}
