package raptor.engine.model;

import java.util.Map;

public class DirectionalWireModelFrame {
	private final String name;
	private final Map<Direction, WireModelFrame> frames;

	public DirectionalWireModelFrame(final String name, final Map<Direction, WireModelFrame> frames) {
		this.name = name;
		this.frames = frames;

		checkAllDirectionsExist();
		framesMatch();
	}

	public WireModelFrame getFrameForDirection(final Direction direction) {
		return frames.get(direction);
	}

	public String getName() {
		return name;
	}

	private void checkAllDirectionsExist() {
		for (final Direction d : Direction.values())
			if (!frames.containsKey(d))
				throw new IllegalArgumentException(String.format("All directions must be represented in a frame. %s was not found.", d.name()));
	}

	private void framesMatch() {
		final IHardpoint[] compare = frames.get(Direction.NORTH).getSortedHardpoints();

		for (final WireModelFrame current : frames.values()) {
			if (current.getSortedHardpoints().length != compare.length)
				throw new IllegalArgumentException("Frames did not have the same number of hardpoints.");

			for (final IHardpoint hardpoint : compare) {
				final IHardpoint currentHardpoint = current.getHardpoint(hardpoint.getName());

				if (currentHardpoint == null)
					throw new IllegalArgumentException("All frames must have the same hardpoints.");
			}
		}
	}
}
