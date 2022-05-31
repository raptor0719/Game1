package raptor.engine.model;

import java.util.Arrays;
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
		final Hardpoint[] depthSorted = new Hardpoint[hardpoints.length];
		final boolean[] marked = new boolean[hardpoints.length];
		Arrays.fill(marked, false);

		for (int x = 0; x < depthSorted.length; x++) {
			Hardpoint current = null;
			for (int y = 0; y < hardpoints.length; y++) {
				if (marked[y])
					continue;

				if (current == null) {
					current = hardpoints[y];
					continue;
				}

				final Hardpoint compare = hardpoints[y];
				current = (compare.getDepth() < current.getDepth()) ? compare : current;
			}
			depthSorted[x] = current;
		}

		return depthSorted;
	}

	private Map<String, Hardpoint> map(final Hardpoint[] hardpoints) {
		final Map<String, Hardpoint> mapped = new HashMap<>();

		for (final Hardpoint h : hardpoints)
			mapped.put(h.getName(), h);

		return mapped;
	}
}
