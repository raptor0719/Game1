package logical.nav.graph.search.breadth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import logical.nav.graph.api.IGraphEdge;
import logical.nav.graph.api.IGraphNode;
import util.structures.ValuePair;

public class BreadthFirstTest {

	@Test
	public void test() {
		final boolean[][] conns = new boolean[][] {{false, true, false},{false, false, true},{true, false, false}};

		final ValuePair<TestNode, TestNode> se = buildGraph(conns, 0, 2);

		final List<? extends IGraphNode<Integer>> path = new BreadthFirst<Integer>().findPath(se.getValue1(), se.getValue2());

		System.out.println(path);
	}

	// TODO: Migrate this to a graph testing suite

	/* HELPER METHODS */

	private ValuePair<TestNode, TestNode> buildGraph(final boolean[][] connectionGraph, final int start, final int end) {
		if (connectionGraph.length == 0 || connectionGraph[0].length == 0)
			throw new IllegalArgumentException("Given matrix must have length greater than 0.");
		if (connectionGraph.length != connectionGraph[0].length)
			throw new IllegalArgumentException("Given matrix must be square.");
		if (start >= connectionGraph.length || end >= connectionGraph.length)
			throw new IllegalArgumentException("Given start and end nodes must be within the graph.");

		final List<TestNode> nodes = new ArrayList<TestNode>(connectionGraph.length);

		for (int i = 0; i != connectionGraph.length; i++)
			nodes.add(new TestNode(i, new HashSet<TestEdge>()));

		for (int i = 0; i != connectionGraph.length; i++) {
			final TestNode node = nodes.get(i);

			for (int j = 0; j != connectionGraph[i].length; j++) {
				if (connectionGraph[i][j]) {
					final TestEdge newEdge = new TestEdge(0, nodes.get(j));
					node.addConnection(newEdge);
				}
			}
		}

		return new ValuePair<TestNode, TestNode>(nodes.get(start), nodes.get(end));
	}

	/* TEST CLASSES */

	private class TestNode implements IGraphNode<Integer> {
		private final int data;
		private final Set<TestEdge> connections;

		public TestNode(final int data, final Set<TestEdge> connections) {
			this.data = data;
			this.connections = connections;
		}

		@Override
		public Integer getData() {
			return data;
		}

		@Override
		public Set<TestEdge> getConnections() {
			return connections;
		}

		public void addConnection(final TestEdge conn) {
			connections.add(conn);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TestNode other = (TestNode) obj;
			if (data != other.data)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "" + data;
		}
	}

	private class TestEdge implements IGraphEdge<Integer> {
		private final int cost;
		private final IGraphNode<Integer> dest;

		public TestEdge(final int cost, final IGraphNode<Integer> dest) {
			this.cost = cost;
			this.dest = dest;
		}

		@Override
		public int getCost() {
			return cost;
		}

		@Override
		public IGraphNode<Integer> getDestination() {
			return dest;
		}
	}
}
