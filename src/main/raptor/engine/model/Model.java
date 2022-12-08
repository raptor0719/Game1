package raptor.engine.model;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.api.IPoint;

public class Model implements IDrawable {
	private final WireModel wireModel;
	private final int hardpointCount;
	private final SpriteModel spriteModel;

	private String currentFrame;
	private int facingInDegrees;

	private IPoint position;

	public Model(final WireModel wireModel, final SpriteModel spriteModel) {
		this.wireModel = wireModel;
		this.hardpointCount = wireModel.getHardpointCount();

		this.spriteModel = spriteModel;

		this.facingInDegrees = 0;
	}

	public void setFrame(final String frameName) {
		this.currentFrame = frameName;
	}

	public void setFacingInDegrees(final int degrees) {
		this.facingInDegrees = normalizeDegrees(degrees);
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

			final Point rotatedPoint = rotatePoint(h.getX(), h.getY(), facingInDegrees);

			graphics.drawSprite(sprite, position.getX() + rotatedPoint.getX(), position.getY() - rotatedPoint.getY(), h.getRotation() + facingInDegrees);
		}
	}

	private int normalizeDegrees(final int degrees) {
		int normalized = degrees % 360;

		if (normalized < 0)
			normalized += 360;

		return normalized;
	}

	private Point rotatePoint(final int x, final int y, final int degrees) {
		final int pivotX = 0;
		final int pivotY = 0;

		final double sin = Math.sin(Math.toRadians(degrees));
		final double cos = Math.cos(Math.toRadians(degrees));

		final int tX = x - pivotX;
		final int tY = y - pivotY;

		final double newX = tX * cos - tY * sin;
		final double newY = tX * sin + tY * cos;

		final double finalX = newX + pivotX;
		final double finalY = newY + pivotY;

		return new Point((int)finalX, (int)finalY);
	}
}
