package raptor.engine.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SpriteCollection {
	private final List<DirectionalSprite> spriteCollections;

	public SpriteCollection() {
		this.spriteCollections = new LinkedList<>();
	}

	public void addCollectionOnTop(final DirectionalSprite newCollection) {
		spriteCollections.add(newCollection);
	}

	public void addCollectionOnBottom(final DirectionalSprite newCollection) {
		spriteCollections.add(0, newCollection);
	}

	public void addCollectionAfter(final DirectionalSprite newCollection, final String targetCollection) {
		final int index = indexOfCollection(targetCollection);

		if (index < 0)
			throw new IllegalArgumentException("Target collection did not exist. Target Collection: " + targetCollection);

		spriteCollections.add(index + 1, newCollection);
	}

	public void addCollectionBefore(final DirectionalSprite newCollection, final String targetCollection) {
		final int index = indexOfCollection(targetCollection);

		if (index < 0)
			throw new IllegalArgumentException("Target collection did not exist. Target Collection: " + targetCollection);

		spriteCollections.add(index, newCollection);
	}

	public DirectionalSprite removeCollection(final String name) {
		for (final DirectionalSprite c : spriteCollections) {
			if (c.getName().equals(name)) {
				spriteCollections.remove(c);
				return c;
			}
		}
		return null;
	}

	public DirectionalSprite removeCollection(final DirectionalSprite collection) {
		final boolean removed = spriteCollections.remove(collection);
		return (removed) ? collection : null;
	}

	public boolean hasCollection(final String name) {
		for (final DirectionalSprite c : spriteCollections) {
			if (c.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasCollection(final DirectionalSprite collection) {
		return spriteCollections.contains(collection);
	}

	public Collection<DirectionalSprite> getCollections() {
		return spriteCollections;
	}

	private int indexOfCollection(final String collectionName) {
		for (int i = 0; i < spriteCollections.size(); i++) {
			if (spriteCollections.get(i).getName().equals(collectionName)) {
				return i;
			}
		}
		return -1;
	}
}
