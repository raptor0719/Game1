package raptor.engine.model;

import java.util.List;

public class SpriteCollection extends DirectionalAssetCollection<Sprite> {
	private final String name;

	public SpriteCollection(final String name, final List<Sprite> physicalSprites, final int[][] mappings) {
		super(physicalSprites, mappings);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	protected boolean isValidAssets(final List<Sprite> assetList) {
		return assetList != null && assetList.size() > 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpriteCollection other = (SpriteCollection) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
