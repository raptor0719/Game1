package logical.nav.api.graph.structures;

import java.util.Set;

public interface IGraphNode {
	public Set<? extends IGraphEdge> getConnections();
}
