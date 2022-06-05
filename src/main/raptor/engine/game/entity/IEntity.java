package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.display.render.IDrawable;
import raptor.engine.game.Terrain;

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
	int getWidth();
	int getHeight();
	boolean hasCollision(long planeId);
	ICollisionShape getCollision(long planeId);
	void handleEntityCollision(final long planeId, final IEntity entity);
	void handleTerrainCollision(final long planeId, final Terrain terrain);
}
