package raptor.engine.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Map;

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

	public Model(final WireModel wireModel, final Map<String, DirectionalSprite> defaultVisuals) {
		this.wireModel = wireModel;
		this.hardpointCount = wireModel.getHardpointCount();

		this.spriteModel = new SpriteModel();
		for (final Map.Entry<String, DirectionalSprite> entries : defaultVisuals.entrySet()) {
			spriteModel.addMapping(entries.getKey());

			final SpriteCollection collection = spriteModel.getSpriteCollection(entries.getKey());
			collection.addCollectionOnTop(entries.getValue());
		}

		this.direction = Direction.NORTH;
	}

	public void setFrame(final String frameName) {
		this.currentFrame = frameName;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	public IHardpoint getHardpoint(final String name) {
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

		for (final IHardpoint h : wire.getSortedHardpoints()) {
			final Collection<DirectionalSprite> sprites = spriteModel.getSpriteCollection(h.getName()).getCollections();
			if (sprites == null || sprites.size() <= 0)
				continue;

			for (final DirectionalSprite spriteCollection : sprites) {
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
