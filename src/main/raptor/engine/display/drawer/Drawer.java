package raptor.engine.display.drawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import raptor.engine.display.api.IDrawable;

public class Drawer {

	private final LocationToViewportTransformer locToVp;
	private final Graphics2D actualG;

	private final BufferedImage buffer;
	private final Graphics2D g;

	private final int width;
	private final int height;

	public Drawer(final int width, final int height, final Graphics2D g, final LocationToViewportTransformer locToVp) {
		this.locToVp = locToVp;
		actualG = g;

		buffer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		this.g = buffer.createGraphics();

		this.width = width;
		this.height = height;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		actualG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void draw(final List<IDrawable> drawables) {
		clear();

		for (final IDrawable d : drawables) {
			if (d.doDraw()) {
				final int x = locToVp.transformX(d.getPosition().getX());
				final int y = locToVp.transformY(d.getPosition().getY());
				g.drawImage(d.getImage(), x, y, null);
			}
		}

		actualG.drawImage(buffer, 0, 0, null);
	}

	private void clear() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
	}
}
