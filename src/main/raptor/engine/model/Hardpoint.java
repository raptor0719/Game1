package raptor.engine.model;

public class Hardpoint implements HardpointPosition {
	private final int x;
	private final int y;
	private final int rotation;
	private final int depth;

	public Hardpoint(final int x, final int y, final int rotation, final int depth) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.depth = depth;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getRotation() {
		return rotation;
	}

	@Override
	public int getDepth() {
		return depth;
	}
}
