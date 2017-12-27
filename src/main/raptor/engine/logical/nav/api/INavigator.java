package raptor.engine.logical.nav.api;

import java.util.List;

import raptor.engine.util.geometry.Point;

public interface INavigator {
	public List<Point> findPath(final Point start, final Point end);
}
