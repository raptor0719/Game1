package raptor.engine.model;

import java.util.Map;

public class DirectionalSprite {
	private final String name;
	private final Map<Direction, Sprite> sprites;

	public DirectionalSprite(final String name, final Map<Direction, Sprite> sprites) {
		for (final Direction d : Direction.values())
			if (!sprites.containsKey(d) || sprites.get(d) == null)
				throw new IllegalArgumentException(String.format("Sprite must contain a sprite for each direction. %s was missing.", d.name()));

		this.name = name;
		this.sprites = sprites;
	}

	public Sprite getSprite(final Direction direction) {
		return sprites.get(direction);
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectionalSprite other = (DirectionalSprite) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
