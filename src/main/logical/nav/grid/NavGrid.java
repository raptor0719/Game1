package logical.nav.grid;

import logical.nav.graph.NavNode;

public class NavGrid {
	private static final int MINIMUM_DIMENSION = 2;
	private static final int DEFAULT_DIMENSION = 8;

	private final NavGridCell[][] arrayGrid;

	public NavGrid() {
		this(DEFAULT_DIMENSION);
	}

	public NavGrid(final int dimension) {
		if (dimension < MINIMUM_DIMENSION)
			throw new IllegalArgumentException(String.format("Specified dimension cannot be less than %d", MINIMUM_DIMENSION));
		else if (!isPowerOfTwo(dimension))
			throw new IllegalArgumentException("Specified dimension must be a power of two");

		this.arrayGrid = new NavGridCell[dimension][dimension];
	}

	public void setCell(final int xPos, final int yPos, final NavGridCell cell) {
		if (xPos >= arrayGrid.length)
			throw new IllegalArgumentException("Specified xPos exceeds grid size");
		else if (yPos >= arrayGrid[0].length)
			throw new IllegalArgumentException("Specified yPos exceeds grid size");
		else if (cell == null)
			throw new IllegalArgumentException("Specified NavGridCell must be non-null");

		arrayGrid[xPos][yPos] = cell;
	}

	public void setCell(final int xPos, final int yPos, final int cellX, final int cellY, final NavNode... triangles) {
		final NavGridCell cell = new NavGridCell(cellX, cellY);

		for (final NavNode t : triangles)
			cell.addNode(t);

		setCell(xPos, yPos, cell);
	}

	public NavGridCell getCell(final int x, final int y) {
		if (x >= arrayGrid.length)
			throw new IllegalArgumentException("Specified xPos exceeds grid size");
		else if (y >= arrayGrid[0].length)
			throw new IllegalArgumentException("Specified yPos exceeds grid size");

		return arrayGrid[x][y];
	}

	public int getDimension() {
		return arrayGrid.length;
	}

	private boolean isPowerOfTwo(final int num) {
		int i = num;
		while (true) {
			if (i == 1)
				return true;
			else if (i < 1)
				return false;

			i /= 2;
		}
	}
}
