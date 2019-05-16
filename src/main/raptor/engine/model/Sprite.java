package raptor.engine.model;

import java.awt.Image;

public class Sprite {
	private final Image image;
	private final int originX;
	private final int originY;

	public Sprite(final Image image, final int originX, final int originY) {
		this.image = image;
		this.originX = originX;
		this.originY = originY;
	}

	public Image getImage() {
		return image;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}
}


