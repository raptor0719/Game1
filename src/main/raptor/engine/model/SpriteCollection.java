package raptor.engine.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteCollection {
	private final Map<String, DirectionalSprite> phases;
	private final String name;

	public SpriteCollection(final String name, final List<DirectionalSprite> phases) {
		this.phases = new HashMap<>();

		for (final DirectionalSprite sprite : phases)
			this.phases.put(sprite.getName(), sprite);

		this.name = name;
	}

	public DirectionalSprite getSprite(final String phase) {
		return phases.get(phase);
	}

	public String getName() {
		return name;
	}
}
