package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.display.render.IDrawable;

public interface IEntity extends IDrawable {
	long getId();
	String getName();
	void update();
	int getX();
	int getY();
	void setX(int newX);
	void setY(int newY);
	boolean hasCollision();
	ICollisionShape getCollision();
}
