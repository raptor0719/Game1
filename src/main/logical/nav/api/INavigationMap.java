package logical.nav.api;

import logical.nav.graph.api.IGraphNode;
import util.geometry.Point;

public interface INavigationMap<D extends IGraphNode<?>> {
	public D resolvePoint(final Point p);
}
