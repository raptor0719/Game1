package logical.nav.graph.search.breadth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import logical.nav.api.graph.structures.IGraphEdge;
import logical.nav.api.graph.structures.IGraphNode;
import logical.nav.mesh.graph.search.BreadthFirst;
import util.structures.ValuePair;

public class BreadthFirstTest {

	@Test
	public void test() {
		final int[][] conns = new int[][] {
			{0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 1, 0},};

		final ValuePair<TestNode, TestNode> se = buildGraph(conns, 3, 6);

		final List<IGraphNode> path = new BreadthFirst().findPath(se.getValue1(), se.getValue2());

		System.out.println(path);
	}

	// TODO: Migrate this to a graph testing suite

	/* HELPER METHODS */

	private ValuePair<TestNode, TestNode> buildGraph(final int[][] connectionGraph, final int start, final int end) {
		if (connectionGraph.length == 0 || connectionGraph[0].length == 0)
			throw new IllegalArgumentException("Given matrix must have length greater than 0.");
		if (connectionGraph.length != connectionGraph[0].length)
			throw new IllegalArgumentException("Given matrix must be square.");
		if (start >= connectionGraph.length || end >= connectionGraph.length)
			throw new IllegalArgumentException("Given start and end nodes must be within the graph.");

		final List<TestNode> nodes = new ArrayList<TestNode>(connectionGraph.length);

		for (int i = 0; i != connectionGraph.length; i++)
			nodes.add(new TestNode(i));

		for (int i = 0; i != connectionGraph.length; i++) {
			final TestNode node = nodes.get(i);

			for (int j = 0; j != connectionGraph[i].length; j++) {
				if (connectionGraph[i][j] == 1) {
					final TestEdge newEdge = new TestEdge(0, nodes.get(j));
					node.addConnection(newEdge);
				}
			}
		}

		return new ValuePair<TestNode, TestNode>(nodes.get(start), nodes.get(end));
	}

	/* TEST CLASSES */

	private class TestNode implements IGraphNode {
		private final int id;
		private final Set<TestEdge> connections;

		public TestNode(final int id) {
			this.id = id;
			connections = new HashSet<TestEdge>();
		}

		@Override
		public Set<TestEdge> getConnections() {
			return connections;
		}

		public void addConnection(final TestEdge e) {
			connections.add(e);
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o)
				return true;
			if (o == null)
				return false;
			if (!(o instanceof TestNode))
				return false;
			final TestNode o2 = (TestNode)o;
			if (this.id == o2.id)
				return true;
			return false;
		}

		@Override
		public String toString() {
			return "" + this.id;
		}
	}

	private class TestEdge implements IGraphEdge {
		private final int cost;
		private final TestNode dest;

		public TestEdge(final int cost, final TestNode dest) {
			this.cost = cost;
			this.dest = dest;
		}

		@Override
		public int getCost() {
			return cost;
		}

		@Override
		public TestNode getDestination() {
			return dest;
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o)
				return true;
			if (o == null)
				return false;
			if (!(o instanceof TestEdge))
				return false;
			final TestEdge o2 = (TestEdge)o;
			if (this.dest == o2.dest)
				return true;
			return false;
		}
	}
}
