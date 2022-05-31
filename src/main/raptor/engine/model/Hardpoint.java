package raptor.engine.model;

public class Hardpoint {
	private final String name;
	private final int x;
	private final int y;
	private final int rotation;
	private final int depth;

	public Hardpoint(final String name, final int x, final int y, final int rotation, final int depth) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.depth = depth;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRotation() {
		return rotation;
	}

	public int getDepth() {
		return depth;
	}
}
