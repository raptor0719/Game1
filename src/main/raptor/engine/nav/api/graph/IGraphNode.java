package raptor.engine.nav.api.graph;

import java.util.Set;

public interface IGraphNode {
	Set<? extends IGraphEdge> getConnections();
}
