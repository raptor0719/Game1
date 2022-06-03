package raptor.engine.model;

import java.util.Map;

public class DirectionalSprite {
	private final Map<Direction, Sprite> sprites;

	public DirectionalSprite(final String name, final Map<Direction, Sprite> sprites) {
		for (final Direction d : Direction.values())
			if (!sprites.containsKey(d) || sprites.get(d) == null)
				throw new IllegalArgumentException(String.format("Sprite must contain a sprite for each direction. %s was missing.", d.name()));

		this.sprites = sprites;
	}

	public Sprite getSprite(final Direction direction) {
		return sprites.get(direction);
	}
}
