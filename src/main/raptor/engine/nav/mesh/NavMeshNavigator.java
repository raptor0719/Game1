package raptor.engine.nav.mesh;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.nav.api.INavigator;
import raptor.engine.nav.api.graph.IGraphSearch;
import raptor.engine.nav.api.graph.structures.IGraphNode;
import raptor.engine.nav.api.materializer.IPathMaterializer;
import raptor.engine.nav.api.resolver.IPointResolver;
import raptor.engine.nav.mesh.graph.NavMeshNode;
import raptor.engine.util.ITransformer;
import raptor.engine.util.geometry.Point;

public class NavMeshNavigator implements INavigator {
	private final IPointResolver<NavMeshNode> navMap;
	private final IGraphSearch graphSearch;
	private final ITransformer<List<IGraphNode>, List<NavMeshNode>> typeTransformer;
	private final IPathMaterializer<NavMeshNode> pathMaterializer;

	public NavMeshNavigator(final IPointResolver<NavMeshNode> navMap, final IGraphSearch graphSearch, final ITransformer<List<IGraphNode>, List<NavMeshNode>> typeTransformer, final IPathMaterializer<NavMeshNode> pathMaterializer) {
		this.navMap = navMap;
		this.graphSearch = graphSearch;
		this.typeTransformer = typeTransformer;
		this.pathMaterializer = pathMaterializer;
	}

	@Override
	public List<Point> findPath(final Point start, final Point end) {
		final NavMeshNode startNode = navMap.resolvePoint(start);
		final NavMeshNode endNode = navMap.resolvePoint(end);

		if (endNode == null || startNode == null)
			return null;

		// Short circuit here if start and end nodes are the same
		if (startNode.equals(endNode)) {
			final List<Point> path = new ArrayList<Point>(2);
			path.add(start);
			path.add(end);
			return path;
		}

		final List<IGraphNode> dirtyPath = graphSearch.findPath(startNode, endNode);
		final List<NavMeshNode> nodePath = typeTransformer.transform(dirtyPath);

		final List<Point> cartesianPath = pathMaterializer.materialize(nodePath, start, end);

		return cartesianPath;
	}
}
