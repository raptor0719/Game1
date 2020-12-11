package raptor.engine.nav.api.graph.structures;

import java.util.Set;

public interface IGraphNode {
	Set<? extends IGraphEdge> getConnections();
}
