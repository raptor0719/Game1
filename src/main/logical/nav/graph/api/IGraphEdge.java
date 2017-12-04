package logical.nav.graph.api;

public interface IGraphEdge<T extends IGraphNode<?>> {
	public int getCost();
	public T getDestination();
}
