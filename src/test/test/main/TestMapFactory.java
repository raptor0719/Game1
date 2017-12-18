package test.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logical.nav.NavMap;
import logical.nav.graph.NavEdge;
import logical.nav.graph.NavNode;
import logical.nav.graph.api.IGraphNode;
import logical.nav.graph.api.IGraphSearch;
import logical.nav.graph.search.breadth.BreadthFirst;
import logical.nav.graph.transformer.NavNodeTransformer;
import logical.nav.grid.NavGrid;
import logical.nav.grid.NavGridCell;
import logical.nav.grid.NavGridTree;
import logical.nav.path.NavNodePathMaterializer;
import logical.nav.path.api.IPathFinder;
import logical.nav.path.api.IPathMaterializer;
import util.geometry.LineSegment;
import util.geometry.Point;
import util.geometry.Triangle;
import util.structures.ValuePair;
import util.transformer.ITransformer;

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
		final NavNode node1 = new NavNode(1, tri1, emptySet());
		final NavNode node2 = new NavNode(2, tri2, emptySet());
		final NavNode node3 = new NavNode(3, tri3, emptySet());
		final NavNode node4 = new NavNode(4, tri4, emptySet());
		final NavNode node5 = new NavNode(5, tri5, emptySet());
		final NavNode node6 = new NavNode(6, tri6, emptySet());
		final NavNode node7 = new NavNode(7, tri7, emptySet());
		final NavNode node8 = new NavNode(8, tri8, emptySet());
		final NavNode node9 = new NavNode(9, tri9, emptySet());

		/* LINES */
		// Lines B, D, G, and I have "e" editions which are the longer of the two
		final LineSegment lineA = makeLine(point_400_300, point_800_0);
		final LineSegment lineB = makeLine(point_500_300, point_800_300);
		final LineSegment lineBe = makeLine(point_400_300, point_800_300);
		final LineSegment lineC = makeLine(point_500_300, point_800_500);
		final LineSegment lineD = makeLine(point_500_500, point_800_500);
		final LineSegment lineDe = makeLine(point_400_500, point_800_500);
		final LineSegment lineE = makeLine(point_400_500, point_800_800);
		final LineSegment lineF = makeLine(point_0_800, point_400_500);
		final LineSegment lineG = makeLine(point_0_500, point_300_500);
		final LineSegment lineGe = makeLine(point_0_500, point_400_500);
		final LineSegment lineH = makeLine(point_0_500, point_300_300);
		final LineSegment lineI = makeLine(point_0_300, point_300_300);
		final LineSegment lineIe = makeLine(point_0_300, point_400_300);
		final LineSegment lineJ = makeLine(point_0_0, point_400_300);

		/* EDGES */
		node0.addEdge(new NavEdge(0, node9, getAdjPair(lineJ, lineJ)));
		node0.addEdge(new NavEdge(0, node1, getAdjPair(lineA, lineA)));
		node1.addEdge(new NavEdge(0, node0, getAdjPair(lineA, lineA)));
		node1.addEdge(new NavEdge(0, node2, getAdjPair(lineBe, lineB)));
		node2.addEdge(new NavEdge(0, node1, getAdjPair(lineB, lineBe)));
		node2.addEdge(new NavEdge(0, node3, getAdjPair(lineC, lineC)));
		node3.addEdge(new NavEdge(0, node2, getAdjPair(lineC, lineC)));
		node3.addEdge(new NavEdge(0, node4, getAdjPair(lineD, lineDe)));
		node4.addEdge(new NavEdge(0, node3, getAdjPair(lineDe, lineD)));
		node4.addEdge(new NavEdge(0, node5, getAdjPair(lineE, lineE)));
		node5.addEdge(new NavEdge(0, node4, getAdjPair(lineE, lineE)));
		node5.addEdge(new NavEdge(0, node6, getAdjPair(lineF, lineF)));
		node6.addEdge(new NavEdge(0, node5, getAdjPair(lineF, lineF)));
		node6.addEdge(new NavEdge(0, node7, getAdjPair(lineGe, lineG)));
		node7.addEdge(new NavEdge(0, node6, getAdjPair(lineG, lineGe)));
		node7.addEdge(new NavEdge(0, node8, getAdjPair(lineH, lineH)));
		node8.addEdge(new NavEdge(0, node7, getAdjPair(lineH, lineH)));
		node8.addEdge(new NavEdge(0, node9, getAdjPair(lineI, lineIe)));
		node9.addEdge(new NavEdge(0, node8, getAdjPair(lineIe, lineI)));
		node9.addEdge(new NavEdge(0, node0, getAdjPair(lineJ, lineJ)));

		/* NAV GRID CELLS */
		final NavGridCell cell00 = new NavGridCell(0, 0);
		cell00.addNode(node0);
		cell00.addNode(node9);
		final NavGridCell cell01 = new NavGridCell(0, 1);
		cell01.addNode(node0);
		cell01.addNode(node9);
		final NavGridCell cell02 = new NavGridCell(0, 2);
		cell02.addNode(node0);
		final NavGridCell cell03 = new NavGridCell(0, 3);
		cell03.addNode(node0);
		final NavGridCell cell04 = new NavGridCell(0, 4);
		cell04.addNode(node0);
		final NavGridCell cell05 = new NavGridCell(0, 5);
		cell05.addNode(node0);
		final NavGridCell cell06 = new NavGridCell(0, 6);
		cell06.addNode(node0);
		cell06.addNode(node1);
		final NavGridCell cell07 = new NavGridCell(0, 7);
		cell07.addNode(node0);
		cell07.addNode(node1);

		final NavGridCell cell10 = new NavGridCell(1, 0);
		cell10.addNode(node9);
		final NavGridCell cell11 = new NavGridCell(1, 1);
		cell11.addNode(node0);
		cell11.addNode(node9);
		final NavGridCell cell12 = new NavGridCell(1, 2);
		cell12.addNode(node0);
		cell12.addNode(node9);
		final NavGridCell cell13 = new NavGridCell(1, 3);
		cell13.addNode(node4);
		final NavGridCell cell14 = new NavGridCell(1, 4);
		cell14.addNode(node0);
		final NavGridCell cell15 = new NavGridCell(1, 5);
		cell15.addNode(node0);
		cell15.addNode(node1);
		final NavGridCell cell16 = new NavGridCell(1, 6);
		cell16.addNode(node0);
		cell16.addNode(node1);
		final NavGridCell cell17 = new NavGridCell(1, 7);
		cell17.addNode(node1);

		final NavGridCell cell20 = new NavGridCell(2, 0);
		cell20.addNode(node9);
		final NavGridCell cell21 = new NavGridCell(2, 1);
		cell21.addNode(node9);
		final NavGridCell cell22 = new NavGridCell(2, 2);
		cell22.addNode(node0);
		cell22.addNode(node9);
		final NavGridCell cell23 = new NavGridCell(2, 3);
		cell23.addNode(node0);
		cell23.addNode(node9);
		final NavGridCell cell24 = new NavGridCell(2, 4);
		cell24.addNode(node0);
		cell24.addNode(node1);
		final NavGridCell cell25 = new NavGridCell(2, 5);
		cell25.addNode(node0);
		cell25.addNode(node1);
		final NavGridCell cell26 = new NavGridCell(2, 6);
		cell26.addNode(node1);
		final NavGridCell cell27 = new NavGridCell(2, 7);
		cell27.addNode(node1);

		final NavGridCell cell30 = new NavGridCell(3, 0);
		cell30.addNode(node8);
		final NavGridCell cell31 = new NavGridCell(3, 1);
		cell31.addNode(node7);
		cell31.addNode(node8);
		final NavGridCell cell32 = new NavGridCell(3, 2);
		cell32.addNode(node7);
		cell32.addNode(node8);
		final NavGridCell cell33 = new NavGridCell(3, 3);
		final NavGridCell cell34 = new NavGridCell(3, 4);
		final NavGridCell cell35 = new NavGridCell(3, 5);
		cell35.addNode(node2);
		cell35.addNode(node3);
		final NavGridCell cell36 = new NavGridCell(3, 6);
		cell36.addNode(node2);
		cell36.addNode(node3);
		final NavGridCell cell37 = new NavGridCell(3, 7);
		cell37.addNode(node2);

		final NavGridCell cell40 = new NavGridCell(4, 0);
		cell40.addNode(node7);
		cell40.addNode(node8);
		final NavGridCell cell41 = new NavGridCell(4, 1);
		cell41.addNode(node7);
		cell41.addNode(node8);
		final NavGridCell cell42 = new NavGridCell(4, 2);
		cell42.addNode(node7);
		final NavGridCell cell43 = new NavGridCell(4, 3);
		final NavGridCell cell44 = new NavGridCell(4, 4);
		final NavGridCell cell45 = new NavGridCell(4, 5);
		cell45.addNode(node3);
		final NavGridCell cell46 = new NavGridCell(4, 6);
		cell46.addNode(node2);
		cell46.addNode(node3);
		final NavGridCell cell47 = new NavGridCell(4, 7);
		cell47.addNode(node2);
		cell47.addNode(node3);

		final NavGridCell cell50 = new NavGridCell(5, 0);
		cell50.addNode(node6);
		final NavGridCell cell51 = new NavGridCell(5, 1);
		cell51.addNode(node6);
		final NavGridCell cell52 = new NavGridCell(5, 2);
		cell52.addNode(node5);
		cell52.addNode(node6);
		final NavGridCell cell53 = new NavGridCell(5, 3);
		cell53.addNode(node5);
		cell53.addNode(node6);
		final NavGridCell cell54 = new NavGridCell(5, 4);
		cell54.addNode(node4);
		cell54.addNode(node5);
		final NavGridCell cell55 = new NavGridCell(5, 5);
		cell55.addNode(node4);
		cell55.addNode(node5);
		final NavGridCell cell56 = new NavGridCell(5, 6);
		cell56.addNode(node4);
		final NavGridCell cell57 = new NavGridCell(5, 7);
		cell57.addNode(node4);

		final NavGridCell cell60 = new NavGridCell(6, 0);
		cell60.addNode(node6);
		final NavGridCell cell61 = new NavGridCell(6, 1);
		cell61.addNode(node5);
		cell61.addNode(node6);
		final NavGridCell cell62 = new NavGridCell(6, 2);
		cell62.addNode(node5);
		cell62.addNode(node6);
		final NavGridCell cell63 = new NavGridCell(6, 3);
		cell63.addNode(node5);
		final NavGridCell cell64 = new NavGridCell(6, 4);
		cell64.addNode(node5);
		final NavGridCell cell65 = new NavGridCell(6, 5);
		cell65.addNode(node4);
		cell65.addNode(node5);
		final NavGridCell cell66 = new NavGridCell(6, 6);
		cell66.addNode(node4);
		cell66.addNode(node5);
		final NavGridCell cell67 = new NavGridCell(6, 7);
		cell67.addNode(node4);

		final NavGridCell cell70 = new NavGridCell(7, 0);
		cell70.addNode(node5);
		cell70.addNode(node6);
		final NavGridCell cell71 = new NavGridCell(7, 1);
		cell71.addNode(node5);
		cell71.addNode(node6);
		final NavGridCell cell72 = new NavGridCell(7, 2);
		cell72.addNode(node5);
		final NavGridCell cell73 = new NavGridCell(7, 3);
		cell73.addNode(node5);
		final NavGridCell cell74 = new NavGridCell(7, 4);
		cell74.addNode(node5);
		final NavGridCell cell75 = new NavGridCell(7, 5);
		cell75.addNode(node5);
		final NavGridCell cell76 = new NavGridCell(7, 6);
		cell76.addNode(node4);
		cell76.addNode(node5);
		final NavGridCell cell77 = new NavGridCell(7, 7);
		cell77.addNode(node4);
		cell77.addNode(node5);

		/* NAV GRID */
		final NavGrid grid = new NavGrid();

		grid.setCell(0, 0, cell00);
		grid.setCell(0, 1, cell01);
		grid.setCell(0, 2, cell02);
		grid.setCell(0, 3, cell03);
		grid.setCell(0, 4, cell04);
		grid.setCell(0, 5, cell05);
		grid.setCell(0, 6, cell06);
		grid.setCell(0, 7, cell07);

		grid.setCell(1, 0, cell10);
		grid.setCell(1, 1, cell11);
		grid.setCell(1, 2, cell12);
		grid.setCell(1, 3, cell13);
		grid.setCell(1, 4, cell14);
		grid.setCell(1, 5, cell15);
		grid.setCell(1, 6, cell16);
		grid.setCell(1, 7, cell17);

		grid.setCell(2, 0, cell20);
		grid.setCell(2, 1, cell21);
		grid.setCell(2, 2, cell22);
		grid.setCell(2, 3, cell23);
		grid.setCell(2, 4, cell24);
		grid.setCell(2, 5, cell25);
		grid.setCell(2, 6, cell26);
		grid.setCell(2, 7, cell27);

		grid.setCell(3, 0, cell30);
		grid.setCell(3, 1, cell31);
		grid.setCell(3, 2, cell32);
		grid.setCell(3, 3, cell33);
		grid.setCell(3, 4, cell34);
		grid.setCell(3, 5, cell35);
		grid.setCell(3, 6, cell36);
		grid.setCell(3, 7, cell37);

		grid.setCell(4, 0, cell40);
		grid.setCell(4, 1, cell41);
		grid.setCell(4, 2, cell42);
		grid.setCell(4, 3, cell43);
		grid.setCell(4, 4, cell44);
		grid.setCell(4, 5, cell45);
		grid.setCell(4, 6, cell46);
		grid.setCell(4, 7, cell47);

		grid.setCell(5, 0, cell50);
		grid.setCell(5, 1, cell51);
		grid.setCell(5, 2, cell52);
		grid.setCell(5, 3, cell53);
		grid.setCell(5, 4, cell54);
		grid.setCell(5, 5, cell55);
		grid.setCell(5, 6, cell56);
		grid.setCell(5, 7, cell57);

		grid.setCell(6, 0, cell60);
		grid.setCell(6, 1, cell61);
		grid.setCell(6, 2, cell62);
		grid.setCell(6, 3, cell63);
		grid.setCell(6, 4, cell64);
		grid.setCell(6, 5, cell65);
		grid.setCell(6, 6, cell66);
		grid.setCell(6, 7, cell67);

		grid.setCell(7, 0, cell70);
		grid.setCell(7, 1, cell71);
		grid.setCell(7, 2, cell72);
		grid.setCell(7, 3, cell73);
		grid.setCell(7, 4, cell74);
		grid.setCell(7, 5, cell75);
		grid.setCell(7, 6, cell76);
		grid.setCell(7, 7, cell77);

		/* NAV MAP */
		final NavGridTree gridTree = new NavGridTree(grid, 800, 800);
		final IGraphSearch graphSearch = new BreadthFirst();
		final ITransformer<List<IGraphNode>, List<NavNode>> nodeTransform = new NavNodeTransformer();
		final IPathMaterializer<NavNode> pathMaterializer = new NavNodePathMaterializer();

		final NavMap newNav = new NavMap(gridTree, graphSearch, nodeTransform, pathMaterializer);

		return newNav;
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
