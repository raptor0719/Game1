package raptor.engine.model;

import java.awt.Image;

import raptor.engine.util.geometry.Point;

public class Sprite implements ISprite {
	private final Point attachPoint;
	private final Image image;

	public Sprite(final int x, final int y, final Image image) {
		this.attachPoint = new Point(x, y);
		this.image = image;
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
