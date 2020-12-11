package raptor.engine.collision.api;

import raptor.engine.game.entity.IEntity;

public interface ICollideable {
	void onCollision(IEntity collidingEntity);
}
