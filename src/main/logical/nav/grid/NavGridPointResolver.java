package logical.nav.grid;

import logical.nav.api.INavigationMap;
import logical.nav.graph.NavNode;
import util.geometry.Point;

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
		final NavNode[] nodes = getNodes(cellCoord);

		for (final NavNode node : nodes)
			if (node.getData().containsPoint(p))
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
}
