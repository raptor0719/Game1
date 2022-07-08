package raptor.engine.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WireModelFrame {
	private final Hardpoint[] sortedHardpoints;
	private final Map<String, Hardpoint> hardpointMap;

	public WireModelFrame(final Hardpoint[] hardpoints) {
		this.sortedHardpoints = sort(hardpoints);
		this.hardpointMap = map(hardpoints);
	}

	public Hardpoint[] getSortedHardpoints() {
		return sortedHardpoints;
	}

	public Hardpoint getHardpoint(final String name) {
		return hardpointMap.get(name);
	}

	private Hardpoint[] sort(final Hardpoint[] hardpoints) {
		final Hardpoint[] toSort = Arrays.copyOf(hardpoints, hardpoints.length);

		Arrays.sort(toSort, new HardpointComparator());

		return toSort;
	}

	private static class HardpointComparator implements Comparator<Hardpoint> {
		@Override
		public int compare(final Hardpoint h1, final Hardpoint h2) {
			if (h1.getDepth() < h2.getDepth())
				return -1;
			else if (h1.getDepth() == h2.getDepth())
				return 0;
			else
				return 1;
		}
	}

	private Map<String, Hardpoint> map(final Hardpoint[] hardpoints) {
		final Map<String, Hardpoint> mapped = new HashMap<>();

		for (final Hardpoint h : hardpoints)
			mapped.put(h.getName(), h);

		return mapped;
	}
}
