package raptor.engine.model;

import java.awt.Image;

import raptor.engine.util.geometry.Point;

public interface ISprite {
	int getWidth();
	int getHeight();
	Point getAttachPoint();
	Image getImage();
}
