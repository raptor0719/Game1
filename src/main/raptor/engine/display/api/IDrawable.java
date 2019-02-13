package raptor.engine.display.api;

import java.util.List;

import raptor.engine.util.geometry.Point;

/**
 * Any object that specifies how to draw itself, using the given IDrawer.
 */
public interface IDrawable {
	Point getPosition();
	Point getDimensions();
	List<IDrawable> getDrawables();
}
