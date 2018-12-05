package raptor.engine.display.api;

import raptor.engine.util.geometry.Point;

/**
 * Any object that specifies how to draw itself, using the given IDrawer.
 */
public interface IDrawable {
	public Point getPosition();
	public Point getDimensions();
}
