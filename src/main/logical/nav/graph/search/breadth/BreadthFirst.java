package logical.nav.graph.search.breadth;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import logical.nav.graph.api.IGraphEdge;
import logical.nav.graph.api.IGraphNode;
import logical.nav.graph.api.IGraphSearch;

public class BreadthFirst<T extends IGraphNode<?>> implements IGraphSearch<T> {

	@Override
	public List<T> findPath(final T start, final T end) {
		/*
		 * 1. Queue start into queue Q
		 * 2. Set A to dequeue of Q
		 * 3. If any child of A is end, add it to list L and return L
		 * 4. Expand A, add A to list L, and queue children into queue Q
		 * 5. Goto step 2
		 */
		if (start.equals(end))
			return null;

		final List<T> path = new LinkedList<T>();
		path.add(start);

		final Queue<List<T>> Q = new LinkedList<List<T>>();
		Q.add(makePath(new LinkedList<T>(), start));

		List<T> A;
		while (!Q.isEmpty()) {
			A = Q.poll();

			if (A == null)
				break;

			final T tail = getTail(A);

			for (final IGraphEdge<? extends IGraphNode<?>> e : tail.getConnections()) {
				@SuppressWarnings("unchecked")
				final T n = (T) e.getDestination();

				if (n.equals(end))
					return makePath(A, n);
			}

			Q.addAll(expand(A));
		}

		return null;
	}

	private List<List<T>> expand(final List<T> path) {
		final T tail = getTail(path);

		final List<List<T>> paths = new LinkedList<List<T>>();

		for (final IGraphEdge<? extends IGraphNode<?>> e : tail.getConnections()) {
			@SuppressWarnings("unchecked")
			final T n = (T) e.getDestination();

			paths.add(makePath(path, n));
		}

		return paths;
	}

	private List<T> makePath(final List<T> path, final T node) {
		final List<T> list = new LinkedList<T>();
		list.addAll(path);
		list.add(node);

		return list;
	}

	private T getTail(final List<T> path) {
		return path.get(path.size()-1);
	}
}
