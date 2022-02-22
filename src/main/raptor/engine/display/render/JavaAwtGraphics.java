package raptor.engine.display.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;

public class JavaAwtGraphics implements IGraphics {
	private final Graphics2D graphics;
	private final Stroke defaultStroke;

	public JavaAwtGraphics(final Graphics2D graphics) {
		this.graphics = graphics;
		this.defaultStroke = new BasicStroke(2);
	}

	@Override
	public void drawImage(final Image image, final int x, final int y) {
		graphics.drawImage(image, x, y, null);
	}

	@Override
	public void drawOval(final int x, final int y, final int width, final int height, final boolean fill, final IColor color) {
		final Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		graphics.setColor(awtColor);
		graphics.setStroke(defaultStroke);
		graphics.fillOval(x, y, width, height);
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height,final boolean fill,  final IColor color) {
		final Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		graphics.setColor(awtColor);
		graphics.setStroke(defaultStroke);
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void drawLine(final int startX, final int startY, final int endX, final int endY, final int thickness, final IColor color) {
		final Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		graphics.setColor(awtColor);
		graphics.setStroke(new BasicStroke(thickness));
		graphics.drawLine(startX, startY, endX, endY);
	}

	@Override
	public IGraphics getViewportRenderer() {
		return this;
	}
}
