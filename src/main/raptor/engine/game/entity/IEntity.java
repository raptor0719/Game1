package raptor.engine.game.entity;

import raptor.engine.display.render.IGraphics;

public interface IEntity {
	void update();
	void draw(IGraphics graphics);
	int getX();
	int getY();
}
