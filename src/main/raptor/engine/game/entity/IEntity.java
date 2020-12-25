package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollideable;
import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.collision.physics.IPhysicsCollideable;
import raptor.engine.display.render.IDrawable;

public interface IEntity extends ICollideable, IPhysicsCollideable, IDrawable {
	long getId();
	void update();
	int getX();
	int getY();
	void setX(int newX);
	void setY(int newY);
	ICollisionShape getCollision();
}
