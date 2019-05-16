package raptor.engine.model;

public class SpriteModelDescriptor {
	private final int targetHardpoint;
	private final SpriteModel sprite;

	public SpriteModelDescriptor(final int targetHardpoint, final SpriteModel sprite) {
		this.targetHardpoint = targetHardpoint;
		this.sprite = sprite;
	}

	public int getTargetHardpoint() {
		return targetHardpoint;
	}

	public SpriteModel getSpriteModel() {
		return sprite;
	}
}
