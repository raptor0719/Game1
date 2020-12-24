package raptor.engine.collision.geometry;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.util.geometry.Circle;
import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.api.ILineSegment;
import raptor.engine.util.geometry.api.IPoint;
import raptor.engine.util.geometry.api.IRectangle;

public class CollisionRectangle implements ICollisionShape {
	private IRectangle collision;

	@Override
	public boolean collidesWithTriangle(final CollisionTriangle t) {
		for (final IPoint trianglePoint : t.getCollision().getPoints())
			if (collision.containsPoint(trianglePoint))
				return true;

		for (final IPoint rectanglePoint : collision.getPoints())
			if (t.getCollision().containsPoint(rectanglePoint))
				return true;

		for (final LineSegment rectangleSegment : collision.getLineSegments())
			for (final LineSegment triangleSegment : t.getCollision().getLines())
				if (rectangleSegment.intersectsWith(triangleSegment))
					return true;

		return false;
	}

	@Override
	public boolean collidesWithCircle(final CollisionCircle c) {
		for (final ILineSegment collisionSegment : collision.getLineSegments()) {
			if (Circle.isIntersectedByLine(c.getCollision().getOrigin().getX(), c.getCollision().getOrigin().getY(), c.getCollision().getRadius(),
					collisionSegment.getStart().getX(), collisionSegment.getStart().getY(), collisionSegment.getEnd().getX(), collisionSegment.getEnd().getY())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean collidesWithRectangle(final CollisionRectangle r) {
		for (final IPoint thisPoint : collision.getPoints())
			if (r.getCollision().containsPoint(thisPoint))
				return true;

		for (final IPoint inputPoint : r.getCollision().getPoints())
			if (collision.containsPoint(inputPoint))
				return true;

		for (final LineSegment thisSegment : collision.getLineSegments())
			for (final LineSegment inputSegment : r.getCollision().getLineSegments())
				if (thisSegment.intersectsWith(inputSegment))
					return true;

		return false;
	}

	public void setCollision(final IRectangle newCollision) {
		this.collision = newCollision;
	}

	public IRectangle getCollision() {
		return collision;
	}
}
