package raptor.engine.event;

public interface IEventDestination {
	void send(IEvent event);
}
