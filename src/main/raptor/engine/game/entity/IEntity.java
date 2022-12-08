package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.display.render.IDrawable;

public interface IEntity extends IDrawable {
	long getId();
	String getName();
	void update(double tickCount);
	int getX();
	int getY();
	void setX(int newX);
	void setY(int newY);
	int getWidth();
	int getHeight();
	boolean hasCollision(long planeId);
	ICollisionShape getCollision(long planeId);
}
