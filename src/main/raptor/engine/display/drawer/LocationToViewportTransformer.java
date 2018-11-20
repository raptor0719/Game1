package raptor.engine.display.drawer;

import raptor.engine.game.Viewport;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.transformer.ITransformer;

public class LocationToViewportTransformer implements ITransformer<Point, Point> {
	private final Viewport vp;

	public LocationToViewportTransformer(final Viewport vp) {
		this.vp = vp;
	}

	@Override
	public Point transform(final Point in) {
		return new Point(in.getX() - vp.getXpos(), in.getY() - vp.getYpos());
	}
}
