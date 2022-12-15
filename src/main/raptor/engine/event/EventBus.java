package raptor.engine.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventBus implements IEventBus {
	private final List<IEvent> newEvents;
	private final List<IEvent> currentEvents;

	public EventBus() {
		this.newEvents = new ArrayList<IEvent>();
		this.currentEvents = new ArrayList<IEvent>();
	}

	@Override
	public void emit(final IEvent event) {
		newEvents.add(event);
	}

	@Override
	public Iterator<IEvent> getCurrentEvents() {
		return currentEvents.iterator();
	}

	@Override
	public Iterator<IEvent> getCurrentEvents(final IEventFilter filter) {
		return new FilteringEventIterator(currentEvents.iterator(), filter);
	}

	public void update() {
		currentEvents.clear();
		currentEvents.addAll(newEvents);
		newEvents.clear();
	}
}
