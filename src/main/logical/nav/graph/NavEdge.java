package logical.nav.graph;

import logical.nav.graph.api.GraphEdge;
import logical.nav.graph.api.GraphNode;

public class NavEdge implements GraphEdge<NavNodeTriangle> {

	private final int cost;
	private final GraphNode<NavNodeTriangle> destination;

	public NavEdge(final int cost, final GraphNode<NavNodeTriangle> destination) {
		this.cost = cost;
		this.destination = destination;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	@Override
	public GraphNode<NavNodeTriangle> getDestination() {
		// TODO Auto-generated method stub
		return destination;
	}
}
