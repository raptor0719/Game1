package logical.nav.graph.api;

import java.util.List;

public interface IGraphSearch<D> {
	public List<? extends IGraphNode<D>> findPath(final IGraphNode<D> start, final IGraphNode<D> end);
}
