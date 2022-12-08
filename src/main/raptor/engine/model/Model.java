package raptor.engine.model;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.geometry.api.IPoint;

public class Model implements IDrawable {
	private final WireModel wireModel;
	private final int hardpointCount;
	private final SpriteModel spriteModel;

	private String currentFrame;

	private IPoint position;

	public Model(final WireModel wireModel, final SpriteModel spriteModel) {
		this.wireModel = wireModel;
		this.hardpointCount = wireModel.getHardpointCount();

		this.spriteModel = spriteModel;
	}

	public void setFrame(final String frameName) {
		this.currentFrame = frameName;
	}

	public Hardpoint getHardpoint(final String name) {
		return wireModel.getFrame(currentFrame).getHardpoint(name);
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
		final WireModelFrame wire = wireModel.getFrame(currentFrame);

		for (final Hardpoint h : wire.getSortedHardpoints()) {
			final Sprite sprite = spriteModel.getSpriteCollection(h.getName()).getSprite(h.getPhase());

			if (sprite == null)
				continue;

			graphics.drawSprite(sprite, position.getX(), position.getY(), h.getRotation());
		}
	}
}
