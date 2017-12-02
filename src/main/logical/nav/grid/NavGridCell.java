package logical.nav.grid;

import java.util.ArrayList;
import java.util.List;

import logical.nav.graph.NavNode;

// TODO: This object should be read only once created, create a builder for this
// 		and remove the addNode method
public class NavGridCell {
	private final List<NavNode> nodes;
	private final int xPos;
	private final int yPos;

	public NavGridCell(final int xPos, final int yPos) {
		nodes = new ArrayList<NavNode>(5);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void addNode(final NavNode node) {
		if (node != null)
			nodes.add(node);
	}

	public NavNode[] getNodes() {
		return nodes.toArray(new NavNode[nodes.size()]);
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof NavGridCell))
			return false;

		final NavGridCell o1 = (NavGridCell)o;

		if (xPos != o1.getX())
			return false;
		if (yPos != o1.getY())
			return false;

		return true;
	}
}
