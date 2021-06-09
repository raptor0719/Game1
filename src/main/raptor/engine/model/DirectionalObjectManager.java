package raptor.engine.model;

import java.util.Map;

public class DirectionalObjectManager<T> {
	private final Map<Direction, T[]> mappings;

	public DirectionalObjectManager(final Map<Direction, T[]> mappings) {
		for (final Direction d : Direction.values())
			if (!mappings.containsKey(d))
				throw new RuntimeException("Direction " + d.name() + " was no represented.");

		this.mappings = mappings;
	}

	public T get(final int index, final Direction direction) {
		return mappings.get(direction)[index];
	}

	public void set(final T obj, final int index, final Direction direction) {
		mappings.get(direction)[index] = obj;
	}

	public int count() {
		return mappings.get(Direction.NORTH).length;
	}
}
