package raptor.engine.model;

import java.util.Map;

public class DirectionalObjectManager<T> {
	private final Map<Direction, T> mappings;

	public DirectionalObjectManager(final Map<Direction, T> mappings) {
		for (final Direction d : Direction.values())
			if (!mappings.containsKey(d))
				throw new RuntimeException("Direction " + d.name() + " was no represented.");

		this.mappings = mappings;
	}

	public T get(final Direction direction) {
		return mappings.get(direction);
	}

	public void set(final T obj, final Direction direction) {
		mappings.put(direction, obj);
	}
}
