package raptor.engine.game.entity;

import raptor.engine.display.render.IDrawable;
import raptor.engine.logical.collision.api.ICollideable;
import raptor.engine.logical.collision.api.ICollisionShape;

public interface IEntity extends ICollideable, IDrawable {
	int getId();
	void update();
	int getX();
	int getY();
	ICollisionShape getCollision();
}
