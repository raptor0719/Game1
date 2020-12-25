package raptor.engine.collision.physics;

import raptor.engine.util.geometry.api.ICircle;

public interface IPhysicsCollideable {
	boolean isPhysicsEnabled();
	ICircle getPhysicsCollision();
	int getWeight();
}
