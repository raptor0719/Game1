package raptor.engine.display.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class JavaAwtRenderer implements IRenderer {
	private final Graphics2D awtGraphics;

	private final BufferedImage buffer;
	private final Graphics2D toBuffer;

	private final Viewport viewport;

	private final IGraphics graphics;

	private final Queue<IDrawable> drawQueue;

	public JavaAwtRenderer(final Graphics2D awtGraphics, final int width, final int height) {
		this.awtGraphics = awtGraphics;
		this.viewport = new Viewport(width, height, 0, 0);

		buffer = new BufferedImage(viewport.getWidth(), viewport.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		this.toBuffer = buffer.createGraphics();

		toBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		awtGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics = new ToViewportGraphicsWrapper(new JavaAwtGraphics(toBuffer), viewport);

		drawQueue = new LinkedList<>();
	}

	@Override
	public void queueDrawable(final IDrawable drawable) {
		drawQueue.add(drawable);
	}

	@Override
	public void queueDrawables(Iterator<IDrawable> drawables) {
		while (drawables.hasNext())
			drawQueue.add(drawables.next());
	}

	@Override
	public void draw() {
		clear();

		IDrawable current;
		while(!drawQueue.isEmpty()) {
			current = drawQueue.poll();
			current.draw(graphics);
		}

		awtGraphics.drawImage(buffer, 0, 0, null);
	}

	@Override
	public IViewport getViewport() {
		return viewport;
	}

	private void clear() {
		toBuffer.setColor(Color.WHITE);
		toBuffer.fillRect(0, 0, viewport.getWidth(), viewport.getHeight());
	}
}
