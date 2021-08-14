package raptor.engine.model;

import java.util.List;

public class SpriteModel extends DirectionalAssetCollection<SpriteFrame> {
	private final int hardpointCount;

	public SpriteModel(final List<SpriteFrame> spriteFrames, final int[][] directionMappings) {
		super(spriteFrames, directionMappings);
		this.hardpointCount = spriteFrames.get(0).getCount();
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	@Override
	protected boolean isValidAssets(final List<SpriteFrame> assetList) {
		if (assetList.isEmpty())
			return false;

		final int hardpointCount = assetList.get(0).getCount();

		for (final SpriteFrame s : assetList)
			if (s.getCount() != hardpointCount)
				return false;

		return true;
	}
}
