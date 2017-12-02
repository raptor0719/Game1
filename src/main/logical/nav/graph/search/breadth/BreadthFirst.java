package logical.nav.graph.search.breadth;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import logical.nav.graph.api.IGraphEdge;
import logical.nav.graph.api.IGraphNode;
import logical.nav.graph.api.IGraphSearch;

public class BreadthFirst<D> implements IGraphSearch<D> {

	@Override
	public List<? extends IGraphNode<D>> findPath(final IGraphNode<D> start, final IGraphNode<D> end) {
		/*
		 * 1. Queue start into queue Q
		 * 2. Set A to dequeue of Q
		 * 3. If any child of A is end, add it to list L and return L
		 * 4. Expand A, add A to list L, and queue children into queue Q
		 * 5. Goto step 2
		 */
		if (start.equals(end))
			return null;

		final List<IGraphNode<D>> path = new LinkedList<IGraphNode<D>>();
		path.add(start);

		final Queue<List<IGraphNode<D>>> Q = new LinkedList<List<IGraphNode<D>>>();
		Q.add(makePath(new LinkedList<IGraphNode<D>>(), start));

		List<IGraphNode<D>> A;
		while (!Q.isEmpty()) {
			A = Q.poll();

			if (A == null)
				break;

			final IGraphNode<D> tail = getTail(A);

			for (final IGraphEdge<D> e : tail.getConnections()) {
				final IGraphNode<D> n = e.getDestination();

				if (n.equals(end))
					return makePath(A, n);
			}

			Q.addAll(expand(A));
		}

		return null;
	}

	private List<List<IGraphNode<D>>> expand(final List<IGraphNode<D>> path) {
		final IGraphNode<D> tail = getTail(path);

		final List<List<IGraphNode<D>>> paths = new LinkedList<List<IGraphNode<D>>>();

		for (final IGraphEdge<D> e : tail.getConnections()) {
			final IGraphNode<D> n = e.getDestination();

			paths.add(makePath(path, n));
		}

		return paths;
	}

	private List<IGraphNode<D>> makePath(final List<IGraphNode<D>> path, final IGraphNode<D> node) {
		final List<IGraphNode<D>> list = new LinkedList<IGraphNode<D>>();
		list.addAll(path);
		list.add(node);

		return list;
	}

	private IGraphNode<D> getTail(final List<IGraphNode<D>> path) {
		return path.get(path.size()-1);
	}
}
