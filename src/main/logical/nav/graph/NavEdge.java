package logical.nav.graph;

import logical.nav.graph.api.IGraphEdge;
import logical.nav.graph.api.IGraphNode;

public class NavEdge implements IGraphEdge<NavNode> {

	private final int cost;
	private final IGraphNode<NavNode> destination;

	public NavEdge(final int cost, final IGraphNode<NavNode> destination) {
		this.cost = cost;
		this.destination = destination;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	@Override
	public IGraphNode<NavNode> getDestination() {
		// TODO Auto-generated method stub
		return destination;
	}
}
