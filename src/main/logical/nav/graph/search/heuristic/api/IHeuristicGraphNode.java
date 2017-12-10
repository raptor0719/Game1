package logical.nav.graph.search.heuristic.api;

import logical.nav.graph.api.IGraphNode;

public interface IHeuristicGraphNode extends IGraphNode {
	public int getHeuristic();
}
