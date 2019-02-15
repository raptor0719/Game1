package raptor.engine.display.drawer;

import raptor.engine.game.Viewport;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.transformer.ITransformer;

public class LocationToViewportTransformer implements ITransformer<Point, Point> {
	private final Viewport vp;
	private final int scale;

	public LocationToViewportTransformer(final Viewport vp, final int scale) {
		this.vp = vp;
		this.scale = scale;
	}

	@Override
	public Point transform(final Point in) {
		return new Point((in.getX() / scale) - vp.getXpos(), (in.getY() / scale) - vp.getYpos());
	}
}
