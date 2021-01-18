package raptor.engine.ui;

import java.awt.Image;

import raptor.engine.display.render.IGraphics;

public class UIImage extends UIElement {
	private Image image;

	public UIImage(final Image image, final int x, final int y, final UIAnchorPoint anchor, final int width, final int height, final int depth) {
		super(x, y, anchor, width, height, depth);
	}

	@Override
	protected void drawThis(final IGraphics graphics) {
		if (image == null)
			return;
		graphics.drawImage(image, getAbsoluteX(), getAbsoluteY());
	}

	public void setImage(final Image newImage) {
		this.image = newImage;
	}
}
