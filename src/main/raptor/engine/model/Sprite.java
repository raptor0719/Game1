package raptor.engine.model;

import java.awt.Image;

import raptor.engine.util.geometry.Point;

public class Sprite implements ISprite {
	private final int width;
	private final int height;
	private final Point attachPoint;
	private final Image image;

	public Sprite(final int width, final int height, final int x, final int y, final Image image) {
		this.width = width;
		this.height = height;
		this.attachPoint = new Point(x, y);
		this.image = image;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Point getAttachPoint() {
		return attachPoint;
	}

	@Override
	public Image getImage() {
		return image;
	}
}
