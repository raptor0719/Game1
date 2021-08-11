package raptor.engine.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WireModelFrame {
	private final IHardpoint[] sortedHardpoints;
	private final Map<String, IHardpoint> hardpointMap;

	public WireModelFrame(final IHardpoint[] hardpoints) {
		this.sortedHardpoints = sort(hardpoints);
		this.hardpointMap = map(hardpoints);
	}

	public IHardpoint[] getSortedHardpoints() {
		return sortedHardpoints;
	}

	public IHardpoint getHardpoint(final String name) {
		return hardpointMap.get(name);
	}

	private IHardpoint[] sort(final IHardpoint[] hardpoints) {
		final IHardpoint[] depthSorted = new IHardpoint[hardpoints.length];
		final boolean[] marked = new boolean[hardpoints.length];
		Arrays.fill(marked, false);

		for (int x = 0; x < depthSorted.length; x++) {
			IHardpoint current = null;
			for (int y = 0; y < hardpoints.length; y++) {
				if (marked[y])
					continue;

				if (current == null) {
					current = hardpoints[y];
					continue;
				}

				final IHardpoint compare = hardpoints[y];
				current = (compare.getDepth() < current.getDepth()) ? compare : current;
			}
			depthSorted[x] = current;
		}

		return depthSorted;
	}

	private Map<String, IHardpoint> map(final IHardpoint[] hardpoints) {
		final Map<String, IHardpoint> mapped = new HashMap<>();

		for (final IHardpoint h : hardpoints)
			mapped.put(h.getName(), h);

		return mapped;
	}
}
