package logical.nav.graph.search.heuristic.api;

import logical.nav.graph.api.IGraphNode;

public interface IHeuristicGraphNode<D> extends IGraphNode<D> {
	public int getHeuristic();
}
