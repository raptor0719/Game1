package logical.nav.graph.api;

import java.util.Set;

public interface IGraphNode {
	public Set<? extends IGraphEdge> getConnections();
}
