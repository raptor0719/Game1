package raptor.engine.model;

import java.awt.Image;

import raptor.engine.util.geometry.api.IPoint;

public interface ISprite {
	IPoint getAttachPoint();
	Image getImage();
}
