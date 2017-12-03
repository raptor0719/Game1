package logical.nav.path.api;

import java.util.List;

import util.geometry.Point;

public interface IPathFinder {
	public List<Point> findPath(final Point start, final Point end);
}
