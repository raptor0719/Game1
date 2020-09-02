package raptor.engine.event;

import java.util.HashMap;
import java.util.Map;

public class Event implements IEvent {
	private final String name;
	private final Map<String, Object> properties;

	public Event(final String name) {
		this.name = name;
		this.properties = new HashMap<String, Object>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getProperty(final String propertyName) {
		return properties.get(propertyName);
	}

	public void setProperty(final String name, final Object value) {
		properties.put(name, value);
	}
}
