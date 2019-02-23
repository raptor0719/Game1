package raptor.engine.game.ui.input;

import raptor.engine.game.entity.unit.Unit;

public class GameplayInputManager implements IInputManager {
	private final BinaryInputMap<KeyboardInput> inputs;
	private final Unit player;

	public GameplayInputManager(final BinaryInputMap<KeyboardInput> inputs, final Unit player) {
		this.inputs = inputs;
		this.player = player;
	}

	@Override
	public void processInputs() {
		int xScale = 0;
		int yScale = 0;

		if (inputs.getKeyState(KeyboardInput.W_KEY))
			yScale -= 1;

		if (inputs.getKeyState(KeyboardInput.A_KEY))
			xScale -= 1;

		if (inputs.getKeyState(KeyboardInput.S_KEY))
			yScale += 1;

		if (inputs.getKeyState(KeyboardInput.D_KEY))
			xScale += 1;

		player.move(xScale, yScale);
	}
}
