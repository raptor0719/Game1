package logical.nav.grid;

import logical.nav.api.INavigationMap;
import logical.nav.graph.NavNode;
import logical.nav.graph.api.IGraphNode;
import util.geometry.Point;
import util.geometry.Triangle;
import util.structures.BinaryTree;

public class NavGridTree implements INavigationMap<Triangle> {
	private final BinaryTree<NavGridCell> tree;
	private final NavGrid grid;

	public NavGridTree(final NavGrid grid) {
		this.tree = new BinaryTree<NavGridCell>();
		this.grid = grid;

		createGridTree(grid);
	}

	@Override
	public IGraphNode<Triangle> resolvePoint(final Point p) {
		final NavNode[] nodes = getNodes(p);
		
		for (final NavNode node : nodes)
			if (isPointInTriangle(p, node.getData()))
				return node;
		
		return null;
	}

	public NavNode[] getNodes(final Point point) {
		if (point.getX() >= grid.getDimension() || point.getY() >= grid.getDimension())
			throw new RuntimeException(String.format("Attempted retrieving nodes in cell (%d,%d) which is out of the dimension (%d)", point.getX(), point.getY(), grid.getDimension()));

		traverseTree(point.getX(), point.getY());

		return tree.getCurrent().getNodes();
	}
	
	// should this be a part of the Triangle class?
	private boolean isPointInTriangle(final Point p, final Triangle t) {
		final Point p0 = t.getPoints()[0];
		final Point p1 = t.getPoints()[1];
		final Point p2 = t.getPoints()[2];
		
		// Calculate Area of all 4 triangles (A, A1, A2, A3)
		final float A = getTriangleArea(t);
		final float A1 = getTriangleArea(new Triangle(p, p0, p1));
		final float A2 = getTriangleArea(new Triangle(p, p1, p2));
		final float A3 = getTriangleArea(new Triangle(p, p2, p0));
		
		// May need to watch floating point precision here
		return (A1 + A2 + A3) == A;
	}

	// Area= [x1(y2-y3)+x2(y3-y1)+x3(y1-y2)]/2
	private float getTriangleArea(final Triangle t) {
		final Point[] points = t.getPoints();
		
		final int x1 = points[0].getX();
		final int y1 = points[0].getY();
		
		final int x2 = points[1].getX();
		final int y2 = points[1].getY();
		
		final int x3 = points[2].getX();
		final int y3 = points[2].getY();
		
		return (x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2))/2;
	}

	private void createGridTree(final NavGrid spec) {
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

		traverseTree_x(xPos, yPos, grid.getDimension());
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
