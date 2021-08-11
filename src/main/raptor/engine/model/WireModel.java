package raptor.engine.model;

import java.util.List;

public class WireModel extends ModelComponent<WireModelFrame> {
	private final int hardpointCount;

	public WireModel(final List<WireModelFrame> frameList, int[][] directionMappings) {
		super(frameList, directionMappings);
		hardpointCount = frameList.get(0).getSortedHardpoints().length;
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	@Override
	protected boolean isValidAssets(final List<WireModelFrame> assetList) {
		if (assetList.isEmpty())
			return false;

		int hardpointCount = assetList.get(0).getSortedHardpoints().length;
		for (final WireModelFrame f : assetList)
			if (f.getSortedHardpoints().length != hardpointCount)
				return false;
		return true;
	}
}
