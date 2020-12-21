package raptor.engine.nav.api.graph;

import java.util.List;

public interface IGraphSearch {
	List<IGraphNode> findPath(final IGraphNode start, final IGraphNode end);
}
