package logical.nav.graph.api;

import java.util.List;

public interface IGraphSearch<T extends IGraphNode<?>> {
	public List<T> findPath(final T start, final T end);
}
