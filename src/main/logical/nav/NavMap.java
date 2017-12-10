package logical.nav;

import java.util.List;

import logical.nav.api.INavigationMap;
import logical.nav.graph.NavNode;
import logical.nav.graph.api.IGraphNode;
import logical.nav.graph.api.IGraphSearch;
import logical.nav.path.api.IPathFinder;
import logical.nav.path.api.IPathMaterializer;
import util.geometry.Point;
import util.transformer.ITransformer;

public class NavMap implements IPathFinder {
	private final INavigationMap<NavNode> navMap;
	private final IGraphSearch graphSearch;
	private final ITransformer<List<IGraphNode>, List<NavNode>> typeTransformer;
	private final IPathMaterializer<NavNode> pathMaterializer;

	public NavMap(final INavigationMap<NavNode> navMap, final IGraphSearch graphSearch, final ITransformer<List<IGraphNode>, List<NavNode>> typeTransformer, final IPathMaterializer<NavNode> pathMaterializer) {
		this.navMap = navMap;
		this.graphSearch = graphSearch;
		this.typeTransformer = typeTransformer;
		this.pathMaterializer = pathMaterializer;
	}

	@Override
	public List<Point> findPath(final Point start, final Point end) {
		final NavNode startNode = navMap.resolvePoint(start);
		final NavNode endNode = navMap.resolvePoint(end);

		final List<NavNode> nodePath = typeTransformer.transform(graphSearch.findPath(startNode, endNode));

		final List<Point> cartesianPath = pathMaterializer.materialize(nodePath);

		return cartesianPath;
	}
}
