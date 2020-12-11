package raptor.engine.nav.api.graph.structures;

public interface IGraphEdge {
	int getCost();
	IGraphNode getDestination();
}
