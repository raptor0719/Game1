package raptor.engine.model;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.geometry.api.IPoint;

public class Model implements IDrawable {
	private final WireModel wireModel;
	private final int hardpointCount;
	private final SpriteModel spriteModel;

	private int currentFrame;
	private Direction direction;

	private IPoint position;

	public Model(final WireModel wireModel, final SpriteModel spriteModel) {
		this.wireModel = wireModel;
		this.hardpointCount = wireModel.getHardpointCount();

		if (this.hardpointCount != spriteModel.getHardpointCount())
			throw new IllegalArgumentException("Wire model hardpoint count did not match sprite model hardpoint count");

		this.spriteModel = spriteModel;

		this.direction = Direction.NORTH;
	}

	public void setFrame(final int frameId) {
		currentFrame = frameId;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	public WireModelFrame getCurrentFrame() {
		return wireModel.getFrame(currentFrame, direction);
	}

	public SpriteFrame getCurrentSprites() {
		return spriteModel.getFrame(currentFrame, direction);
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	public void setPosition(final IPoint reference) {
		this.position = reference;
	}

	@Override
	public void draw(final IGraphics graphics) {
		final WireModelFrame wire = getCurrentFrame();
		final SpriteFrame visuals = getCurrentSprites();

		for (final IHardpoint h : wire.getSortedHardpoints()) {
			final Sprite sprite = visuals.getSprite(h.getName());
			final IPoint attach = sprite.getAttachPoint();

			final int x = position.getX() + h.getX() - attach.getX();
			final int y = position.getY() + h.getY() - attach.getY();

			graphics.drawImage(sprite.getImage(), x, y);
		}
	}
}
