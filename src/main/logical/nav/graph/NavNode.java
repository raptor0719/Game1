package logical.nav.graph;

import java.util.Set;

import logical.nav.graph.api.IDataGraphNode;
import util.geometry.Triangle;

public class NavNode implements IDataGraphNode<Triangle> {
	private final int id;
	private final Triangle triangle;
	private final Set<NavEdge> connections;

	public NavNode(final int id, final Triangle triangle, final Set<NavEdge> connections) {
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
	public Set<NavEdge> getConnections() {
		return connections;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof NavNode))
			return false;

		NavNode o2 = (NavNode)o;

		if (o2.getId() == getId())
			return true;

		return false;
	}

	public void addEdge(final NavEdge edge) {
		connections.add(edge);
	}

	public static class NavNodeBuilder {
		private int id;
		private Triangle triangle;
		private Set<NavEdge> connections;

		public NavNodeBuilder id(final int id) {
			this.id = id;
			return this;
		}

		public NavNodeBuilder triangle(final Triangle triangle) {
			this.triangle = triangle;
			return this;
		}

		public NavNodeBuilder connections(final Set<NavEdge> connections) {
			this.connections = connections;
			return this;
		}

		public NavNode build() {
			return new NavNode(id, triangle, connections);
		}
	}
}
