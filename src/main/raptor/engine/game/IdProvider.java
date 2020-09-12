package raptor.engine.game;

import java.util.LinkedList;
import java.util.Queue;

public class IdProvider implements IIdProvider {
	private final Queue<Long> available;
	private long nextAvailable;

	public IdProvider() {
		available = new LinkedList<Long>();
		nextAvailable = 0;
	}

	@Override
	public long get() {
		if (!available.isEmpty())
			return available.poll();
		final long returnValue = nextAvailable;
		nextAvailable++;
		return returnValue;
	}

	@Override
	public long allocate(final long request) {
		if (request < nextAvailable || !available.contains(request))
			throw new IllegalArgumentException(String.format("Attempted allocation of used id: %s", request));

		if (request == nextAvailable)
			return get();

		if (available.contains(request)) {
			available.remove(request);
			return request;
		}

		for (long i = nextAvailable; i < request; i++)
			available.offer(i);
		nextAvailable = request + 1;
		return request;
	}

	@Override
	public void free(final long id) {
		available.offer(id);
	}

	@Override
	public void freeAll() {
		available.clear();
		nextAvailable = 0;
	}

	@Override
	public boolean isFree(final long value) {
		return value >= nextAvailable || available.contains(value);
	}
}
