package raptor.engine.model;

public class Hardpoint implements IHardpoint {
	private final String name;
	private final int x;
	private final int y;
	private final int depth;

	public Hardpoint(final String name, final int x, final int y, final int depth) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.depth = depth;
	}

	@Override
	public String getName() {
		return name;
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
	public int getDepth() {
		return depth;
	}
}
