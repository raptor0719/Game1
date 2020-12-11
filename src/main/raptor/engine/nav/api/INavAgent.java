package raptor.engine.nav.api;

import raptor.engine.util.geometry.Point;

public interface INavAgent {
	void setSpeed(final int speed);
	void setDestination(final Point destination);
	Point getPosition();
	void move();
}
