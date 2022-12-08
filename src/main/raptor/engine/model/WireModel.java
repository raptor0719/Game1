package raptor.engine.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WireModel {
	private final String name;
	private final List<WireModelFrame> frameList;
	private final Map<String, WireModelFrame> frames;
	private final int hardpointCount;

	public WireModel(final String name, final List<WireModelFrame> frameList) {
		this.name = name;
		this.frameList = frameList;
		this.frames = createFrameMap(frameList);
		this.hardpointCount = frameList.get(0).getSortedHardpoints().length;
	}

	public WireModelFrame getFrame(final String name) {
		if (!frames.containsKey(name))
			return null;

		return frames.get(name);
	}

	public List<WireModelFrame> getFrameList() {
		return frameList;
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	public String getName() {
		return name;
	}

	private Map<String, WireModelFrame> createFrameMap(final List<WireModelFrame> frameList) {
		final Map<String, WireModelFrame> frames = new HashMap<>();

		for (final WireModelFrame frame : frameList)
			frames.put(frame.getName(), frame);

		return frames;
	}
}
