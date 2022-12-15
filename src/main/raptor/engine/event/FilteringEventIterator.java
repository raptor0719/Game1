package raptor.engine.event;

import java.util.Iterator;

public class FilteringEventIterator implements Iterator<IEvent> {
	private final Iterator<IEvent> wrapped;
	private final IEventFilter filter;

	private IEvent next;

	public FilteringEventIterator(final Iterator<IEvent> wrapped, final IEventFilter filter) {
		this.wrapped = wrapped;
		this.filter = filter;

		this.next = null;
	}

	@Override
	public boolean hasNext() {
		swallowFiltered();
		return next != null;
	}

	@Override
	public IEvent next() {
		swallowFiltered();
		return next;
	}

	private void swallowFiltered() {
		this.next = null;

		if (!wrapped.hasNext())
			return;

		while (wrapped.hasNext()) {
			final IEvent event = wrapped.next();

			if (filter.filter(event)) {
				next = event;
				return;
			}
		}
	}
}
