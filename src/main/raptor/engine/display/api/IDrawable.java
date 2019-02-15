package raptor.engine.display.api;

import raptor.modelLibrary.model.point.IPointReader;

/**
 * Any object that specifies how to draw itself, using the given IDrawer.
 */
public interface IDrawable {
	IPointReader getPosition();
	IPointReader getDimensions();
}
