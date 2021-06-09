package raptor.engine.model;

public enum Direction {
	NORTH(0),
	NORTH_EAST(1),
	EAST(2),
	SOUTH_EAST(3),
	SOUTH(4),
	SOUTH_WEST(5),
	WEST(6),
	NORTH_WEST(7);

	private int value;

	private Direction(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
