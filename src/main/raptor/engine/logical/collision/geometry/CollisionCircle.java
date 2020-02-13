package raptor.engine.logical.collision.geometry;

import raptor.engine.logical.collision.api.ICollideable;
import raptor.engine.util.geometry.Circle;
import raptor.engine.util.geometry.api.ICircle;
import raptor.engine.util.geometry.api.IPoint;

public class CollisionCircle implements ICollideable {
	private Circle collisionCircle;

	public CollisionCircle(final Circle collisionCircle) {
		this.collisionCircle = collisionCircle;
	}

	@Override
	public boolean collidesWithTriangle(final CollisionTriangle t) {
		for (final IPoint p : t.getCollision().getPoints())
			if (Circle.containsPoint(collisionCircle, p))
				return true;
		return false;
	}

	public void setCollision(final Circle collisionCircle) {
		this.collisionCircle = collisionCircle;
	}

	public ICircle getCollision() {
		return collisionCircle;
	}
}
