package raptor.engine.logical.nav.api.resolver;

import raptor.engine.logical.nav.api.graph.structures.IGraphNode;
import raptor.engine.util.geometry.Point;

public interface IPointResolver<D extends IGraphNode> {
	public D resolvePoint(final Point p);
}
