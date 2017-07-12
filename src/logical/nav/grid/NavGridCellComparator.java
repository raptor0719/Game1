package logical.nav.grid;

import java.util.Comparator;

public class NavGridCellComparator implements Comparator<NavGridCell> {
	@Override
	public int compare(final NavGridCell o1, final NavGridCell o2) {
		if (o1 == null || o2 == null)
			throw new RuntimeException("Attempted comparison of null NavGridCell.");

		// TODO Finish this implementation here

		return 0;
	}
}
