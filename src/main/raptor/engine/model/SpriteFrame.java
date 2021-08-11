package raptor.engine.model;

import java.util.Map;

public class SpriteFrame {
	private final Map<String, Sprite> sprites;
	private final int count;

	public SpriteFrame(final Map<String, Sprite> sprites) {
		this.sprites = sprites;
		this.count = sprites.size();
	}

	public Sprite getSprite(final String positionName) {
		return sprites.get(positionName);
	}

	public int getCount() {
		return count;
	}
}
