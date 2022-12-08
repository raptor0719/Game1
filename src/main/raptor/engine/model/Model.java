package raptor.engine.model;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.geometry.api.IPoint;

public class Model implements IDrawable {
	private final Sprite sprite;

	private IPoint position;

	public Model(final Sprite sprite) {
		this.sprite = sprite;
	}

	public void setPosition(final IPoint reference) {
		this.position = reference;
	}

	@Override
	public void draw(final IGraphics graphics) {
		graphics.drawSprite(sprite, position.getX(), position.getY(), 0);
	}
}
