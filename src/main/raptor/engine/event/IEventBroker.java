package raptor.engine.event;

public interface IEventBroker {
	void emit(IEvent event);
	void distribute();
	IEventSource register(int entityId);
	void register(int entityId, String eventName);
	void unregister(int entityId);
	void unregister(int entityId, String eventName);
}
