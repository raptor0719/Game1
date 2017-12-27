package raptor.engine.test.main;

import java.awt.Graphics2D;

import raptor.engine.display.api.IDrawer;

public class TestDrawer implements IDrawer {
	private final Graphics2D g;

	public TestDrawer(final Graphics2D g) {
		this.g = g;
	}

	@Override
	public void drawOval(int x, int y, int sizeX, int sizeY) {
		g.fillOval(x, y, sizeX, sizeY);
	}
}
