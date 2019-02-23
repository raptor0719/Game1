package raptor.engine.display.api;

import java.awt.Image;

import raptor.modelLibrary.model.util.point.IRotatedPoint;

/**
 * Any object that specifies how to draw itself, using the given IDrawer.
 */
public interface IDrawable {
	boolean doDraw();
	Image getImage();
	IRotatedPoint getPosition();
	int getWidth();
	int getHeight();
}
