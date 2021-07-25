package raptor.engine.event.events;

import raptor.engine.event.Event;
import raptor.engine.game.Terrain;
import raptor.engine.game.entity.IEntity;

public class TerrainCollisionEvent extends Event {
	public TerrainCollisionEvent(final IEntity collidingEntity, final Terrain collidingTerrain) {
		super(Event.TERRAIN_COLLISION);
		this.setProperty(Event.COLLIDING_ENTITY, collidingEntity);
		this.setProperty(Event.COLLIDING_TERRAIN, collidingTerrain);
	}
}
