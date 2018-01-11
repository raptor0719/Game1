package raptor.engine.logical.entity.projectile.collision.api;

import raptor.engine.logical.entity.projectile.Projectile;

public interface IProjectileCollision {
	public void collide(final Projectile projectile);
	public int getCollisionCount();
}
