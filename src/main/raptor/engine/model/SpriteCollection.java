package raptor.engine.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteCollection {
	private final Map<String, DirectionalSprite> phases;

	public SpriteCollection(final List<DirectionalSprite> phases) {
		this.phases = new HashMap<>();

		for (final DirectionalSprite sprite : phases)
			this.phases.put(sprite.getName(), sprite);
	}

	public DirectionalSprite getSprite(final String phase) {
		return phases.get(phase);
	}
}
