package raptor.engine.logical.nav.api;

import raptor.engine.util.geometry.Point;

public interface INavAgent {
	public void setSpeed(final int speed);
	public void setDestination(final Point destination);
	public Point getPosition();
	public void move();
}
