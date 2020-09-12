package raptor.engine.main;

import java.util.HashMap;
import java.util.Map;

import raptor.engine.display.render.IRenderer;
import raptor.engine.game.Viewport;
import raptor.engine.game.level.Level;

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

	protected Game(final Level initLevel, final Viewport viewport, final IRenderer renderer) {
		if (currentLevel != null)
			throw new IllegalStateException("Only 1 instance of the Game is allowed.");
		currentLevel = initLevel;
		this.renderer = renderer;
	}

	public void start() {
		long previousTime = System.currentTimeMillis();
		while (true) {
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

	public static boolean hasGlobalProperty(final String propertyName) {
		return globalProperties.containsKey(propertyName);
	}

	public static void setGlobalProperty(final String propertyName, final Object propertyValue) {
		globalProperties.put(propertyName, propertyValue);
	}

	public static Object getGlobalProperty(final String propertyName) {
		return globalProperties.get(propertyName);
	}
}
