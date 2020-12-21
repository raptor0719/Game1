package raptor.engine.nav.mesh.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import raptor.engine.nav.api.graph.IGraphSearch;
import raptor.engine.nav.api.graph.structures.IGraphEdge;
import raptor.engine.nav.api.graph.structures.IGraphNode;

public class BreadthFirst implements IGraphSearch {

	@Override
	public List<IGraphNode> findPath(final IGraphNode start, final IGraphNode end) {
		/*
		 * 1. Queue start into queue Q
		 * 2. Set A to dequeue of Q
		 * 3. If any child of A is end, add it to list L and return L
		 * 4. Expand A, add A to list L, and queue children into queue Q
		 * 5. Goto step 2
		 */
		if (start.equals(end))
			return null;

		final List<IGraphNode> path = new LinkedList<IGraphNode>();
		path.add(start);

		final Queue<List<IGraphNode>> Q = new LinkedList<List<IGraphNode>>();
		Q.add(makePath(new LinkedList<IGraphNode>(), start));

		List<IGraphNode> A;
		while (!Q.isEmpty()) {
			A = Q.poll();

			if (A == null)
				break;

			final IGraphNode tail = getTail(A);

			for (final IGraphEdge e : tail.getConnections()) {
				// Based on what is around this, this should never be a problem
				final IGraphNode n = e.getDestination();

				if (n.equals(end))
					return makePath(A, n);
			}

			Q.addAll(expand(A));
		}

		return null;
	}

	private List<List<IGraphNode>> expand(final List<IGraphNode> path) {
		final IGraphNode tail = getTail(path);

		final List<List<IGraphNode>> paths = new LinkedList<List<IGraphNode>>();

		for (final IGraphEdge e : tail.getConnections()) {
			// Based on what is around this, this should never be a problem
			final IGraphNode n = e.getDestination();

			paths.add(makePath(path, n));
		}

		return paths;
	}

	private List<IGraphNode> makePath(final List<IGraphNode> path, final IGraphNode node) {
		final List<IGraphNode> list = new LinkedList<IGraphNode>();
		list.addAll(path);
		list.add(node);

		return list;
	}

	private IGraphNode getTail(final List<IGraphNode> path) {
		return path.get(path.size()-1);
	}
}
