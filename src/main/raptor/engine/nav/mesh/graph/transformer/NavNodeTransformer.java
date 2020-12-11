package raptor.engine.nav.mesh.graph.transformer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import raptor.engine.nav.api.graph.structures.IGraphEdge;
import raptor.engine.nav.api.graph.structures.IGraphNode;
import raptor.engine.nav.mesh.graph.structures.NavMeshEdge;
import raptor.engine.nav.mesh.graph.structures.NavMeshNode;
import raptor.engine.util.transformer.ITransformer;

public class NavNodeTransformer implements ITransformer<List<IGraphNode>, List<NavMeshNode>> {
	@Override
	public List<NavMeshNode> transform(final List<IGraphNode> in) {
		final List<NavMeshNode> result = new ArrayList<NavMeshNode>(in.size());

		if (in.size() == 0)
			return result;

		for (int i = 0; i < in.size()-1; i++) {
			final IGraphNode start = in.get(i);
			final IGraphNode dest = in.get(i+1);

			result.add(sanitizeConnection(start, dest));
		}

		result.add(createNode(castNode(in.get(in.size()-1)), new HashSet<NavMeshEdge>()));

		return result;
	}

	// Strip all connections off "a" except the one that goes to "b"
	//  also, cast the correct types.
	private NavMeshNode sanitizeConnection(final IGraphNode a, final IGraphNode b) {
		for (final IGraphEdge e : a.getConnections()) {
			if (e.getDestination().equals(b)) {
				final Set<NavMeshEdge> singletonSet = new HashSet<NavMeshEdge>();
				singletonSet.add(castEdge(e));
				return createNode(castNode(a), singletonSet);
			}
		}

		throw new RuntimeException("Expected connection");
	}

	private NavMeshNode createNode(final NavMeshNode n, final Set<NavMeshEdge> edge) {
		final NavMeshNode newNode = new NavMeshNode(n.getId(), n.getData(), edge);
		return newNode;
	}

	private NavMeshNode castNode(final IGraphNode n) {
		if (!(n instanceof NavMeshNode))
			throw new RuntimeException("Expected instance of NavNode, instead got " + n.getClass().getSimpleName());
		return (NavMeshNode)n;
	}

	private NavMeshEdge castEdge(final IGraphEdge e) {
		if (!(e instanceof NavMeshEdge))
			throw new RuntimeException("Expected instance of NavEdge, instead got " + e.getClass().getSimpleName());
		return (NavMeshEdge)e;
	}
}
