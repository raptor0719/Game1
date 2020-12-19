package raptor.engine.display.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class JavaAwtRenderer implements IRenderer {
	private final Graphics2D awtGraphics;

	private final BufferedImage buffer;
	private final Graphics2D toBuffer;

	private final Viewport viewport;

	private final IGraphics graphics;

	public JavaAwtRenderer(final Graphics2D awtGraphics, final Viewport viewport) {
		this.awtGraphics = awtGraphics;

		buffer = new BufferedImage(viewport.getWidth(), viewport.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		this.toBuffer = buffer.createGraphics();

		toBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		awtGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		this.viewport = viewport;

		graphics = new ToViewportGraphicsWrapper(new JavaAwtGraphics(toBuffer), viewport);
	}

	@Override
	public void draw(final Iterator<IDrawable> drawables) {
		clear();

		IDrawable current;
		while(drawables.hasNext()) {
			current = drawables.next();
			current.draw(graphics);
		}

		awtGraphics.drawImage(buffer, 0, 0, null);
	}

	private void clear() {
		toBuffer.setColor(Color.WHITE);
		toBuffer.fillRect(0, 0, viewport.getWidth(), viewport.getHeight());
	}
}
