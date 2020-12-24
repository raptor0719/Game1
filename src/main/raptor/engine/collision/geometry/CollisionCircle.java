package raptor.engine.collision.geometry;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.util.geometry.Circle;
import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.api.ICircle;
import raptor.engine.util.geometry.api.ILineSegment;
import raptor.engine.util.geometry.api.IPoint;

public class CollisionCircle implements ICollisionShape {
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

	@Override
	public boolean collidesWithCircle(final CollisionCircle c) {
		final double distance = LineSegment.length(collisionCircle.getOrigin().getX(), collisionCircle.getOrigin().getY(),
				c.getCollision().getOrigin().getX(), c.getCollision().getOrigin().getY());
		return distance < (c.getCollision().getRadius() + collisionCircle.getRadius());
	}

	@Override
	public boolean collidesWithRectangle(final CollisionRectangle r) {
		for (final ILineSegment collisionSegment : r.getCollision().getLineSegments()) {
			if (Circle.isIntersectedByLine(collisionCircle.getOrigin().getX(), collisionCircle.getOrigin().getY(), collisionCircle.getRadius(),
					collisionSegment.getStart().getX(), collisionSegment.getStart().getY(), collisionSegment.getEnd().getX(), collisionSegment.getEnd().getY())) {
				return true;
			}
		}

		return false;
	}

	public void setCollision(final Circle collisionCircle) {
		this.collisionCircle = collisionCircle;
	}

	public ICircle getCollision() {
		return collisionCircle;
	}
}
