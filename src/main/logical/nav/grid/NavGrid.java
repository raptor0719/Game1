package logical.nav.grid;

import util.structures.BinaryTree;

public class NavGrid {
	private static final int MINIMUM_DIMENSION = 2;
	private static final int DEFAULT_DIMENSION = 8;

	private final BinaryTree<NavGridCell> tree;

	public NavGrid() {
		this.tree = new BinaryTree<NavGridCell>();

		createGridTree(DEFAULT_DIMENSION);
	}

	private void createGridTree(final int dimension) {

	}
}
