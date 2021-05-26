package raptor.engine.model;

import java.util.Collections;
import java.util.List;

public class WireModel {
	private final List<WireModelFrame> frameList;
	private final int[][] mappings;

	public WireModel(final List<WireModelFrame> frameList, int[][] directionMappings) {
		if (frameList == null || directionMappings == null)
			throw new IllegalArgumentException("Null argument given for mapping.");
		if (frameList.size() <= 0 || directionMappings.length <= 0 || directionMappings[0].length <= 0)
			throw new IllegalArgumentException("0 length argument given for mapping.");
		if (!isValidFrames(frameList))
			throw new IllegalArgumentException("Given frames different in number of hardpoints.");
		if (!isRectangularArray(directionMappings))
			throw new IllegalArgumentException("Mappings differed in length.");
		if (!isValidMapping(frameList, directionMappings))
			throw new IllegalArgumentException("Mappings referenced non-existent asset in asset list.");

		this.frameList = frameList;
		this.mappings = directionMappings;
	}

	public WireModelFrame getFrame(final int index, final int direction) {
		return frameList.get(mappings[direction][index]);
	}

	public int getHardpointCount() {
		return frameList.get(0).getHardpointPositions().length;
	}

	protected List<WireModelFrame> getFrameList() {
		return Collections.unmodifiableList(frameList);
	}

	protected int getDirectionCount() {
		return mappings.length;
	}

	protected int getFrameCount() {
		return mappings[0].length;
	}

	protected int[][] getMappings() {
		return mappings;
	}

	private boolean isRectangularArray(final int[][] mappings) {
		int targetLength = mappings[0].length;
		for (final int[] arr : mappings)
			if (arr.length != targetLength)
				return false;
		return true;
	}

	private boolean isValidMapping(final List<WireModelFrame> assetList, final int[][] mappings) {
		for (final int[] arr : mappings)
			for (final int i : arr)
				if (i >= assetList.size() || i < 0)
					return false;
		return true;
	}

	private boolean isValidFrames(final List<WireModelFrame> assetList) {
		if (assetList.isEmpty())
			return false;

		int hardpointCount = assetList.get(0).getHardpointPositions().length;
		for (final WireModelFrame f : assetList)
			if (f.getHardpointPositions().length != hardpointCount)
				return false;
		return true;
	}
}
