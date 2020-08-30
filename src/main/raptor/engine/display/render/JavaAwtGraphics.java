package raptor.engine.display.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

public class JavaAwtGraphics implements IGraphics {
	private final Graphics2D graphics;

	public JavaAwtGraphics(final Graphics2D graphics) {
		this.graphics = graphics;
	}

	@Override
	public void drawImage(final Image image, final int x, final int y) {
		graphics.drawImage(image, x, y, null);
	}

	@Override
	public void drawOval(final int x, final int y, final int width, final int height, final IColor color) {
		final Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		graphics.setColor(awtColor);
		graphics.drawOval(x, y, width, height);
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height, final IColor color) {
		final Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		graphics.setColor(awtColor);
		graphics.drawRect(x, y, width, height);
	}
}
