package raptor.engine.nav.mesh;

public class NavMeshGrid {
	private static final int MINIMUM_DIMENSION = 1;

	private final NavMeshGridCell[][] arrayGrid;

	public NavMeshGrid(final int xCells, final int yCells) {
		if (xCells < MINIMUM_DIMENSION || yCells < MINIMUM_DIMENSION)
			throw new IllegalArgumentException(String.format("Specified dimension cannot be less than %d", MINIMUM_DIMENSION));

		this.arrayGrid = new NavMeshGridCell[xCells][yCells];
	}

	public void setCell(final int xPos, final int yPos, final NavMeshGridCell cell) {
		if (xPos >= arrayGrid.length)
			throw new IllegalArgumentException("Specified xPos exceeds grid size");
		else if (yPos >= arrayGrid[0].length)
			throw new IllegalArgumentException("Specified yPos exceeds grid size");
		else if (cell == null)
			throw new IllegalArgumentException("Specified NavGridCell must be non-null");

		arrayGrid[xPos][yPos] = cell;
	}

	public NavMeshGridCell getCell(final int x, final int y) {
		if (x >= arrayGrid.length)
			throw new IllegalArgumentException("Specified xPos exceeds grid size");
		else if (y >= arrayGrid[0].length)
			throw new IllegalArgumentException("Specified yPos exceeds grid size");

		return arrayGrid[x][y];
	}

	public int getXCellCount() {
		return arrayGrid.length;
	}

	public int getYCellCount() {
		return arrayGrid[0].length;
	}
}
