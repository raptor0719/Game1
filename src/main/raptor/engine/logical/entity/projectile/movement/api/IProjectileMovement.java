package raptor.engine.logical.entity.projectile.movement.api;

import raptor.engine.logical.entity.projectile.Projectile;
import raptor.engine.util.geometry.Point;

public interface IProjectileMovement {
	public Point calculateNewPosition(final Projectile projectile);
}
