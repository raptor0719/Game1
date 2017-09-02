package logical.nav.grid;

import logical.nav.graph.NavNodeTriangle;
import util.structures.BinaryTree;

public class NavGrid {
	private final BinaryTree<NavGridCell> tree;
	private final int dimension;

	public NavGrid(final NavGridSpecification spec) {
		this.tree = new BinaryTree<NavGridCell>();
		this.dimension = spec.getDimension();

		createGridTree(spec);
	}

	public NavNodeTriangle[] getNodes(final int xPos, final int yPos) {
		if (xPos >= dimension || yPos >= dimension)
			throw new RuntimeException("Attempted retrieving nodes ");

		traverseTree(xPos, yPos);

		return tree.getCurrent().getNodes();
	}

	private void createGridTree(final NavGridSpecification spec) {
		// The depth from the root is equal to the dimension of the grid
		generateEmptyTree(spec.getDimension());

		for (int x = 0; x != spec.getDimension(); x++) {
			for (int y = 0; y != spec.getDimension(); y++) {
				traverseTree(x, y);
				tree.setCurrent(spec.getCell(x, y));
			}
		}
	}

	/* INTERNAL */

	// TODO: Tree traversal should also maybe be moved into an externalized
	// class to improve testability
	private void traverseTree(final int xPos, final int yPos) {
		tree.resetToRoot();

		if (tree.getCurrent() == null)
			throw new RuntimeException("Attempted to traverse tree when empty");

		traverseTree_x(xPos, yPos, dimension);
	}

	private void traverseTree_y(final int xPos, final int yPos, final int dim) {
		if (yPos < dim/2)
			tree.moveToLeft();
		else
			tree.moveToRight();

		if (tree.getLeft() == null && tree.getRight() == null)
			return;

		traverseTree_x(xPos, yPos, dim/2);
	}

	private void traverseTree_x(final int xPos, final int yPos, final int dim) {
		if (xPos < dim/2)
			tree.moveToLeft();
		else
			tree.moveToRight();

		traverseTree_y(xPos, yPos, dim);
	}

	// TODO: Empty tree generation should be externalized into a provider class
	// to improve testability
	private void generateEmptyTree(final int depth) {
		tree.resetToRoot();

		// Generate the root
		tree.setCurrent(null);

		// Generate the tree
		generateEmptyTree_Recursive(depth);
	}

	private void generateEmptyTree_Recursive(final int depth) {
		if (depth <= 0)
			return;

		// Generate the left tree
		tree.setLeft(null);
		tree.moveToLeft();
		generateEmptyTree_Recursive(depth-1);
		tree.moveToParent();

		// Generate the right tree
		tree.setRight(null);
		tree.moveToRight();
		generateEmptyTree_Recursive(depth-1);
		tree.moveToParent();
	}
}
