package raptor.engine.display.render;

import java.awt.Image;

import raptor.engine.model.Sprite;

public class ToViewportGraphicsWrapper implements IGraphics {
	private final IGraphics graphics;
	private final LocationToViewportTransformer toViewport;
	private final Viewport viewport;

	public ToViewportGraphicsWrapper(final IGraphics graphics, final Viewport viewport) {
		this.graphics = graphics;
		this.toViewport = new LocationToViewportTransformer(viewport);
		this.viewport = viewport;
	}

	@Override
	public void drawSprite(final Sprite sprite, final int x, final int y, final int rotation) {
		final int viewportX = toViewport.transformX(x);
		final int viewportY = toViewport.transformY(y);

		if (!isInViewport(viewportX, viewportY, sprite.getImage().getWidth(null), sprite.getImage().getHeight(null)))
			return;

		graphics.drawSprite(sprite, viewportX, viewportY, rotation);
	}

	@Override
	public void drawImage(final Image image, final int x, final int y) {
		final int viewportX = toViewport.transformX(x);
		final int viewportY = toViewport.transformY(y);

		if (!isInViewport(viewportX, viewportY, image.getWidth(null), image.getHeight(null)))
			return;

		graphics.drawImage(image, viewportX, viewportY);
	}

	@Override
	public void drawOval(final int x, final int y, final int width, final int height, final boolean fill, final IColor color) {
		final int viewportX = toViewport.transformX(x);
		final int viewportY = toViewport.transformY(y);

		if (!isInViewport(viewportX, viewportY, width, height))
			return;

		graphics.drawOval(viewportX, viewportY, width, height, fill, color);
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height, final boolean fill, final IColor color) {
		final int viewportX = toViewport.transformX(x);
		final int viewportY = toViewport.transformY(y);

		if (!isInViewport(viewportX, viewportY, width, height))
			return;

		graphics.drawRectangle(viewportX, viewportY, width, height, fill, color);
	}

	@Override
	public void drawLine(int startX, int startY, int endX, int endY, int thickness, IColor color) {
		final int viewportStartX = toViewport.transformX(startX);
		final int viewportStartY = toViewport.transformY(startY);
		final int viewportEndX = toViewport.transformX(endX);
		final int viewportEndY = toViewport.transformY(endY);

		// Treat the line as a rectangle to test if it is in the viewport
		// This handles the case where both endpoints are outside of the viewport BUT
		//  it intersect into the viewport.
		final int testX = (viewportStartX < viewportEndX) ? viewportStartX : viewportEndX;
		final int testY = (viewportStartY < viewportEndY) ? viewportStartY : viewportEndY;
		final int testWidth = (viewportStartX < viewportEndX) ? viewportEndX - viewportStartX : viewportStartX - viewportEndX;
		final int testHeight = (viewportStartY < viewportEndY) ? viewportEndY - viewportStartY : viewportStartY - viewportEndY;

		if (!isInViewport(testX, testY, testWidth, testHeight))
			return;

		graphics.drawLine(viewportStartX, viewportStartY, viewportEndX, viewportEndY, thickness, color);
	}

	@Override
	public IGraphics getViewportRenderer() {
		return graphics;
	}

	private boolean isInViewport(final int viewportX, final int viewportY, final int width, final int height) {
		return (viewportX <= viewport.getWidth()) && (viewportX + width >= 0) && (viewportY <= viewport.getHeight()) && (viewportY + height >= 0);
	}
}
