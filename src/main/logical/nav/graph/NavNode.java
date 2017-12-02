package logical.nav.graph;

import java.util.Set;

import logical.nav.graph.api.IGraphEdge;
import logical.nav.graph.api.IGraphNode;
import util.geometry.Triangle;

public class NavNode implements IGraphNode<Triangle> {
	private final int id;
	private final Triangle triangle;
	private final Set<IGraphEdge<Triangle>> connections;

	protected NavNode(final int id, final Triangle triangle, final Set<IGraphEdge<Triangle>> connections) {
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
	public Set<IGraphEdge<Triangle>> getConnections() {
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

	public static class NavNodeBuilder {
		private int id;
		private Triangle triangle;
		private Set<IGraphEdge<Triangle>> connections;

		public NavNodeBuilder id(final int id) {
			this.id = id;
			return this;
		}

		public NavNodeBuilder triangle(final Triangle triangle) {
			this.triangle = triangle;
			return this;
		}

		public NavNodeBuilder connections(final Set<IGraphEdge<Triangle>> connections) {
			this.connections = connections;
			return this;
		}

		public NavNode build() {
			return new NavNode(id, triangle, connections);
		}
	}
}
