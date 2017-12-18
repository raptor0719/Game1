package logical.nav.grid;

import logical.nav.api.INavigationMap;
import logical.nav.graph.NavNode;
import util.geometry.Point;
import util.geometry.Triangle;
import util.structures.BinaryTree;

public class NavGridTree implements INavigationMap<NavNode> {
	private final BinaryTree<NavGridCell> tree;
	private final NavGrid grid;
	private final int realXSize;
	private final int realYSize;

	public NavGridTree(final NavGrid grid, final int realXSize, final int realYSize) {
		this.tree = new BinaryTree<NavGridCell>();
		this.grid = grid;

		if (realXSize % grid.getDimension() != 0 || realYSize % grid.getDimension() != 0)
			throw new RuntimeException("Size of actual space must be divisible by the grid dimensions.");

		this.realXSize = realXSize;
		this.realYSize = realYSize;

		createGridTree(grid);
	}

	@Override
	public NavNode resolvePoint(final Point p) {
		// Don't confuse the true coordinate with the cell coordinate
		final Point cellCoord = convertRealToCell(p);
		System.out.println("Cell coordinates are: " + cellCoord);
		final NavNode[] nodes = getNodes(cellCoord);

		for (final NavNode node : nodes)
			if (isPointInTriangle(p, node.getData()))
				return node;

		return null;
	}

	public NavNode[] getNodes(final Point point) {
		if (point.getX() >= grid.getDimension() || point.getY() >= grid.getDimension())
			throw new RuntimeException(String.format("Attempted retrieving nodes in cell (%d,%d) which is out of the dimension (%d)", point.getX(), point.getY(), grid.getDimension()));

		traverseTree(point.getX(), point.getY());
		System.out.println("Tree query resulted in " + tree.getCurrent());

		return tree.getCurrent().getNodes();
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
		// The depth from the root is equal to the dimension of the grid\
		if (spec.getDimension() == 8)
			generateEmptyTree(6);
		else
			throw new RuntimeException("A dimension of 8 is required currently for nav grids.");

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

		if (!tree.hasLeft())
			throw new RuntimeException("Attempted to traverse tree when empty");

		traverseTree_x(xPos, yPos, grid.getDimension());
	}

	private void traverseTree_y(final int xPos, final int yPos, final int dim) {
//		System.out.println("Deciding y with: xPos=" + xPos + ", yPos=" + yPos + ", dim=" + dim);
		boolean moved;
		if (yPos < dim/2)
			moved = tree.moveToLeft();
		else
			moved = tree.moveToRight();

		if (!moved)
			throw new RuntimeException("Excepected to be able to move but couldnt.");

 		if (!tree.hasLeft() && !tree.hasRight())
			return;

		traverseTree_x(xPos, yPos, dim/2);
	}

	private void traverseTree_x(final int xPos, final int yPos, final int dim) {
//		System.out.println("Deciding x with: xPos=" + xPos + ", yPos=" + yPos + ", dim=" + dim);

		boolean moved;
		if (xPos < dim/2)
			moved = tree.moveToLeft();
		else
			moved = tree.moveToRight();

		if (!moved)
			throw new RuntimeException("Excepected to be able to move but couldnt.");

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
		if (!tree.moveToLeft())
			throw new RuntimeException();
		generateEmptyTree_Recursive(depth-1);
		if (!tree.moveToParent())
			throw new RuntimeException();

		// Generate the right tree
		tree.setRight(null);
		if (!tree.moveToRight())
			throw new RuntimeException();
		generateEmptyTree_Recursive(depth-1);
		if (!tree.moveToParent())
			throw new RuntimeException();
	}
}
