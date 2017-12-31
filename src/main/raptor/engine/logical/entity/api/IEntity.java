package raptor.engine.logical.entity.api;

import raptor.engine.display.api.IDrawable;
import raptor.engine.logical.event.api.IEvent;
import raptor.engine.util.geometry.Point;

public interface IEntity extends IDrawable {
	public int getId();
	public Point getPosition();
	public void act();
	public IEvent onDeath();
}
