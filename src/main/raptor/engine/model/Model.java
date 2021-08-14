package raptor.engine.model;

import java.util.Collection;

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

		this.spriteModel = spriteModel;

		this.direction = Direction.NORTH;
	}

	public void setFrame(final int frameId) {
		currentFrame = frameId;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	public IHardpoint getHardpoint(final String name) {
		return wireModel.getAsset(currentFrame, direction).getHardpoint(name);
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
		final WireModelFrame wire = wireModel.getAsset(currentFrame, direction);

		for (final IHardpoint h : wire.getSortedHardpoints()) {
			final Collection<SpriteCollection> sprites = spriteModel.getSpriteCollection(h.getName()).getCollections();
			if (sprites == null || sprites.size() <= 0)
				continue;

			for (final SpriteCollection spriteCollection : sprites) {
				final Sprite sprite = spriteCollection.getAsset(currentFrame, direction);
				final IPoint attach = sprite.getAttachPoint();

				final int x = position.getX() + h.getX() - attach.getX();
				final int y = position.getY() + h.getY() - attach.getY();

				graphics.drawImage(sprite.getImage(), x, y);
			}
		}
	}
}
