package logical.nav.api.graph;

import java.util.List;

import logical.nav.api.graph.structures.IGraphNode;

public interface IGraphSearch {
	public List<IGraphNode> findPath(final IGraphNode start, final IGraphNode end);
}
