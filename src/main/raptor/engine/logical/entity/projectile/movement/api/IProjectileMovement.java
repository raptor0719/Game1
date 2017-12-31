package raptor.engine.logical.entity.projectile.movement.api;

import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Vector;

public interface IProjectileMovement {
	public Point calculateNewPosition(final Point currentPos, final Vector direction, final double amount);
}
