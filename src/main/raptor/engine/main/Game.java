package raptor.engine.main;

import java.util.HashMap;
import java.util.Map;

import raptor.engine.display.render.IRenderer;
import raptor.engine.game.level.Level;
import raptor.engine.game.ui.input.IMainLoopInputHandler;

public class Game {
	private static long timeSinceLastFrame;
	private static Level currentLevel;
	private static Map<String, Object> globalProperties;

	static {
		timeSinceLastFrame = 0;
		currentLevel = null;
		globalProperties = new HashMap<String, Object>();
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
			long currentTime = System.currentTimeMillis();
			timeSinceLastFrame = currentTime - previousTime;

			inputHandler.handleInputs();

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

	public static void setCurrentLevel(final Level level) {
		if (level == null)
			throw new IllegalArgumentException("Attempted to load null level.");
		currentLevel = level;
	}

	public static boolean hasGlobalProperty(final String propertyName) {
		return globalProperties.containsKey(propertyName);
	}

	public static void setGlobalProperty(final String propertyName, final Object propertyValue) {
		globalProperties.put(propertyName, propertyValue);
	}

	public static Object getGlobalProperty(final String propertyName) {
		return globalProperties.get(propertyName);
	}

	/* INTERNALS */

	private static class NoopInputHandler implements IMainLoopInputHandler {
		@Override
		public void handleInputs() {
			/* no-op */
		}
	}
}
