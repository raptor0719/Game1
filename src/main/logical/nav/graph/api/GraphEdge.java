package logical.nav.graph.api;

public interface GraphEdge<D> {
	public int getCost();
	public GraphNode<D> getDestination();
}
