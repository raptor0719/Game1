package raptor.engine.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.geometry.Point;
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

			final boolean isRotated = h.getRotation() != 0;

			// TODO: We really should cache these rotations cause rotating these EVERY frame is gonna be super expensive
			final BufferedImage image = (isRotated) ? rotateImage(sprite.getImage(), h.getRotation()) : sprite.getImage();
			final IPoint attach = (isRotated) ? rotatePoint(sprite.getAttachPoint(), image.getWidth()/2, image.getHeight()/2, h.getRotation()) : sprite.getAttachPoint();

			final int x = position.getX() + h.getX() - attach.getX();
			final int y = position.getY() + h.getY() - attach.getY();

			graphics.drawImage(sprite.getImage(), x, y);
		}
	}

	private BufferedImage rotateImage(final BufferedImage image, final int degrees) {
		final int width = image.getWidth();
		final int height = image.getHeight();

		final double radians = Math.toRadians(degrees);

		final BufferedImage rotated = new BufferedImage(width, height, image.getType());

		final Graphics2D g = rotated.createGraphics();

		g.rotate(radians, width/2, height/2);
		g.drawImage(image, null, 0, 0);

		return rotated;
	}

	private Point rotatePoint(final Point point, final int pivotX, final int pivotY, final int degrees) {
		final double sin = Math.sin(Math.toRadians(degrees));
		final double cos = Math.cos(Math.toRadians(degrees));

		final int tX = point.getX() - pivotX;
		final int tY = point.getY() - pivotY;

		final double newX = tX * cos - tY * sin;
		final double newY = tX * sin + tY * cos;

		final double x = newX + pivotX;
		final double y = newY + pivotY;

		return new Point((int)x, (int)y);
	}
}
