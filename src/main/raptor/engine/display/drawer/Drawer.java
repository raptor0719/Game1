package raptor.engine.display.drawer;

import java.awt.Graphics2D;

import raptor.engine.display.api.IDrawer;
import raptor.engine.util.geometry.Point;

public class Drawer implements IDrawer {

	private final LocationToViewportTransformer locToVp;
	private final Graphics2D g;

	public Drawer(final Graphics2D g, final LocationToViewportTransformer locToVp) {
		this.g = g;
		this.locToVp = locToVp;
	}

	@Override
	public void drawOval(int x, int y, int sizeX, int sizeY) {
		final Point vpPoint = transformLocation(new Point(x, y));
		g.drawOval(vpPoint.getX(), vpPoint.getY(), sizeX, sizeY);
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		final Point vpPoint = transformLocation(new Point(x, y));
		g.drawRect(vpPoint.getX(), vpPoint.getY(), width, height);
	}

	private Point transformLocation(final Point in) {
		return locToVp.transform(in);
	}
}
