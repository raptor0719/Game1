package raptor.engine.logical.entity.api;

import raptor.engine.display.api.IDrawable;
import raptor.engine.util.geometry.Point;

public interface IEntity extends IDrawable {
	public Point getPosition();
	public void act();
}
