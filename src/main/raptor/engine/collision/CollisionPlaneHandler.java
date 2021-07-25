package raptor.engine.collision;

import raptor.engine.event.IEventBroker;
import raptor.engine.event.events.EntityCollisionEvent;
import raptor.engine.event.events.TerrainCollisionEvent;
import raptor.engine.game.Terrain;
import raptor.engine.game.entity.IEntity;

public class CollisionPlaneHandler {
	private final IEventBroker eventBroker;

	public CollisionPlaneHandler(final IEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}

	public void handleEntityCollision(final IEntity a, final IEntity b) {
		eventBroker.emit(new EntityCollisionEvent(a, b));
	}

	public void handleTerrainCollision(final IEntity a, final Terrain b) {
		eventBroker.emit(new TerrainCollisionEvent(a, b));
	}
}
