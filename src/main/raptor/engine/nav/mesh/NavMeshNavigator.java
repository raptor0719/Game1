package raptor.engine.nav.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import raptor.engine.nav.api.INavigator;
import raptor.engine.nav.api.IPathMaterializer;
import raptor.engine.nav.api.IPointResolver;
import raptor.engine.nav.api.graph.IGraphNode;
import raptor.engine.nav.api.graph.IGraphSearch;
import raptor.engine.nav.mesh.graph.BreadthFirst;
import raptor.engine.nav.mesh.graph.NavMeshEdge;
import raptor.engine.nav.mesh.graph.NavMeshNode;
import raptor.engine.nav.mesh.graph.NavNodeTransformer;
import raptor.engine.util.ITransformer;
import raptor.engine.util.ValuePair;
import raptor.engine.util.geometry.GeometryFunctions;
import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Polygon;
import raptor.engine.util.geometry.Triangle;
import raptor.engine.util.geometry.api.IPoint;

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
		NavMeshNode startNode;
		NavMeshNode endNode;

		try {
			startNode = navMap.resolvePoint(start);
			endNode = navMap.resolvePoint(end);
		} catch (final NavMeshPointResolver.PointNotInBoundsException e) {
			final List<Point> path = new ArrayList<Point>(2);
			path.add(start);
			path.add(start);
			return path;
		}

		if (endNode == null || startNode == null) {
			final List<Point> path = new ArrayList<Point>(2);
			path.add(start);
			path.add(start);
			return path;
		}

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

	@Override
	public boolean containsPoint(final int x, final int y) {
		return navMap.resolvePoint(new Point(x, y)) != null;
	}

	public static NavMeshNavigator buildNavigator(final Polygon parent, final List<Polygon> holes) {
		return buildNavigator(parent, holes, GeometryFunctions.fillPolygonWithTriangles(parent, holes));
	}

	/**
	* It is assumed the given mesh will be actually valid for the given parent and holes.
	* Maps are assumed to begin at 0,0
	*/
	public static NavMeshNavigator buildNavigator(final Polygon parent, final List<Polygon> holes, final List<Triangle> mesh) {
		final int cellSize = 100;

		final Point extremus = getXYMaximums(parent);
		final NavMeshGrid grid = new NavMeshGrid(getCellCount(extremus.getX()), getCellCount(extremus.getY()));

		final List<NavMeshNode> nodes = buildNodes(mesh);

		populateGrid(grid, cellSize, nodes);

		final IPointResolver<NavMeshNode> navMap = new NavMeshPointResolver(grid, getCellCount(extremus.getX()) * cellSize, getCellCount(extremus.getY()) * cellSize);

		final IGraphSearch graphSearch = new BreadthFirst();

		final ITransformer<List<IGraphNode>, List<NavMeshNode>> typeTransformer = new NavNodeTransformer();

		final List<Polygon> areaBounds = new ArrayList<>(holes);
		areaBounds.add(parent);
		final IPathMaterializer<NavMeshNode> pathMaterializer = new NavMeshPathMaterializer(areaBounds);

		return new NavMeshNavigator(navMap, graphSearch, typeTransformer, pathMaterializer);
	}

	private static Point getXYMaximums(final Polygon parent) {
		final Point maximums = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

		for (final Point p : parent.getPoints()) {
			maximums.setX(Math.max(p.getX(), maximums.getX()));
			maximums.setY(Math.max(p.getY(), maximums.getY()));
		}

		return maximums;
	}

	private static int getCellCount(final int extreme) {
		return ((extreme + 99) / 100);
	}

	private static List<NavMeshNode> buildNodes(final List<Triangle> mesh) {
		final List<NavMeshNode> nodes = new ArrayList<>();

		int idCounter = 0;
		for (final Triangle t : mesh)
			nodes.add(new NavMeshNode(idCounter++, t));

		for (int i = 0; i < nodes.size(); i++) {
			final NavMeshNode outer = nodes.get(i);
			for (int j = 0; j < nodes.size(); j++) {
				final NavMeshNode inner = nodes.get(j);

				if (outer.getId() == inner.getId())
					continue;

				buildConnections(outer, inner);
			}
		}

		return nodes;
	}

	// Builds connections FROM start TO end only. Does not modify end at all.
	private static void buildConnections(final NavMeshNode start, final NavMeshNode end) {
		for (final LineSegment startSeg : start.getData().getLines()) {
			for (final LineSegment endSeg : end.getData().getLines()) {
				if (startSeg.overlaps(endSeg) && !startSeg.isConnected(endSeg))
					start.addEdge(new NavMeshEdge(end, new ValuePair<LineSegment, LineSegment>(startSeg, endSeg)));
			}
		}
	}

	private static void populateGrid(final NavMeshGrid grid, final int cellSize, final List<NavMeshNode> nodes) {
		for (int x = 0; x < grid.getXCellCount(); x++) {
			for (int y = 0; y < grid.getYCellCount(); y++) {
				final NavMeshGridCell cell = grid.getCell(x, y);

				for (final NavMeshNode node : nodes)
					if (cellContainsNode(cell, cellSize, node))
						cell.addNode(node);
			}
		}
	}

	private static boolean cellContainsNode(final NavMeshGridCell cell, final int cellSize, final NavMeshNode node) {
		final int xAnchor = cell.getX() * cellSize;
		final int yAnchor = cell.getY() * cellSize;

		// We create points 1 unit shorter on all sides, so that we can use intersection methods for contains
		final Point cellTopLeft = new Point(xAnchor + 1, yAnchor + 1);
		final Point cellTopRight = new Point(xAnchor + cellSize - 1, yAnchor + 1);
		final Point cellBottomRight = new Point(xAnchor + cellSize - 1, yAnchor + cellSize - 1);
		final Point cellBottomLeft = new Point(xAnchor + 1, yAnchor + cellSize - 1);

		final Polygon cellPoly = new Polygon(Arrays.asList(new Point[] {cellTopLeft, cellTopRight, cellBottomRight, cellBottomLeft}));

		final Triangle nodeTriangle = node.getData();

		// If the cell contains a point of the triangle we know for sure it's in there
		for (final IPoint p : nodeTriangle.getPoints())
			if (cellPoly.containsPoint(p.getX(), p.getY()))
				return true;

		// If the triangle contains any point of the cell, we also know for sure
		for (final Point p : cellPoly.getPoints())
			if (nodeTriangle.containsPoint(p))
				return true;

		// If the triangle intersects the cell poly than we know it's inside
		for (final LineSegment nodeSegment : nodeTriangle.getLines())
			for (final LineSegment cellSegment : cellPoly.getLines())
				if (cellSegment.intersectsWith(nodeSegment))
					return true;

		return false;
	}
}
