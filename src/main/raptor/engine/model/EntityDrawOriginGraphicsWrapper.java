package raptor.engine.model;

import java.awt.Image;

import raptor.engine.display.render.IColor;
import raptor.engine.display.render.IGraphics;
import raptor.engine.game.entity.IEntity;

public class EntityDrawOriginGraphicsWrapper implements IGraphics {
	private final IEntity entity;

	private IGraphics wrapped = null;

	public EntityDrawOriginGraphicsWrapper(final IEntity entity) {
		this.entity = entity;
	}

	public void setGraphics(final IGraphics wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public void drawImage(final Image image, final int x, final int y) {
		wrapped.drawImage(image, translateX(x), translateY(y));
	}

	@Override
	public void drawOval(final int x, final int y, final int width, final int height, final boolean fill, final IColor color) {
		wrapped.drawOval(translateX(x), translateY(y), width, height, fill, color);
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height, final boolean fill, final IColor color) {
		wrapped.drawRectangle(translateX(x), translateY(y), width, height, fill, color);
	}

	@Override
	public void drawLine(final int startX, final int startY, final int endX, final int endY, final int thickness, final IColor color) {
		wrapped.drawLine(translateX(startX), translateY(startY), translateX(endX), translateY(endY), thickness, color);
	}

	@Override
	public IGraphics getViewportRenderer() {
		return wrapped;
	}

	private int translateX(final int x) {
		return x - entity.getWidth()/2;
	}

	private int translateY(final int y) {
		return y - entity.getHeight();
	}
}
