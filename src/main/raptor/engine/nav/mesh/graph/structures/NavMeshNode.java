package raptor.engine.nav.mesh.graph.structures;

import java.util.Set;

import raptor.engine.nav.api.graph.structures.IDataGraphNode;
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

	public static class NavNodeBuilder {
		private int id;
		private Triangle triangle;
		private Set<NavMeshEdge> connections;

		public NavNodeBuilder id(final int id) {
			this.id = id;
			return this;
		}

		public NavNodeBuilder triangle(final Triangle triangle) {
			this.triangle = triangle;
			return this;
		}

		public NavNodeBuilder connections(final Set<NavMeshEdge> connections) {
			this.connections = connections;
			return this;
		}

		public NavMeshNode build() {
			return new NavMeshNode(id, triangle, connections);
		}
	}
}
