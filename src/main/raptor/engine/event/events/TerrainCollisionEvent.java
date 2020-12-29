package raptor.engine.event.events;

import raptor.engine.event.Event;
import raptor.engine.game.Terrain;

public class TerrainCollisionEvent extends Event {
	public TerrainCollisionEvent(final Terrain collidingTerrain) {
		super(Event.TERRAIN_COLLISION);
		this.setProperty(Event.COLLIDING_TERRAIN, collidingTerrain);
	}
}
