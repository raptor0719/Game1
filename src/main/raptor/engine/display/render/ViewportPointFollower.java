package raptor.engine.display.render;

import raptor.engine.util.geometry.Point;

public class ViewportPointFollower {
	private final Viewport viewport;
	private final int scale;

	private Point point;

	public ViewportPointFollower(final Viewport viewport, final Point point, final int scale) {
		this.viewport = viewport;
		this.point = point;
		this.scale = scale;
	}

	public void setPoint(final Point newPoint) {
		this.point = newPoint;
	}

	public void follow() {
		viewport.setXPosition((point.getX() / scale) - (viewport.getWidth() / 2));
		viewport.setYPosition((point.getY() / scale) - (viewport.getHeight() / 2));
	}
}
