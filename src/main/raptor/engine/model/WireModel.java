package raptor.engine.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WireModel {
	private final List<WireModelFrame> frameList;
	private final int[][] mappings;

	private final DirectionalObjectManager<WireModelFrame> manager;

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
		this.manager = buildFrameManager(frameList, directionMappings);
	}

	public WireModelFrame getFrame(final int index, final Direction direction) {
		return manager.get(index, direction);
	}

	public int getHardpointCount() {
		return manager.count();
	}

	protected List<WireModelFrame> getFrameList() {
		return Collections.unmodifiableList(frameList);
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

	private DirectionalObjectManager<WireModelFrame> buildFrameManager(final List<WireModelFrame> frameList, int[][] directionMappings) {
		final Map<Direction, WireModelFrame[]> mappings = new HashMap<>();

		final Direction[] directions = Direction.values();
		final int count = directionMappings[0].length;
		for (int i = 0; i < directions.length; i++) {
			final Direction currentDirection = directions[i];
			if (!mappings.containsKey(currentDirection))
				mappings.put(currentDirection, new WireModelFrame[count]);

			final WireModelFrame[] currentFrames = mappings.get(currentDirection);
			for (int j = 0; j < count; j++)
				currentFrames[j] = frameList.get(directionMappings[i][j]);
		}

		return new DirectionalObjectManager<>(mappings);
	}
}
