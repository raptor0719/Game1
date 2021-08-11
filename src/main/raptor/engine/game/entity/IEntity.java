package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.display.render.IDrawable;

public interface IEntity extends IDrawable {
	long getId();
	String getName();
	void update();
	int getX();
	int getY();
	int getFacingInDegrees();
	void setX(int newX);
	void setY(int newY);
	void setFacingInDegrees(int degrees);
	boolean hasCollision(long planeId);
	ICollisionShape getCollision(long planeId);
}
