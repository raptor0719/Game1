package raptor.engine.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class EventBroker implements IEventBroker {
	private final Queue<IEvent> eventQueue;
	private final Map<Integer, IEventDestination> entityRegistration;
	private final Map<String, List<Integer>> eventRegistration;

	public EventBroker() {
		this.eventQueue = new LinkedList<IEvent>();
		this.entityRegistration = new HashMap<Integer, IEventDestination>();
		this.eventRegistration = new HashMap<String, List<Integer>>();
	}

	@Override
	public void emit(final IEvent event) {
		eventQueue.offer(event);
	}

	@Override
	public void distribute() {
		while(!eventQueue.isEmpty()) {
			final IEvent event = eventQueue.poll();

			final List<Integer> registrees = eventRegistration.get(event.getName());

			if (registrees == null)
				continue;

			for (final Integer id : registrees)
				entityRegistration.get(id).send(event);
		}
	}

	@Override
	public IEventSource register(final int entityId) {
		final EventExchange exchange = new EventExchange();
		entityRegistration.put(entityId, exchange);
		return exchange;
	}

	@Override
	public void register(final int entityId, final String eventName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(final int entityId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(final int entityId, final String eventName) {
		// TODO Auto-generated method stub

	}

}
