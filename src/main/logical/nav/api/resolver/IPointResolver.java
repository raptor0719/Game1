package logical.nav.api.resolver;

import logical.nav.api.graph.structures.IGraphNode;
import util.geometry.Point;

public interface IPointResolver<D extends IGraphNode> {
	public D resolvePoint(final Point p);
}
