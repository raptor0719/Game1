package raptor.engine.collision.api;

import raptor.engine.collision.geometry.CollisionCircle;
import raptor.engine.collision.geometry.CollisionTriangle;

public interface ICollisionShape {
	boolean collidesWithTriangle(CollisionTriangle t);
	boolean collidesWithCircle(CollisionCircle c);
}
