package raptor.engine.nav.api;

import java.util.Collection;
import java.util.List;

import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.api.ILineSegment;

public interface INavigator {
	List<Point> findPath(final Point start, final Point end);
	Collection<ILineSegment> getMapBounds();
}
