package raptor.engine.game;

import java.util.List;

import raptor.engine.display.api.IDrawable;
import raptor.engine.util.geometry.Point;

public class Level implements IDrawable {
	public int width;
	public int height;

	public Level(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public Point getPosition() {
		return new Point(0, 0);
	}

	@Override
	public Point getDimensions() {
		return new Point(width, height);
	}

	@Override
	public List<IDrawable> getDrawables() {
		// TODO Auto-generated method stub
		return null;
	}
}
