package raptor.engine.nav.api.graph;

import java.util.List;

import raptor.engine.nav.api.graph.structures.IGraphNode;

public interface IGraphSearch {
	List<IGraphNode> findPath(final IGraphNode start, final IGraphNode end);
}
