package raptor.engine.nav.api;

import raptor.engine.util.geometry.Point;

public interface INavAgent {
	void setPosition(int x, int y);
	Point getPosition();
	void setDestination(int x, int y);
	void move(int unitsToMove);
}
