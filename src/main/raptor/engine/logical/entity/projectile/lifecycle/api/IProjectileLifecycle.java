package raptor.engine.logical.entity.projectile.lifecycle.api;

import raptor.engine.logical.entity.projectile.Projectile;

public interface IProjectileLifecycle {
	public boolean isExpired(final Projectile projectile);
}
