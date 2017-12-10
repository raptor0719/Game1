package logical.nav.graph.transformer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logical.nav.graph.NavEdge;
import logical.nav.graph.NavNode;
import logical.nav.graph.api.IGraphEdge;
import logical.nav.graph.api.IGraphNode;
import util.transformer.ITransformer;

public class NavNodeTransformer implements ITransformer<List<IGraphNode>, List<NavNode>> {
	@Override
	public List<NavNode> transform(final List<IGraphNode> in) {
		final List<NavNode> result = new ArrayList<NavNode>(in.size());

		if (in.size() == 0)
			return result;

		for (int i = 0; i < in.size()-1; i++) {
			final IGraphNode start = in.get(i);
			final IGraphNode dest = in.get(i+1);

			result.add(sanitizeConnection(start, dest));
		}

		result.add(createNode(castNode(in.get(in.size()-1)), new HashSet<NavEdge>()));

		return result;
	}

	// Strip all connections off "a" except the one that goes to "b"
	//  also, cast the correct types.
	private NavNode sanitizeConnection(final IGraphNode a, final IGraphNode b) {
		for (final IGraphEdge e : a.getConnections()) {
			if (e.getDestination().equals(b)) {
				final Set<NavEdge> singletonSet = new HashSet<NavEdge>();
				singletonSet.add(castEdge(e));
				return createNode(castNode(a), singletonSet);
			}
		}

		throw new RuntimeException("Expected connection");
	}

	private NavNode createNode(final NavNode n, final Set<NavEdge> edge) {
		final NavNode newNode = new NavNode(n.getId(), n.getData(), edge);
		return newNode;
	}

	private NavNode castNode(final IGraphNode n) {
		if (!(n instanceof NavNode))
			throw new RuntimeException("Expected instance of NavNode, instead got " + n.getClass().getSimpleName());
		return (NavNode)n;
	}

	private NavEdge castEdge(final IGraphEdge e) {
		if (!(e instanceof NavEdge))
			throw new RuntimeException("Expected instance of NavEdge, instead got " + e.getClass().getSimpleName());
		return (NavEdge)e;
	}
}
