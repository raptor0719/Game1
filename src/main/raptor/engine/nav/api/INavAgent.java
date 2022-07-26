package raptor.engine.nav.api;

import raptor.engine.util.geometry.DoubleVector;
import raptor.engine.util.geometry.Point;

public interface INavAgent {
	void setPosition(int x, int y);
	int getPositionX();
	int getPositionY();
	void setDestination(int x, int y);
	Point getDestination();
	void move(double unitsToMove);
	void setNavigator(INavigator navigator);
	DoubleVector getFaceVector();
	boolean atDestination();
}
