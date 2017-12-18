package logical.nav.grid;

import logical.nav.api.INavigationMap;
import logical.nav.graph.NavNode;
import util.geometry.Point;
import util.geometry.Triangle;

public class NavGridPointResolver implements INavigationMap<NavNode> {
	private final NavGrid grid;
	private final int realXSize;
	private final int realYSize;

	public NavGridPointResolver(final NavGrid grid, final int realXSize, final int realYSize) {
		this.grid = grid;

		if (realXSize % grid.getDimension() != 0 || realYSize % grid.getDimension() != 0)
			throw new RuntimeException("Size of actual space must be divisible by the grid dimensions.");

		this.realXSize = realXSize;
		this.realYSize = realYSize;
	}

	@Override
	public NavNode resolvePoint(final Point p) {
		// Don't confuse the true coordinate with the cell coordinate
		final Point cellCoord = convertRealToCell(p);
//		System.out.println("Cell coordinates are: " + cellCoord);
		final NavNode[] nodes = getNodes(cellCoord);

//		System.out.print("Node list is: ");
//		for (NavNode n : nodes)
//			System.out.print(n + ", ");
//		System.out.println("");

		for (final NavNode node : nodes)
			if (isPointInTriangle(p, node.getData()))
				return node;

		return null;
	}

	private NavNode[] getNodes(final Point p) {
		return grid.getCell(p.getX(), p.getY()).getNodes();
	}

	// Translates the true coordinates given to a cell coordinate
	private Point convertRealToCell(final Point p) {
		final int xPartitionSize = realXSize / grid.getDimension();
		final int YPartitionSize = realYSize / grid.getDimension();

		final int x = findPartition(p.getX(), xPartitionSize, grid.getDimension());
		final int y = findPartition(p.getY(), YPartitionSize, grid.getDimension());

		return new Point(x,y);
	}

	private int findPartition(final int val, final int partitionSize, final int max) {
		int iter = 1;
		while(iter <= max) {
			if (val < iter*partitionSize) {
				return iter-1;
			}
			iter++;
		}
		throw new RuntimeException("Given point out of bounds");
	}

	// should this be a part of the Triangle class?
	private boolean isPointInTriangle(final Point p, final Triangle t) {
		final Point p0 = t.getPoints()[0];
		final Point p1 = t.getPoints()[1];
		final Point p2 = t.getPoints()[2];

		// Calculate Area of all 4 triangles (A, A1, A2, A3)
		final double A = t.getArea();
		final double A1 = new Triangle(p, p0, p1).getArea();
		final double A2 = new Triangle(p, p1, p2).getArea();
		final double A3 = new Triangle(p, p2, p0).getArea();

		// May need to watch floating point precision here
		return (A1 + A2 + A3) == A;
	}
}
