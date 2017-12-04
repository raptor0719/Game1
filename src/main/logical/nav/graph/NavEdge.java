package logical.nav.graph;

import logical.nav.graph.api.IGraphEdge;
import util.geometry.LineSegment;
import util.structures.ValuePair;

public class NavEdge implements IGraphEdge<NavNode> {
	private final int cost;
	private final NavNode destination;
	private final ValuePair<LineSegment, LineSegment> adjacentEdges;

	public NavEdge(final int cost, final NavNode destination, final ValuePair<LineSegment, LineSegment> adjacentEdges) {
		this.cost = cost;
		this.destination = destination;
		this.adjacentEdges = adjacentEdges;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public NavNode getDestination() {
		return destination;
	}

	public ValuePair<LineSegment, LineSegment> getAdjacentEdges() {
		return adjacentEdges;
	}
}
