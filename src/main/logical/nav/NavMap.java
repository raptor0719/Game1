package logical.nav;

import java.util.List;

import logical.nav.api.INavigationMap;
import logical.nav.graph.api.IGraphNode;
import logical.nav.graph.api.IGraphSearch;
import logical.nav.path.api.IPathFinder;
import logical.nav.path.api.IPathMaterializer;
import util.geometry.Point;
import util.geometry.Triangle;

public class NavMap implements IPathFinder {
	private final INavigationMap<Triangle> navMap;
	private final IGraphSearch<Triangle> graphSearch;
	private final IPathMaterializer<Triangle> pathMaterializer;

	public NavMap(final INavigationMap<Triangle> navMap, final IGraphSearch<Triangle> graphSearch, final IPathMaterializer<Triangle> pathMaterializer) {
		this.navMap = navMap;
		this.graphSearch = graphSearch;
		this.pathMaterializer = pathMaterializer;
	}

	@Override
	public List<Point> findPath(final Point start, final Point end) {
		final IGraphNode<Triangle> startNode = navMap.resolvePoint(start);
		final IGraphNode<Triangle> endNode = navMap.resolvePoint(end);

		final List<? extends IGraphNode<Triangle>> nodePath = graphSearch.findPath(startNode, endNode);

		final List<Point> cartesianPath = pathMaterializer.materialize(nodePath);

		return cartesianPath;
	}
}
