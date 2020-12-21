package raptor.engine.nav.mesh.graph;

import java.util.HashSet;
import java.util.Set;

import raptor.engine.nav.api.graph.IDataGraphNode;
import raptor.engine.util.geometry.Triangle;

public class NavMeshNode implements IDataGraphNode<Triangle> {
	private final int id;
	private final Triangle triangle;
	private final Set<NavMeshEdge> connections;

	public NavMeshNode(final int id, final Triangle triangle, final Set<NavMeshEdge> connections) {
		this.id = id;
		this.triangle = triangle;
		this.connections = connections;
	}

	public NavMeshNode(final int id, final Triangle triangle) {
		this.id = id;
		this.triangle = triangle;
		this.connections = new HashSet<>();
	}

	public int getId() {
		return id;
	}

	@Override
	public Triangle getData() {
		return triangle;
	}

	@Override
	public Set<NavMeshEdge> getConnections() {
		return connections;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof NavMeshNode))
			return false;

		NavMeshNode o2 = (NavMeshNode)o;

		if (o2.getId() == getId())
			return true;

		return false;
	}

	@Override
	public String toString() {
		return "NavNode:[id=" + id + "]";
	}

	public void addEdge(final NavMeshEdge edge) {
		connections.add(edge);
	}
}
