package logical.nav.api;

import logical.nav.graph.api.IGraphNode;
import util.geometry.Point;

public interface INavigationMap<D> {
	public IGraphNode<D> resolvePoint(final Point p);
}
