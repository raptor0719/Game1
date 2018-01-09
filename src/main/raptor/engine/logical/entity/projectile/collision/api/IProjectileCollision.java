package raptor.engine.logical.entity.projectile.collision.api;

import raptor.engine.util.geometry.Point;

public interface IProjectileCollision {
	public void collide(final Point currentPosition);
	public int getCollisionCount();
}
