package raptor.engine.event;

public interface IEventBroker {
	void emit(IEvent event);
	void trigger(long entityId, IEvent event);
	void distribute();
	IEventSource register(long entityId);
	void register(long entityId, String eventName);
	void unregister(long entityId);
	void unregister(long entityId, String eventName);
	boolean isRegistered(long entityId);
	boolean isRegistered(long entityId, String eventName);
	boolean brokersEvent(String eventName);
}
