package raptor.engine.nav.api;

import java.util.List;

import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.Point;

public interface INavigator {
	List<Point> findPath(final Point start, final Point end);
	boolean containsPoint(int x, int y);
	List<LineSegment> getWalls();
}
