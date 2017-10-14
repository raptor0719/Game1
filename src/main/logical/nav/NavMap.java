package logical.nav;

import java.util.List;

import logical.nav.grid.NavGridTree;
import util.geometry.Point;

public class NavMap {

	private final NavGridTree navGrid;

	public NavMap(final NavGridTree navGrid) {
		this.navGrid = navGrid;
	}

	public List<Point> getPath(final Point start, final Point end) {
		return null;
	}
}
