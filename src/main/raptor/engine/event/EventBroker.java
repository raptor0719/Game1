package raptor.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class EventBroker implements IEventBroker {
	private final Queue<IEvent> eventQueue;
	private final Map<Long, IEventDestination> entityRegistration;
	private final Map<String, List<Long>> eventRegistration;

	private final Queue<Long> triggerEntityIds;
	private final Queue<IEvent> triggerEvents;

	public EventBroker() {
		this.eventQueue = new LinkedList<IEvent>();
		this.entityRegistration = new HashMap<Long, IEventDestination>();
		this.eventRegistration = new HashMap<String, List<Long>>();

		this.triggerEntityIds = new LinkedList<Long>();
		this.triggerEvents = new LinkedList<IEvent>();
	}

	@Override
	public void emit(final IEvent event) {
		eventQueue.offer(event);
	}

	@Override
	public void trigger(final long entityId, final IEvent event) {
		if (!entityRegistration.containsKey(entityId))
			return;

		triggerEntityIds.offer(entityId);
		triggerEvents.offer(event);
	}

	@Override
	public void distribute() {
		while(!eventQueue.isEmpty()) {
			final IEvent event = eventQueue.poll();
			final List<Long> registrees = eventRegistration.get(event.getName());
			if (registrees == null || registrees.size() <= 0)
				continue;
			for (final Long id : registrees)
				entityRegistration.get(id).send(event);
		}

		while(!triggerEntityIds.isEmpty()) {
			final long triggerEntityId = triggerEntityIds.poll();
			final IEvent triggerEvent = triggerEvents.poll();
			if (!entityRegistration.containsKey(triggerEntityId))
				continue;
			entityRegistration.get(triggerEntityId).send(triggerEvent);
		}
	}

	@Override
	public IEventSource register(final long entityId) {
		final EventExchange exchange = new EventExchange();
		entityRegistration.put(entityId, exchange);
		return exchange;
	}

	@Override
	public void register(final long entityId, final String eventName) {
		if (!isRegistered(entityId))
			register(entityId);
		if (!brokersEvent(eventName))
			eventRegistration.put(eventName, new ArrayList<Long>());
		final List<Long> registrees = eventRegistration.get(eventName);
		if (!registrees.contains(entityId))
			registrees.add(entityId);
	}

	@Override
	public void unregister(final long entityId) {
		for (final List<Long> registrees : eventRegistration.values())
			registrees.remove(entityId);
		entityRegistration.remove(entityId);
	}

	@Override
	public void unregister(final long entityId, final String eventName) {
		if (!brokersEvent(eventName))
			return;
		final List<Long> registrees = eventRegistration.get(eventName);
		registrees.remove(entityId);
	}

	@Override
	public boolean isRegistered(final long entityId) {
		return entityRegistration.containsKey(entityId);
	}

	@Override
	public boolean isRegistered(final long entityId, final String eventName) {
		if (!isRegistered(entityId) || !brokersEvent(eventName))
			return false;
		final List<Long> registrees = eventRegistration.get(eventName);
		return registrees.contains(entityId);
	}

	@Override
	public boolean brokersEvent(final String eventName) {
		return eventRegistration.containsKey(eventName);
	}

}
