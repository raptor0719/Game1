package raptor.engine.game;

import raptor.engine.display.render.IRenderer;
import raptor.engine.ui.input.IMainLoopInputHandler;

public class Game {
	private static long timeSinceLastFrame;
	private static Level currentLevel;

	static {
		timeSinceLastFrame = 0;
		currentLevel = null;
	}

	private final IRenderer renderer;
	private final IMainLoopInputHandler inputHandler;

	protected Game(final Level initLevel, final IRenderer renderer, final IMainLoopInputHandler inputHandler) {
		if (currentLevel != null)
			throw new IllegalStateException("Only 1 instance of the Game is allowed.");
		currentLevel = initLevel;
		this.renderer = renderer;
		this.inputHandler = (inputHandler == null) ? new NoopInputHandler() : inputHandler;
	}

	public void start() {
		long previousTime = System.currentTimeMillis();
		while (true) {
			inputHandler.handleInputs();

			long currentTime = System.currentTimeMillis();
			timeSinceLastFrame = currentTime - previousTime;

			if (timeSinceLastFrame >= 17) {
				previousTime = currentTime;
				currentLevel.advanceFrame();
			}

			renderer.draw(currentLevel.getDrawables());
		}
	}

	public static long getTimeSinceLastFrame() {
		return timeSinceLastFrame;
	}

	public static Level getCurrentLevel() {
		return currentLevel;
	}

	public static void loadLevel(final Level level) {
		if (level == null)
			throw new IllegalArgumentException("Attempted to load null level.");
		level.init();
		currentLevel = level;
	}

	/* INTERNALS */

	private static class NoopInputHandler implements IMainLoopInputHandler {
		@Override
		public void handleInputs() {
			/* no-op */
		}
	}
}
