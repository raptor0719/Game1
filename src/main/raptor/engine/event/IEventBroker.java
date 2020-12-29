package raptor.engine.event;

public interface IEventBroker {
	void emit(IEvent event);
	void trigger(int entityId, IEvent event);
	void distribute();
	IEventSource register(int entityId);
	void register(int entityId, String eventName);
	void unregister(int entityId);
	void unregister(int entityId, String eventName);
	boolean isRegistered(int entityId);
	boolean isRegistered(int entityId, String eventName);
	boolean brokersEvent(String eventName);
}
