package logical.nav.graph.api;

import java.util.Set;

public interface IGraphNode<D> {
	public D getData();
	public Set<? extends IGraphEdge<? extends IGraphNode<D>>> getConnections();
}
