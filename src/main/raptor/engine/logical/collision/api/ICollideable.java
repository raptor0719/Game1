package raptor.engine.logical.collision.api;

import raptor.engine.logical.collision.geometry.CollisionTriangle;

public interface ICollideable {
	boolean collidesWithTriangle(CollisionTriangle t);
}
