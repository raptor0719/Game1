package raptor.engine.nav.api.graph;

public interface IGraphEdge {
	int getCost();
	IGraphNode getDestination();
}
