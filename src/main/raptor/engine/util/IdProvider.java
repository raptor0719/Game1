package raptor.engine.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class IdProvider implements IIdProvider {
	private final Queue<Long> available;
	private final Set<Long> availableSet;
	private long nextAvailable;

	public IdProvider() {
		available = new LinkedList<Long>();
		availableSet = new HashSet<Long>();
		nextAvailable = 0;
	}

	@Override
	public long get() {
		if (!availableSet.isEmpty()) {
			final Long id = available.poll();
			availableSet.remove(id);
			return id;
		}
		final long returnValue = nextAvailable;
		nextAvailable++;
		return returnValue;
	}

	@Override
	public long allocate(final long request) {
		if (request < nextAvailable && !availableSet.contains(request))
			throw new IllegalArgumentException(String.format("Attempted allocation of used id: %s", request));

		if (request == nextAvailable)
			return get();

		if (availableSet.contains(request)) {
			availableSet.remove(request);
			available.remove(request);
			return request;
		}

		for (long i = nextAvailable; i < request; i++) {
			availableSet.add(i);
			available.offer(i);
		}
		nextAvailable = request + 1;
		return request;
	}

	@Override
	public void free(final long id) {
		if (availableSet.contains(id))
			return;
		availableSet.add(id);
		available.offer(id);
	}

	@Override
	public void freeAll() {
		availableSet.clear();
		available.clear();
		nextAvailable = 0;
	}

	@Override
	public boolean isFree(final long value) {
		return value >= nextAvailable || availableSet.contains(value);
	}
}
