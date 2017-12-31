package raptor.engine.logical.entity.projectile.movement.types;

import raptor.engine.logical.entity.projectile.movement.api.IProjectileMovement;
import raptor.engine.util.geometry.DoubleVector;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Vector;

public class LinearProjectileMovement implements IProjectileMovement {
	private double fracX;
	private double fracY;

	public LinearProjectileMovement() {
		fracX = 0;
		fracY = 0;
	}

	@Override
	public Point calculateNewPosition(final Point currentPos, final Vector direction, final double amount) {
		final DoubleVector V = direction.unitVector().scale(amount);
		final DoubleVector VplusFrac = new DoubleVector(V.getX() + fracX, V.getY() + fracY);

		fracX = VplusFrac.getX() % 1;
		fracY = VplusFrac.getY() % 1;

		Vector MOVE = new Vector((int)VplusFrac.getX(), (int)VplusFrac.getY());

		return new Point(currentPos.getX() + MOVE.getX(), currentPos.getY() + MOVE.getY());
	}
}
