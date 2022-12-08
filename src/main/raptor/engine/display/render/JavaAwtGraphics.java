package raptor.engine.display.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map;

import raptor.engine.model.Sprite;

public class JavaAwtGraphics implements IGraphics {
	private final Graphics2D graphics;
	private final Stroke defaultStroke;

	private final Map<Long, Sprite> spriteCache;

	public JavaAwtGraphics(final Graphics2D graphics) {
		this.graphics = graphics;
		this.defaultStroke = new BasicStroke(2);
		this.spriteCache = new HashMap<>();
	}

	@Override
	public void drawSprite(final Sprite sprite, final int x, final int y, final int rotation) {
		final long hash = calculateHash(sprite, rotation);

		Sprite toDraw = null;
		if (spriteCache.containsKey(hash)) {
			toDraw = spriteCache.get(hash);
		} else {
			toDraw = SpriteUtility.translateSprite(sprite, rotation);
			spriteCache.put(hash, toDraw);
		}

		drawImage(toDraw.getImage(), x - toDraw.getAttachPoint().getX(), y - toDraw.getAttachPoint().getY());
	}

	private long calculateHash(final Sprite sprite, final int rotation) {
		return sprite.getHash() + rotation;
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

		if (fill)
			graphics.fillRect(x, y, width, height);
		else
			graphics.drawRect(x, y, width, height);
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
