package raptor.engine.logical.entity.projectile.movement.api;

import raptor.engine.util.geometry.Point;

public interface IProjectileMovement {
	public Point calculateNewPosition(final Point currentPos);
}
