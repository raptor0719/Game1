package raptor.engine.event.events;

import raptor.engine.event.Event;
import raptor.engine.nav.api.INavigator;

public class TerrainCollisionEvent extends Event {
	public TerrainCollisionEvent(final INavigator collidingTerrain) {
		super(Event.TERRAIN_COLLISION);
		this.setProperty(Event.COLLIDING_TERRAIN, collidingTerrain);
	}
}
