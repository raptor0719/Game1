package raptor.engine.game;

import java.util.List;

import raptor.engine.game.entity.unit.Unit;

public class Game {
	public Viewport viewport;
	public Level level;
	public List<Unit> units;

	public Game(final Level initLevel) {
		viewport = new Viewport(initLevel.width, initLevel.height, 0, 0);
		level = initLevel;
	}

	public void advanceFrame() {

	}
}
