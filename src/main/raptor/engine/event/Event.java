package raptor.engine.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Event implements IEvent {
	public static final String ENTITY_COLLISION = "entityCollision";
	public static final String TERRAIN_COLLISION = "terrainCollision";
	public static final Collection<String> EVENT_NAMES = new ArrayList<>();
	static {
		EVENT_NAMES.add(ENTITY_COLLISION);
		EVENT_NAMES.add(TERRAIN_COLLISION);
	}

	public static final String COLLIDING_ENTITY = "collidingEntity";
	public static final String COLLIDING_ENTITY_OTHER = "collidingEntityOther";
	public static final String COLLIDING_TERRAIN = "collidingTerrain";
	public static final Collection<String> EVENT_PROPERTY_NAMES = new ArrayList<>();
	static {
		EVENT_PROPERTY_NAMES.add(COLLIDING_ENTITY);
		EVENT_PROPERTY_NAMES.add(COLLIDING_ENTITY_OTHER);
		EVENT_PROPERTY_NAMES.add(COLLIDING_TERRAIN);
	}

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
