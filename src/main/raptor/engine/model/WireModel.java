package raptor.engine.model;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WireModel {
	private static final FrameCountCalculator frameCountCalculator = new FrameCountCalculator();

	private final List<WireModelFrame> frameList;
	private final WireModelFrame defaultFrame;
	private final int[][] mappings;
	private final Map<Integer, WireModelAnimationDescriptor> animations;
	private final int directionCount;
	private final int size;
	private final int hardpointCount;

	public WireModel(final List<WireModelFrame> frameList, int[][] directionMappings, List<WireModelAnimationDescriptor> animations, final WireModelFrame defaultFrame) {
		if (frameList == null || directionMappings == null)
			throw new IllegalArgumentException("Null argument given for mapping.");
		if (frameList.size() <= 0 || directionMappings.length <= 0 || directionMappings[0].length <= 0)
			throw new IllegalArgumentException("0 length argument given for mapping.");
		if (!isValidFrames(frameList, defaultFrame))
			throw new IllegalArgumentException("Given frames different in number of hardpoints.");
		if (!isRectangularArray(directionMappings))
			throw new IllegalArgumentException("Mappings differed in length.");
		if (!isValidMapping(frameList, directionMappings))
			throw new IllegalArgumentException("Mappings referenced non-existent asset in asset list.");

		this.frameList = frameList;
		this.defaultFrame = defaultFrame;
		this.mappings = directionMappings;
		this.animations = buildAnimationMap(animations);
		this.directionCount = directionMappings.length;
		this.size = directionMappings[0].length;
		this.hardpointCount = frameList.get(0).getHardpointPositions().length;
	}

	public AbstractList<Integer> getAnimationFrames(final int animationId, final int totalFrames) {
		if (!animations.containsKey(animationId))
			throw new IllegalArgumentException("Animation does not exist. Given Id: " + animationId);
		if (totalFrames < 0)
			throw new IllegalArgumentException("Negative value given for frame count.");

		final WireModelAnimationDescriptor descriptor = animations.get(animationId);
		return new WireModelAnimationFrames(descriptor.getFrames(), buildCounts(totalFrames, descriptor.getFrameProportions(), descriptor.totalProportions()));
	}

	public WireModelFrame getFrame(final int index, final int direction) {
		if (index >= size)
			throw new IndexOutOfBoundsException("Given frame by index does not exist. Index given: " + index);
		if (direction >= directionCount || direction < 0)
			throw new IndexOutOfBoundsException("Given direction does not exist. Direction given: " + direction);

		if (index < 0)
			return defaultFrame;

		return frameList.get(mappings[direction][index]);
	}

	public WireModelFrame getDefaultFrame() {
		return defaultFrame;
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	private int[] buildCounts(final int total, final int[] portions, final int totalPortions) {
		final int[] counts = new int[portions.length];

		int currentTotal = total;
		int currentPortions = totalPortions;
		for (int i = 0; i < counts.length; i++) {
			final int calculated = frameCountCalculator.calculateFrameCount(currentTotal, portions[i], currentPortions);
			counts[i] = calculated;
			currentTotal -= calculated;
			currentPortions -= portions[i];
		}

		return counts;
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

	private boolean isValidFrames(final List<WireModelFrame> assetList, final WireModelFrame defaultFrame) {
		int hardpointCount = defaultFrame.getHardpointPositions().length;
		for (final WireModelFrame f : assetList)
			if (f.getHardpointPositions().length != hardpointCount)
				return false;
		return true;
	}

	private Map<Integer, WireModelAnimationDescriptor> buildAnimationMap(final List<WireModelAnimationDescriptor> animations) {
		final Map<Integer, WireModelAnimationDescriptor> map = new HashMap<>();
		for (final WireModelAnimationDescriptor a : animations)
			map.put(a.getId(), a);
		return map;
	}
}
