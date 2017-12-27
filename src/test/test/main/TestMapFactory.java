package test.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logical.nav.api.INavigator;
import logical.nav.api.graph.IGraphSearch;
import logical.nav.api.graph.structures.IGraphNode;
import logical.nav.api.materializer.IPathMaterializer;
import logical.nav.mesh.NavMeshNavigator;
import logical.nav.mesh.graph.search.BreadthFirst;
import logical.nav.mesh.graph.structures.NavMeshEdge;
import logical.nav.mesh.graph.structures.NavMeshNode;
import logical.nav.mesh.graph.transformer.NavNodeTransformer;
import logical.nav.mesh.materializer.NavMeshPathMaterializer;
import logical.nav.mesh.resolver.NavMeshPointResolver;
import logical.nav.mesh.resolver.grid.NavMeshGrid;
import logical.nav.mesh.resolver.grid.NavMeshGridCell;
import util.geometry.LineSegment;
import util.geometry.Point;
import util.geometry.Triangle;
import util.structures.ValuePair;
import util.transformer.ITransformer;

public class TestMapFactory {
	public static INavigator getMap1() {
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
		final NavMeshNode node0 = new NavMeshNode(0, tri0, emptySet());
		final NavMeshNode node1 = new NavMeshNode(1, tri1, emptySet());
		final NavMeshNode node2 = new NavMeshNode(2, tri2, emptySet());
		final NavMeshNode node3 = new NavMeshNode(3, tri3, emptySet());
		final NavMeshNode node4 = new NavMeshNode(4, tri4, emptySet());
		final NavMeshNode node5 = new NavMeshNode(5, tri5, emptySet());
		final NavMeshNode node6 = new NavMeshNode(6, tri6, emptySet());
		final NavMeshNode node7 = new NavMeshNode(7, tri7, emptySet());
		final NavMeshNode node8 = new NavMeshNode(8, tri8, emptySet());
		final NavMeshNode node9 = new NavMeshNode(9, tri9, emptySet());

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
		node0.addEdge(new NavMeshEdge(0, node9, getAdjPair(lineJ, lineJ)));
		node0.addEdge(new NavMeshEdge(0, node1, getAdjPair(lineA, lineA)));
		node1.addEdge(new NavMeshEdge(0, node0, getAdjPair(lineA, lineA)));
		node1.addEdge(new NavMeshEdge(0, node2, getAdjPair(lineBe, lineB)));
		node2.addEdge(new NavMeshEdge(0, node1, getAdjPair(lineB, lineBe)));
		node2.addEdge(new NavMeshEdge(0, node3, getAdjPair(lineC, lineC)));
		node3.addEdge(new NavMeshEdge(0, node2, getAdjPair(lineC, lineC)));
		node3.addEdge(new NavMeshEdge(0, node4, getAdjPair(lineD, lineDe)));
		node4.addEdge(new NavMeshEdge(0, node3, getAdjPair(lineDe, lineD)));
		node4.addEdge(new NavMeshEdge(0, node5, getAdjPair(lineE, lineE)));
		node5.addEdge(new NavMeshEdge(0, node4, getAdjPair(lineE, lineE)));
		node5.addEdge(new NavMeshEdge(0, node6, getAdjPair(lineF, lineF)));
		node6.addEdge(new NavMeshEdge(0, node5, getAdjPair(lineF, lineF)));
		node6.addEdge(new NavMeshEdge(0, node7, getAdjPair(lineGe, lineG)));
		node7.addEdge(new NavMeshEdge(0, node6, getAdjPair(lineG, lineGe)));
		node7.addEdge(new NavMeshEdge(0, node8, getAdjPair(lineH, lineH)));
		node8.addEdge(new NavMeshEdge(0, node7, getAdjPair(lineH, lineH)));
		node8.addEdge(new NavMeshEdge(0, node9, getAdjPair(lineI, lineIe)));
		node9.addEdge(new NavMeshEdge(0, node8, getAdjPair(lineIe, lineI)));
		node9.addEdge(new NavMeshEdge(0, node0, getAdjPair(lineJ, lineJ)));

		/* NAV GRID CELLS */
		final NavMeshGridCell cell00 = new NavMeshGridCell(0, 0);
		cell00.addNode(node0);
		cell00.addNode(node9);
		final NavMeshGridCell cell01 = new NavMeshGridCell(0, 1);
		cell01.addNode(node9);
		final NavMeshGridCell cell02 = new NavMeshGridCell(0, 2);
		cell02.addNode(node9);
		final NavMeshGridCell cell03 = new NavMeshGridCell(0, 3);
		cell03.addNode(node8);
		final NavMeshGridCell cell04 = new NavMeshGridCell(0, 4);
		cell04.addNode(node7);
		cell04.addNode(node8);
		final NavMeshGridCell cell05 = new NavMeshGridCell(0, 5);
		cell05.addNode(node6);
		final NavMeshGridCell cell06 = new NavMeshGridCell(0, 6);
		cell06.addNode(node6);
		final NavMeshGridCell cell07 = new NavMeshGridCell(0, 7);
		cell07.addNode(node5);
		cell07.addNode(node6);

		final NavMeshGridCell cell10 = new NavMeshGridCell(1, 0);
		cell10.addNode(node0);
		cell10.addNode(node9);
		final NavMeshGridCell cell11 = new NavMeshGridCell(1, 1);
		cell11.addNode(node0);
		cell11.addNode(node9);
		final NavMeshGridCell cell12 = new NavMeshGridCell(1, 2);
		cell12.addNode(node9);
		final NavMeshGridCell cell13 = new NavMeshGridCell(1, 3);
		cell13.addNode(node7);
		cell13.addNode(node8);
		final NavMeshGridCell cell14 = new NavMeshGridCell(1, 4);
		cell14.addNode(node7);
		cell14.addNode(node8);
		final NavMeshGridCell cell15 = new NavMeshGridCell(1, 5);
		cell15.addNode(node6);
		final NavMeshGridCell cell16 = new NavMeshGridCell(1, 6);
		cell16.addNode(node5);
		cell16.addNode(node6);
		final NavMeshGridCell cell17 = new NavMeshGridCell(1, 7);
		cell17.addNode(node5);
		cell17.addNode(node6);

		final NavMeshGridCell cell20 = new NavMeshGridCell(2, 0);
		cell20.addNode(node9);
		final NavMeshGridCell cell21 = new NavMeshGridCell(2, 1);
		cell21.addNode(node0);
		cell21.addNode(node9);
		final NavMeshGridCell cell22 = new NavMeshGridCell(2, 2);
		cell22.addNode(node0);
		cell22.addNode(node9);
		final NavMeshGridCell cell23 = new NavMeshGridCell(2, 3);
		cell23.addNode(node7);
		cell23.addNode(node8);
		final NavMeshGridCell cell24 = new NavMeshGridCell(2, 4);
		cell24.addNode(node7);
		final NavMeshGridCell cell25 = new NavMeshGridCell(2, 5);
		cell25.addNode(node5);
		cell25.addNode(node6);
		final NavMeshGridCell cell26 = new NavMeshGridCell(2, 6);
		cell26.addNode(node5);
		cell26.addNode(node6);
		final NavMeshGridCell cell27 = new NavMeshGridCell(2, 7);
		cell27.addNode(node5);

		final NavMeshGridCell cell30 = new NavMeshGridCell(3, 0);
		cell30.addNode(node0);
		final NavMeshGridCell cell31 = new NavMeshGridCell(3, 1);
		cell31.addNode(node0);
		final NavMeshGridCell cell32 = new NavMeshGridCell(3, 2);
		cell32.addNode(node0);
		cell32.addNode(node9);
		final NavMeshGridCell cell33 = new NavMeshGridCell(3, 3);
		final NavMeshGridCell cell34 = new NavMeshGridCell(3, 4);
		final NavMeshGridCell cell35 = new NavMeshGridCell(3, 5);
		cell35.addNode(node5);
		cell35.addNode(node6);
		final NavMeshGridCell cell36 = new NavMeshGridCell(3, 6);
		cell36.addNode(node5);
		final NavMeshGridCell cell37 = new NavMeshGridCell(3, 7);
		cell37.addNode(node5);

		final NavMeshGridCell cell40 = new NavMeshGridCell(4, 0);
		cell40.addNode(node0);
		final NavMeshGridCell cell41 = new NavMeshGridCell(4, 1);
		cell41.addNode(node0);
		final NavMeshGridCell cell42 = new NavMeshGridCell(4, 2);
		cell42.addNode(node0);
		cell42.addNode(node1);
		final NavMeshGridCell cell43 = new NavMeshGridCell(4, 3);
		final NavMeshGridCell cell44 = new NavMeshGridCell(4, 4);
		final NavMeshGridCell cell45 = new NavMeshGridCell(4, 5);
		cell45.addNode(node4);
		cell45.addNode(node5);
		final NavMeshGridCell cell46 = new NavMeshGridCell(4, 6);
		cell46.addNode(node5);
		final NavMeshGridCell cell47 = new NavMeshGridCell(4, 7);
		cell47.addNode(node5);

		final NavMeshGridCell cell50 = new NavMeshGridCell(5, 0);
		cell50.addNode(node0);
		final NavMeshGridCell cell51 = new NavMeshGridCell(5, 1);
		cell51.addNode(node0);
		cell51.addNode(node1);
		final NavMeshGridCell cell52 = new NavMeshGridCell(5, 2);
		cell52.addNode(node0);
		cell52.addNode(node1);
		final NavMeshGridCell cell53 = new NavMeshGridCell(5, 3);
		cell53.addNode(node2);
		cell53.addNode(node3);
		final NavMeshGridCell cell54 = new NavMeshGridCell(5, 4);
		cell54.addNode(node3);
		final NavMeshGridCell cell55 = new NavMeshGridCell(5, 5);
		cell55.addNode(node4);
		final NavMeshGridCell cell56 = new NavMeshGridCell(5, 6);
		cell56.addNode(node4);
		cell56.addNode(node5);
		final NavMeshGridCell cell57 = new NavMeshGridCell(5, 7);
		cell57.addNode(node5);

		final NavMeshGridCell cell60 = new NavMeshGridCell(6, 0);
		cell60.addNode(node0);
		cell60.addNode(node1);
		final NavMeshGridCell cell61 = new NavMeshGridCell(6, 1);
		cell61.addNode(node0);
		cell61.addNode(node1);
		final NavMeshGridCell cell62 = new NavMeshGridCell(6, 2);
		cell62.addNode(node1);
		final NavMeshGridCell cell63 = new NavMeshGridCell(6, 3);
		cell63.addNode(node2);
		cell63.addNode(node3);
		final NavMeshGridCell cell64 = new NavMeshGridCell(6, 4);
		cell64.addNode(node2);
		cell64.addNode(node3);
		final NavMeshGridCell cell65 = new NavMeshGridCell(6, 5);
		cell65.addNode(node4);
		final NavMeshGridCell cell66 = new NavMeshGridCell(6, 6);
		cell66.addNode(node4);
		cell66.addNode(node5);
		final NavMeshGridCell cell67 = new NavMeshGridCell(6, 7);
		cell67.addNode(node4);
		cell67.addNode(node5);

		final NavMeshGridCell cell70 = new NavMeshGridCell(7, 0);
		cell70.addNode(node0);
		cell70.addNode(node1);
		final NavMeshGridCell cell71 = new NavMeshGridCell(7, 1);
		cell71.addNode(node1);
		final NavMeshGridCell cell72 = new NavMeshGridCell(7, 2);
		cell72.addNode(node1);
		final NavMeshGridCell cell73 = new NavMeshGridCell(7, 3);
		cell73.addNode(node2);
		final NavMeshGridCell cell74 = new NavMeshGridCell(7, 4);
		cell74.addNode(node2);
		cell74.addNode(node3);
		final NavMeshGridCell cell75 = new NavMeshGridCell(7, 5);
		cell75.addNode(node4);
		final NavMeshGridCell cell76 = new NavMeshGridCell(7, 6);
		cell76.addNode(node4);
		final NavMeshGridCell cell77 = new NavMeshGridCell(7, 7);
		cell77.addNode(node4);
		cell77.addNode(node5);

		/* NAV GRID */
		final NavMeshGrid grid = new NavMeshGrid();

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

		/* OUT OF BOUNDS TRIANGLES */
		final Point oob00 = new Point(301, 301);
		final Point oob10 = new Point(499, 301);
		final Point oob01 = new Point(301, 499);
		final Point oob11 = new Point(499, 499);

		final Triangle oobT1 = new Triangle(oob00, oob10, oob11);
		final Triangle oobT2 = new Triangle(oob00, oob01, oob11);

		final List<Triangle> unpathableTriangles = new ArrayList<Triangle>(2);
		unpathableTriangles.add(oobT1);
		unpathableTriangles.add(oobT2);

		/* NAV MAP */
		final NavMeshPointResolver gridTree = new NavMeshPointResolver(grid, 800, 800);
		final IGraphSearch graphSearch = new BreadthFirst();
		final ITransformer<List<IGraphNode>, List<NavMeshNode>> nodeTransform = new NavNodeTransformer();
		final IPathMaterializer<NavMeshNode> pathMaterializer = new NavMeshPathMaterializer(unpathableTriangles);

		final NavMeshNavigator newNav = new NavMeshNavigator(gridTree, graphSearch, nodeTransform, pathMaterializer);

		return newNav;
	}

	private static LineSegment makeLine(final Point a, final Point b) {
		return new LineSegment(a,b);
	}

	private static ValuePair<LineSegment, LineSegment> getAdjPair(final LineSegment a, final LineSegment b) {
		return new ValuePair<LineSegment, LineSegment>(a,b);
	}

	private static Set<NavMeshEdge> emptySet() {
		return new HashSet<NavMeshEdge>();
	}
}
