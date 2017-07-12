package logical.nav.grid;

import java.util.ArrayList;
import java.util.List;

import logical.nav.NavNodeTriangle;

public class NavGridCell {
	private final List<NavNodeTriangle> nodes;
	private final int posx;
	private final int posy;

	public NavGridCell(final int posx, final int posy) {
		nodes = new ArrayList<NavNodeTriangle>(5);
		this.posx = posx;
		this.posy = posy;
	}

	public void addNode(final NavNodeTriangle node) {
		if (node != null)
			nodes.add(node);
	}

	public NavNodeTriangle[] getNodes() {
		return nodes.toArray(new NavNodeTriangle[nodes.size()]);
	}

	public int getX() {
		return posx;
	}

	public int getY() {
		return posy;
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

		if (posx != o1.getX())
			return false;
		if (posy != o1.getY())
			return false;

		return true;
	}
}
