package raptor.engine.event;

public interface IEvent {
	String getName();
	Object getProperty(String propertyName);
}
