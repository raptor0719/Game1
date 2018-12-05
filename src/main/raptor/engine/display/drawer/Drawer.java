package raptor.engine.display.drawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import raptor.engine.display.api.IDrawer;
import raptor.engine.util.geometry.Point;

public class Drawer implements IDrawer {

	private final LocationToViewportTransformer locToVp;
	private final Graphics2D actualG;

	private final BufferedImage buffer;
	private final Graphics2D g;

	public Drawer(final int width, final int height, final Graphics2D g, final LocationToViewportTransformer locToVp) {
		this.locToVp = locToVp;
		actualG = g;

		buffer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		this.g = buffer.createGraphics();
		g.setColor(Color.BLUE);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		actualG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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

	@Override
	public void clear(int x, int y, int width, int height) {
		final Point vp = transformLocation(new Point(x, y));
		g.clearRect(vp.getX(), vp.getY(), width, height);
	}

	public void draw() {
		actualG.drawImage(buffer, 0, 0, null);
	}
}
