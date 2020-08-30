package raptor.engine.display.render;

import java.awt.Image;

import raptor.engine.game.Viewport;

public class ToViewportGraphicsWrapper implements IGraphics {
	private final IGraphics graphics;
	private final LocationToViewportTransformer toViewport;

	public ToViewportGraphicsWrapper(final IGraphics graphics, final Viewport viewport) {
		this.graphics = graphics;
		this.toViewport = new LocationToViewportTransformer(viewport, 1);
	}

	@Override
	public void drawImage(final Image image, final int x, final int y) {
		graphics.drawImage(image, toViewport.transformX(x), toViewport.transformY(y));
	}

	@Override
	public void drawOval(final int x, final int y, final int width, final int height, final IColor color) {
		graphics.drawOval(toViewport.transformX(x), toViewport.transformY(y), width, height, color);
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height, final IColor color) {
		graphics.drawRectangle(toViewport.transformX(x), toViewport.transformY(y), width, height, color);
	}
}
