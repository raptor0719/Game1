package raptor.engine.nav.api;

import raptor.engine.nav.api.graph.IGraphNode;
import raptor.engine.util.geometry.Point;

public interface IPointResolver<D extends IGraphNode> {
	D resolvePoint(final Point p);
}
