package raptor.engine.game.entity;

import raptor.engine.display.render.IGraphics;
import raptor.engine.logical.collision.api.ICollideable;
import raptor.engine.logical.collision.api.ICollisionShape;

public interface IEntity extends ICollideable {
	int getId();
	void update();
	void draw(IGraphics graphics);
	int getX();
	int getY();
	ICollisionShape getCollision();
}
