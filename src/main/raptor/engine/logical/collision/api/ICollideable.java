package raptor.engine.logical.collision.api;

import raptor.engine.game.entity.IEntity;

public interface ICollideable {
	void onCollision(IEntity collidingEntity);
}
