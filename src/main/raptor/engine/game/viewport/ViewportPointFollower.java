package raptor.engine.game.viewport;

import raptor.engine.game.Viewport;
import raptor.modelLibrary.model.point.IPointReader;

public class ViewportPointFollower {
	private final Viewport viewport;
	private final int scale;

	private IPointReader point;

	public ViewportPointFollower(final Viewport viewport, final IPointReader point, final int scale) {
		this.viewport = viewport;
		this.point = point;
		this.scale = scale;
	}

	public void setPoint(final IPointReader newPoint) {
		this.point = newPoint;
	}

	public void follow() {
		viewport.setXpos((point.getX() / scale) - (viewport.getWidth() / 2));
		viewport.setYpos((point.getY() / scale) - (viewport.getHeight() / 2));
	}
}
