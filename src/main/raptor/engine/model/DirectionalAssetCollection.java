package raptor.engine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DirectionalAssetCollection<T> {
	private final List<T> assetList;
	private final int[][] mappings;

	private final DirectionalObjectManager<List<T>> manager;

	public DirectionalAssetCollection(final List<T> assetList, int[][] directionMappings) {
		if (assetList == null || directionMappings == null)
			throw new IllegalArgumentException("Null argument given for mapping.");
		if (assetList.size() <= 0 || directionMappings.length <= 0 || directionMappings[0].length <= 0)
			throw new IllegalArgumentException("0 length argument given for mapping.");
		if (!isValidAssets(assetList))
			throw new IllegalArgumentException("Given assets were not valid.");
		if (!isRectangularArray(directionMappings))
			throw new IllegalArgumentException("Mappings differed in length.");
		if (!isValidMapping(assetList, directionMappings))
			throw new IllegalArgumentException("Mappings referenced non-existent asset in asset list.");

		this.assetList = assetList;
		this.mappings = directionMappings;
		this.manager = buildFrameManager(assetList, directionMappings);
	}

	public T getAsset(final int index, final Direction direction) {
		return manager.get(direction).get(index);
	}

	protected List<T> getFrameList() {
		return Collections.unmodifiableList(assetList);
	}

	protected int getCount() {
		return mappings[0].length;
	}

	protected int[][] getMappings() {
		return mappings;
	}

	protected abstract boolean isValidAssets(final List<T> assetList);

	private boolean isRectangularArray(final int[][] mappings) {
		int targetLength = mappings[0].length;
		for (final int[] arr : mappings)
			if (arr.length != targetLength)
				return false;
		return true;
	}

	private boolean isValidMapping(final List<T> assetList, final int[][] mappings) {
		for (final int[] arr : mappings)
			for (final int i : arr)
				if (i >= assetList.size() || i < 0)
					return false;
		return true;
	}

	private DirectionalObjectManager<List<T>> buildFrameManager(final List<T> frameList, int[][] directionMappings) {
		final Map<Direction, List<T>> mappings = new HashMap<>();

		final Direction[] directions = Direction.values();
		final int count = directionMappings[0].length;
		for (int i = 0; i < directions.length; i++) {
			final Direction currentDirection = directions[i];
			if (!mappings.containsKey(currentDirection))
				mappings.put(currentDirection, new ArrayList<T>(count));

			final List<T> currentFrames = mappings.get(currentDirection);
			for (int j = 0; j < count; j++)
				currentFrames.set(j, frameList.get(directionMappings[i][j]));
		}

		return new DirectionalObjectManager<>(mappings);
	}
}
