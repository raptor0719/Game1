package raptor.engine.model;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.geometry.api.IPoint;

public class Model implements IDrawable {
	private final WireModel wireModel;
	private final int hardpointCount;
	private final SpriteModel spriteModel;

	private String currentFrame;
	private Direction direction;

	private IPoint position;

	public Model(final WireModel wireModel, final SpriteModel spriteModel) {
		this.wireModel = wireModel;
		this.hardpointCount = wireModel.getHardpointCount();

		this.spriteModel = spriteModel;

		this.direction = Direction.RIGHT;
	}

	public void setFrame(final String frameName) {
		this.currentFrame = frameName;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	public Hardpoint getHardpoint(final String name) {
		return wireModel.getFrame(currentFrame, direction).getHardpoint(name);
	}

	public SpriteModel getSpriteModel() {
		return spriteModel;
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	public void setPosition(final IPoint reference) {
		this.position = reference;
	}

	@Override
	public void draw(final IGraphics graphics) {
		final WireModelFrame wire = wireModel.getFrame(currentFrame, direction);

		for (final Hardpoint h : wire.getSortedHardpoints()) {
			final DirectionalSprite spriteCollection = spriteModel.getSpriteCollection(h.getName()).getSprite(h.getPhase());

			if (spriteCollection == null)
				continue;

			final Sprite sprite = spriteCollection.getSprite(direction);

			graphics.drawSprite(sprite, position.getX() + h.getX(), position.getY() - h.getY(), h.getRotation());
		}
	}
}
