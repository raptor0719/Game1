package raptor.engine.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WireModel {
	private final String name;
	private final List<DirectionalWireModelFrame> frameList;
	private final Map<String, DirectionalWireModelFrame> frames;
	private final int hardpointCount;

	public WireModel(final String name, final List<DirectionalWireModelFrame> frameList) {
		this.name = name;
		this.frameList = frameList;
		this.frames = createFrameMap(frameList);
		this.hardpointCount = frameList.get(0).getFrameForDirection(Direction.NORTH).getSortedHardpoints().length;
	}

	public WireModelFrame getFrame(final String name, final Direction direction) {
		if (!frames.containsKey(name))
			return null;

		return frames.get(name).getFrameForDirection(direction);
	}

	public List<DirectionalWireModelFrame> getFrameList() {
		return frameList;
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	public String getName() {
		return name;
	}

	private Map<String, DirectionalWireModelFrame> createFrameMap(final List<DirectionalWireModelFrame> frameList) {
		final Map<String, DirectionalWireModelFrame> frames = new HashMap<>();

		for (final DirectionalWireModelFrame frame : frameList)
			frames.put(frame.getName(), frame);

		return frames;
	}
}
