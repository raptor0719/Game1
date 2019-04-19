package raptor.engine.game;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.display.api.IDrawable;
import raptor.engine.display.drawer.SpriteWrapper;
import raptor.engine.game.entity.unit.Unit;
import raptor.engine.game.ui.input.BinaryInputMap;
import raptor.engine.game.ui.input.GameplayInputManager;
import raptor.engine.game.ui.input.KeyboardInput;
import raptor.engine.game.viewport.ViewportPointFollower;
import raptor.engine.test.main.TestModelFactory;

public class Game {
	public Level level;
	public List<Unit> units;

	private final ViewportPointFollower vuf;
	private final GameplayInputManager inputManager;

	public Game(final Level initLevel, final BinaryInputMap<KeyboardInput> inputs, final Viewport viewport) {
		level = initLevel;

		units = new ArrayList<Unit>();
		units.add(new Unit(250, 250, 1, 100));
		inputManager = new GameplayInputManager(inputs, units.get(0));
		vuf = new ViewportPointFollower(viewport, units.get(0).getModel().getHardpointPosition(0), 100);

		units.get(0).model.setAnimation(0);
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

		final Unit player = units.get(0);
		player.getModel().advanceFrame();

		// Update
		player.position.setX(player.position.getX() + player.velocity.getX());
		player.position.setY(player.position.getY() + player.velocity.getY());
		vuf.follow();
	}

	public List<IDrawable> getDrawables() {
		final List<IDrawable> d = new ArrayList<IDrawable>();

		d.add(level);

		for (final Unit u : units) {
			d.add(u);

			for (int i = 0; i < u.getModel().getHardpointCount(); i++) {
				d.add(new SpriteWrapper(u.model.getSprite(i).getImage(), u.model.getHardpointPosition(i)));
			}

			d.add(new SpriteWrapper(TestModelFactory.getRectangle(10, 10).getImage(), u.getPosition()));
		}

		return d;
	}
}
