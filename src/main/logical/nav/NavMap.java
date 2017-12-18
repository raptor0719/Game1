package logical.nav;

import java.util.ArrayList;
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
//		System.out.println("Finding path with start " + start + " and end " + end);
		final NavNode startNode = navMap.resolvePoint(start);
		final NavNode endNode = navMap.resolvePoint(end);
//		System.out.println("Start node is " + startNode);
//		System.out.println("End node is " + endNode);

		if (endNode == null || startNode == null)
			return null;

		// Short circuit here if start and end nodes are the same
		if (startNode.equals(endNode)) {
//			System.out.println("Was same node");
			final List<Point> path = new ArrayList<Point>(2);
			path.add(start);
			path.add(end);
			return path;
		}

		final List<IGraphNode> dirtyPath = graphSearch.findPath(startNode, endNode);
//		System.out.print("Path: ");
		for (final IGraphNode n : dirtyPath) {
			final NavNode nc = (NavNode)n;
//			System.out.print(nc.getId() + ", ");
		}
//		System.out.println("");

		final List<NavNode> nodePath = typeTransformer.transform(dirtyPath);
//		System.out.print("Sanitized Path: ");
		for (final IGraphNode n : nodePath) {
			final NavNode nc = (NavNode)n;
//			System.out.print(nc.getId() + ", ");
		}
//		System.out.println("");

		final List<Point> cartesianPath = pathMaterializer.materialize(nodePath, start, end);

		return cartesianPath;
	}
}
