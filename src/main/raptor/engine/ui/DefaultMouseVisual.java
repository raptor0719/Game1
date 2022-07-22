package raptor.engine.ui;

import raptor.engine.display.render.BasicColor;
import raptor.engine.display.render.IColor;
import raptor.engine.display.render.IGraphics;

public class DefaultMouseVisual implements IMouseVisual {
	private static final IColor MOUSE_COLOR = new BasicColor(255, 255, 255, 255);
	private static final int THICKNESS = 5;

	private int x;
	private int y;

	public DefaultMouseVisual() {
		this.x = 0;
		this.y = 0;
	}

	@Override
	public void draw(final IGraphics graphics) {
		graphics.drawLine(x, y, x + 5, y + 5, THICKNESS, MOUSE_COLOR);
	}

	@Override
	public void setX(final int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
}
