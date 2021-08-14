package raptor.engine.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class HardpointSpriteCollection {
	private final int frameCount;
	private final List<SpriteCollection> spriteCollections;

	public HardpointSpriteCollection(final int frameCount) {
		this.frameCount = frameCount;
		this.spriteCollections = new LinkedList<>();
	}

	public void addCollectionOnTop(final SpriteCollection newCollection) {
		addCleanup(newCollection);
		spriteCollections.add(newCollection);
	}

	public void addCollectionOnBottom(final SpriteCollection newCollection) {
		addCleanup(newCollection);
		spriteCollections.add(0, newCollection);
	}

	public void addCollectionAfter(final SpriteCollection newCollection, final String targetCollection) {
		addCleanup(newCollection);

		final int index = indexOfCollection(targetCollection);

		if (index < 0)
			throw new IllegalArgumentException("Target collection did not exist. Target Collection: " + targetCollection);

		spriteCollections.add(index + 1, newCollection);
	}

	public void addCollectionBefore(final SpriteCollection newCollection, final String targetCollection) {
		addCleanup(newCollection);

		final int index = indexOfCollection(targetCollection);

		if (index < 0)
			throw new IllegalArgumentException("Target collection did not exist. Target Collection: " + targetCollection);

		spriteCollections.add(index, newCollection);
	}

	public SpriteCollection removeCollection(final String name) {
		for (final SpriteCollection c : spriteCollections) {
			if (c.getName().equals(name)) {
				spriteCollections.remove(c);
				return c;
			}
		}
		return null;
	}

	public SpriteCollection removeCollection(final SpriteCollection collection) {
		final boolean removed = spriteCollections.remove(collection);
		return (removed) ? collection : null;
	}

	public boolean hasCollection(final String name) {
		for (final SpriteCollection c : spriteCollections) {
			if (c.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasCollection(final SpriteCollection collection) {
		return spriteCollections.contains(collection);
	}

	public Collection<SpriteCollection> getCollections() {
		return spriteCollections;
	}

	private void addCleanup(final SpriteCollection newCollection) {
		if (newCollection.getCount() != frameCount)
			throw new IllegalArgumentException("New SpriteCollection must have exact number of logical assets. Was given: " + newCollection.getCount() + ", but expected: " + frameCount);

		spriteCollections.remove(newCollection);
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
