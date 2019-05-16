package raptor.engine.game;

import raptor.engine.game.entity.unit.Unit;
import raptor.engine.game.ui.input.BinaryInputMap;
import raptor.engine.game.ui.input.GameplayInputManager;
import raptor.engine.game.ui.input.KeyboardInput;
import raptor.engine.game.viewport.ViewportPointFollower;
import raptor.engine.util.geometry.Point;

public class Game {
	public Level level;
	public Unit player;

	private final ViewportPointFollower vuf;
	private final GameplayInputManager inputManager;

	public Game(final Level initLevel, final BinaryInputMap<KeyboardInput> inputs, final Viewport viewport) {
		level = initLevel;

		player = new Unit(250, 250, 1, 100);
		inputManager = new GameplayInputManager(inputs, player);

		vuf = new ViewportPointFollower(viewport, new Point(player.position.getX(), player.position.getY()), 100);

		player.getModel().setAnimation(0, 300);
	}

	public void advanceFrame(final long timePassed) {
		/*
		* Each frame we do the following, in order:
		* 1. Process user input
		* 2. Run AI
		* 3. Process events
		* 4. Move entities
		* 5. Process collisions
		* 6. Advance models to next frame
		*/

		inputManager.processInputs();

		int remaining = player.getModel().advanceFrame();
		if (remaining <= 0) {
			player.getModel().setAnimation(0, 300);
		}

		// Update
		player.position.setX(player.position.getX() + player.velocity.getX());
		player.position.setY(player.position.getY() + player.velocity.getY());

		vuf.setPoint(new Point(player.position.getX(), player.position.getY()));
		vuf.follow();
	}

	public Unit getDrawables() {
		return player;
	}
}
