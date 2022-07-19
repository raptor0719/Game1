package raptor.engine.collision.api;

import raptor.engine.game.Terrain;
import raptor.engine.game.entity.IEntity;

public interface ICollisionPlaneHandler {
	void handleEntityCollision(long planeId, IEntity a, IEntity b);
	void handleTerrainCollision(long planeId, IEntity entity, Terrain terrain);
}
