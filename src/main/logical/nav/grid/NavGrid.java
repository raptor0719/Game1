package logical.nav.grid;

import util.structures.BinaryTree;

public class NavGrid {
	private final BinaryTree<NavGridCell> tree;

	public NavGrid(final NavGridSpecification spec) {
		this.tree = new BinaryTree<NavGridCell>();

		createGridTree(spec);
	}

	private void createGridTree(final NavGridSpecification spec) {
		generateEmptyTree(spec.getDimension());
	}

	private void traverseTree(final int xPos, final int yPos) {
		tree.resetToRoot();
		
	}
	
	/* INTERNAL */
	
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
