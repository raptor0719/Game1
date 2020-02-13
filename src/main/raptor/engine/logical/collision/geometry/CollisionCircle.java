package raptor.engine.logical.collision.geometry;

import raptor.engine.logical.collision.api.ICollideable;
import raptor.engine.util.geometry.Circle;

public class CollisionCircle implements ICollideable {
	private Circle collisionCircle;

	public CollisionCircle(final Circle collisionCircle) {

	}

	@Override
	public boolean collidesWithTriangle(final CollisionTriangle t) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCollision(final Circle collisionCircle) {
		this.collisionCircle = collisionCircle;
	}

	public Circle getCollision() {
		return collisionCircle;
	}
}
