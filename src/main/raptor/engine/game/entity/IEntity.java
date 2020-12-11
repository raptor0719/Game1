package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollideable;
import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.display.render.IDrawable;

public interface IEntity extends ICollideable, IDrawable {
	long getId();
	void update();
	int getX();
	int getY();
	ICollisionShape getCollision();
}
