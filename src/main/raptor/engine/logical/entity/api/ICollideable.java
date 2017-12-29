package raptor.engine.logical.entity.api;

import raptor.engine.util.geometry.Point;

public interface ICollideable extends IAffectable {
	public boolean colliding(final Point p);
}
