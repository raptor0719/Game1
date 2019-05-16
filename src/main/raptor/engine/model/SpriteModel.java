package raptor.engine.model;

import java.util.List;

public class SpriteModel {
	private final List<Sprite> sprites;

	public SpriteModel(final List<Sprite> sprites) {
		this.sprites = sprites;
	}

	public Sprite getSprite(final int direction) {
		return sprites.get(direction);
	}
}
