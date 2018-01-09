package raptor.engine.logical.entity.projectile.lifecycle.api;

public interface IProjectileLifecycle {
	public boolean isExpired(final int collisionCount);
}
