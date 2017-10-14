package logical.nav.graph;

import java.util.Set;

import logical.nav.graph.api.GraphEdge;
import logical.nav.graph.api.GraphNode;
import util.geometry.Triangle;

public class NavNodeTriangle implements GraphNode<Triangle>{
	private final Set<GraphEdge<Triangle>> connections;
	private final Triangle triangle;

	protected NavNodeTriangle(final Set<GraphEdge<Triangle>> connections, final Triangle triangle) {
		this.connections = connections;
		this.triangle = triangle;
	}

	@Override
	public Triangle getData() {
		return triangle;
	}

	@Override
	public Set<GraphEdge<Triangle>> getConnections() {
		return connections;
	}

	public static class NavNodeTriangleBuilder {
		private Set<GraphEdge<Triangle>> connections;
		private Triangle triangle;

		public NavNodeTriangleBuilder connections(final Set<GraphEdge<Triangle>> connections) {
			this.connections = connections;
			return this;
		}

		public NavNodeTriangleBuilder triangle(final Triangle triangle) {
			this.triangle = triangle;
			return this;
		}

		public NavNodeTriangle build() {
			return new NavNodeTriangle(connections, triangle);
		}
	}
}
