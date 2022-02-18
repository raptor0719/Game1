package raptor.engine.collision;

import raptor.engine.game.Terrain;
import raptor.engine.game.entity.IEntity;

public class CollisionPlaneHandler {
	public void handleEntityCollision(final long planeId, final IEntity a, final IEntity b) {
		a.handleEntityCollision(planeId, b);
	}

	public void handleTerrainCollision(final long planeId, final IEntity a, final Terrain terrain) {
		a.handleTerrainCollision(planeId, terrain);
	}
}
