package logical.nav;

import java.util.List;

import logical.nav.api.INavigationMap;
import logical.nav.graph.NavNode;
import logical.nav.graph.api.IGraphSearch;
import logical.nav.path.api.IPathFinder;
import logical.nav.path.api.IPathMaterializer;
import util.geometry.Point;

public class NavMap implements IPathFinder {
	private final INavigationMap<NavNode> navMap;
	private final IGraphSearch<NavNode> graphSearch;
	private final IPathMaterializer<NavNode> pathMaterializer;

	public NavMap(final INavigationMap<NavNode> navMap, final IGraphSearch<NavNode> graphSearch, final IPathMaterializer<NavNode> pathMaterializer) {
		this.navMap = navMap;
		this.graphSearch = graphSearch;
		this.pathMaterializer = pathMaterializer;
	}

	@Override
	public List<Point> findPath(final Point start, final Point end) {
		final NavNode startNode = navMap.resolvePoint(start);
		final NavNode endNode = navMap.resolvePoint(end);

		final List<NavNode> nodePath = graphSearch.findPath(startNode, endNode);

		final List<Point> cartesianPath = pathMaterializer.materialize(nodePath);

		return cartesianPath;
	}
}
