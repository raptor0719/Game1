package raptor.engine.collision.geometry;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.util.geometry.Circle;
import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.Triangle;
import raptor.engine.util.geometry.api.IPoint;

public class CollisionTriangle implements ICollisionShape {
	private Triangle collisionTriangle;

	public CollisionTriangle(final Triangle collisionTriangle) {
		this.collisionTriangle = collisionTriangle;
	}

	@Override
	public boolean collidesWithTriangle(final CollisionTriangle t) {
		for (final IPoint p : t.getCollision().getPoints())
			if (collisionTriangle.containsPoint(p))
				return true;
		for (final IPoint p : collisionTriangle.getPoints())
			if (t.getCollision().containsPoint(p))
				return true;
		for (final LineSegment ls : collisionTriangle.getLines())
			if (t.getCollision().isIntersectedByLine(ls))
				return true;
		return false;
	}

	@Override
	public boolean collidesWithCircle(final CollisionCircle c) {
		for (final IPoint p : collisionTriangle.getPoints())
			if (Circle.containsPoint(c.getCollision(), p))
				return true;
		return false;
	}

	@Override
	public boolean collidesWithRectangle(final CollisionRectangle r) {
		for (final IPoint inputPoint : r.getCollision().getPoints())
			if (collisionTriangle.containsPoint(inputPoint))
				return true;

		for (final IPoint thisPoint : collisionTriangle.getPoints())
			if (r.getCollision().containsPoint(thisPoint))
				return true;

		for (final LineSegment inputSegment : r.getCollision().getLineSegments())
			if (collisionTriangle.isIntersectedByLine(inputSegment))
				return true;

		return false;
	}

	public void setCollision(final Triangle collisionTriangle) {
		this.collisionTriangle = collisionTriangle;
	}

	public Triangle getCollision() {
		return collisionTriangle;
	}
}
