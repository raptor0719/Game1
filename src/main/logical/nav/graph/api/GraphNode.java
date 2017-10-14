package logical.nav.graph.api;

import java.util.Set;

public interface GraphNode<D> {
	public D getData();
	public Set<GraphEdge<D>> getConnections();
}
