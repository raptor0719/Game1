package raptor.engine.game;

import java.util.LinkedList;
import java.util.Queue;

public class IdProvider implements IIdProvider {
	private final Queue<Long> available;
	private long current;

	public IdProvider() {
		available = new LinkedList<Long>();
		current = 0;
	}

	@Override
	public long get() {
		if (!available.isEmpty())
			return available.poll();
		final long returnValue = current;
		current++;
		return returnValue;
	}

	@Override
	public void free(final long id) {
		available.offer(id);
	}
}
