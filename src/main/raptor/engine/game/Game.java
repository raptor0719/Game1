package raptor.engine.game;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.display.api.IDrawable;
import raptor.engine.game.entity.unit.Unit;
import raptor.engine.game.ui.input.BinaryInputMap;
import raptor.engine.game.ui.input.KeyboardInput;
import raptor.engine.game.viewport.ViewportPointFollower;
import raptor.engine.util.geometry.Vector;
import raptor.modelLibrary.model.Model;

public class Game {
	public Level level;
	public List<Unit> units;

	private final BinaryInputMap<KeyboardInput> inputs;
	private final ViewportPointFollower vuf;

	public Game(final Level initLevel, final BinaryInputMap<KeyboardInput> inputs, final Viewport viewport) {
		level = initLevel;
		this.inputs = inputs;

		units = new ArrayList<Unit>();
		units.add(new Unit(250, 250, 1, 100));
		vuf = new ViewportPointFollower(viewport, units.get(0).getModel().getHardpointPosition(8), 100);

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

		// Perform Logic
		final Unit player = units.get(0);

		if (inputs.getKeyState(KeyboardInput.UP_ARROW))
			player.velocity = new Vector(0, -player.moveSpeed);
		else if (inputs.getKeyState(KeyboardInput.DOWN_ARROW))
			player.velocity = new Vector(0, player.moveSpeed);
		else if (inputs.getKeyState(KeyboardInput.RIGHT_ARROW))
			player.velocity = new Vector(player.moveSpeed, 0);
		else if (inputs.getKeyState(KeyboardInput.LEFT_ARROW))
			player.velocity = new Vector(-player.moveSpeed, 0);
		else
			player.velocity = new Vector(0, 0);

		// FIXME: This is working around an out of index exception happening in the Model
		int modelFramesRemaining = player.model.advanceFrame();
		if (modelFramesRemaining == 1)
			player.model.setAnimation(0);

		// Update
		player.position.setX(player.position.getX() + player.velocity.getX());
		player.position.setY(player.position.getY() + player.velocity.getY());
		vuf.follow();
	}

	public List<IDrawable> getDrawables() {
		final List<IDrawable> d = new ArrayList<IDrawable>();

		d.add(level);

		for (final Unit u : units) {
			final Model m = u.getModel();
			d.addAll(m.getCurrentSprites());
		}

		return d;
	}
}
