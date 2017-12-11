package test.main;

import java.util.HashSet;
import java.util.Set;

import logical.nav.NavMap;
import logical.nav.graph.NavEdge;
import logical.nav.graph.NavNode;
import logical.nav.grid.NavGrid;
import logical.nav.grid.NavGridCell;
import logical.nav.grid.NavGridTree;
import logical.nav.path.api.IPathFinder;
import util.geometry.LineSegment;
import util.geometry.Point;
import util.geometry.Triangle;
import util.structures.ValuePair;

public class TestMapFactory {
	public static IPathFinder getMap1() {
		/* POINTS */
		final Point point_0_0 = new Point(0,0);
		final Point point_800_0 = new Point(800,0);
		final Point point_0_300 = new Point(0,300);
		final Point point_300_300 = new Point(300,300);
		final Point point_400_300 = new Point(400,300);
		final Point point_500_300 = new Point(500,300);
		final Point point_800_300 = new Point(800,300);
		final Point point_0_500 = new Point(0,500);
		final Point point_300_500 = new Point(300,500);
		final Point point_400_500 = new Point(400,500);
		final Point point_500_500 = new Point(500,500);
		final Point point_800_500 = new Point(800,500);
		final Point point_0_800 = new Point(0,800);
		final Point point_800_800 = new Point(800,800);

		/* TRIANGLES */
		final Triangle tri0 = new Triangle(point_0_0, point_400_300, point_800_0);
		final Triangle tri1 = new Triangle(point_800_0, point_400_300, point_800_300);
		final Triangle tri2 = new Triangle(point_800_300, point_500_300, point_800_500);
		final Triangle tri3 = new Triangle(point_500_300, point_800_500, point_500_500);
		final Triangle tri4 = new Triangle(point_800_500, point_400_500, point_800_800);
		final Triangle tri5 = new Triangle(point_400_500, point_0_800, point_800_800);
		final Triangle tri6 = new Triangle(point_0_500, point_400_500, point_0_800);
		final Triangle tri7 = new Triangle(point_0_500, point_300_500, point_300_300);
		final Triangle tri8 = new Triangle(point_0_500, point_300_300, point_0_300);
		final Triangle tri9 = new Triangle(point_0_300, point_400_300, point_0_0);

		/* GRAPH */
		final NavNode node0 = new NavNode(0, tri0, emptySet());
		final NavNode node1 = new NavNode(0, tri1, emptySet());
		final NavNode node2 = new NavNode(0, tri2, emptySet());
		final NavNode node3 = new NavNode(0, tri3, emptySet());
		final NavNode node4 = new NavNode(0, tri4, emptySet());
		final NavNode node5 = new NavNode(0, tri5, emptySet());
		final NavNode node6 = new NavNode(0, tri6, emptySet());
		final NavNode node7 = new NavNode(0, tri7, emptySet());
		final NavNode node8 = new NavNode(0, tri8, emptySet());
		final NavNode node9 = new NavNode(0, tri9, emptySet());

		/* EDGES */
		node0.addEdge(new NavEdge(0, node1, getAdjPair(makeLine(point_400_300, point_800_0), makeLine(point_400_300, point_800_0))));
		node0.addEdge(new NavEdge(0, node9, getAdjPair(makeLine(point_0_0, point_400_300), makeLine(point_0_0, point_400_300))));

		// TODO: Finish this (observe TestMapGeo.png in pictures folder on desktop)

		/* NAV GRID CELLS */
		final NavGridCell cell00 = new NavGridCell(0, 0);

		final NavGrid grid = new NavGrid();



		final NavGridTree gridTree = new NavGridTree(null);

		final NavMap newNav = new NavMap(null, null, null, null);

		return null;

	}

	private static Set<NavEdge> makeSet(final NavEdge... edges) {
		final Set<NavEdge> connections = new HashSet<NavEdge>();

		for (final NavEdge e : edges)
			connections.add(e);

		return connections;
	}

	private static LineSegment makeLine(final Point a, final Point b) {
		return new LineSegment(a,b);
	}

	private static ValuePair<LineSegment, LineSegment> getAdjPair(final LineSegment a, final LineSegment b) {
		return new ValuePair<LineSegment, LineSegment>(a,b);
	}

	private static Set<NavEdge> emptySet() {
		return new HashSet<NavEdge>();
	}
}
