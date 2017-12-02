package logical.nav.graph.api;

public interface IGraphEdge<D> {
	public int getCost();
	public IGraphNode<D> getDestination();
}
