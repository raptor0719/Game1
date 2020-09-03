package raptor.engine.game.entity;

import raptor.engine.display.render.IGraphics;
import raptor.engine.logical.collision.api.ICollideable;

public interface IEntity {
	int getId();
	void update();
	void draw(IGraphics graphics);
	int getX();
	int getY();
	ICollideable getCollision();
}
