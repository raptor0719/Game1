package raptor.engine.model;

import java.awt.image.BufferedImage;

import raptor.engine.util.geometry.Point;

public class Sprite implements ISprite {
	private final Point attachPoint;
	private final BufferedImage image;

	public Sprite(final int x, final int y, final BufferedImage image) {
		this.attachPoint = new Point(x, y);
		this.image = image;
	}

	@Override
	public Point getAttachPoint() {
		return attachPoint;
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}
}
