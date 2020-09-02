package raptor.engine.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class EventExchange implements IEventSource, IEventDestination {
	private final Queue<IEvent> eventQueue;

	public EventExchange() {
		this.eventQueue = new LinkedList<IEvent>();
	}

	@Override
	public void send(final IEvent event) {
		eventQueue.offer(event);
	}

	@Override
	public Iterator<IEvent> getEvents() {
		return eventQueue.iterator();
	}

}
