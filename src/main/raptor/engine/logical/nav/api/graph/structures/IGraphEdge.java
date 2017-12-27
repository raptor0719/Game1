package raptor.engine.logical.nav.api.graph.structures;

public interface IGraphEdge {
	public int getCost();
	public IGraphNode getDestination();
}
