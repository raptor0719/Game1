package logical.nav.api;

import java.util.List;

import util.geometry.Point;

public interface INavigator {
	public List<Point> findPath(final Point start, final Point end);
}
