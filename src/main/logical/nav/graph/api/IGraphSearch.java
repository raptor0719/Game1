package logical.nav.graph.api;

import java.util.List;

public interface IGraphSearch {
	public List<IGraphNode> findPath(final IGraphNode start, final IGraphNode end);
}
