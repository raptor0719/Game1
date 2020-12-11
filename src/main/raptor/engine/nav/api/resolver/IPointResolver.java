package raptor.engine.nav.api.resolver;

import raptor.engine.nav.api.graph.structures.IGraphNode;
import raptor.engine.util.geometry.Point;

public interface IPointResolver<D extends IGraphNode> {
	D resolvePoint(final Point p);
}
