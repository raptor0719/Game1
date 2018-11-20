package raptor.engine.game;

import raptor.engine.display.api.IDrawable;
import raptor.engine.display.api.IDrawer;

public class Level implements IDrawable {
	public int width;
	public int height;

	public Level(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawRect(0, 0, width, height);
	}
}
