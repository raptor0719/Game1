package raptor.engine.game;

import raptor.engine.display.render.IRenderer;
import raptor.engine.display.render.LocationToViewportTransformer;
import raptor.engine.display.render.ViewportToLocationTransformer;

public class Game {
	private static long timeSinceLastFrame;
	private static Level currentLevel;

	private static IRenderer renderer;

	private static boolean gameInstantiated;

	private static ViewportToLocationTransformer toLocationTransformer;
	private static LocationToViewportTransformer toViewportTransformer;

	static {
		timeSinceLastFrame = 0;
		currentLevel = null;
		gameInstantiated = false;
	}

	protected Game(final Level initLevel, final IRenderer setRenderer) {
		if (currentLevel != null)
			throw new IllegalStateException("Only 1 instance of the Game is allowed.");
		currentLevel = initLevel;
		renderer = setRenderer;
		gameInstantiated = true;
		toLocationTransformer = new ViewportToLocationTransformer(setRenderer.getViewport());
		toViewportTransformer = new LocationToViewportTransformer(setRenderer.getViewport());
	}

	public void start() {
		long previousTime = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();
			timeSinceLastFrame = currentTime - previousTime;

			if (timeSinceLastFrame >= 17) {
				previousTime = currentTime;
				currentLevel.tick();
			}

			renderer.queueDrawables(currentLevel.getDrawables());

			renderer.draw();
		}
	}

	public static long getTimeSinceLastFrame() {
		return timeSinceLastFrame;
	}

	public static Level getCurrentLevel() {
		if (!gameInstantiated)
			throw new IllegalStateException("The game must be instantiated before static calls can be made.");
		return currentLevel;
	}

	public static void loadLevel(final Level level) {
		if (level == null)
			throw new IllegalArgumentException("Attempted to load null level.");
		level.init();
		currentLevel = level;
	}

	public static IRenderer getRenderer() {
		if (!gameInstantiated)
			throw new IllegalStateException("The game must be instantiated before static calls can be made.");
		return renderer;
	}

	public static ViewportToLocationTransformer getViewportToLocation() {
		return toLocationTransformer;
	}

	public static LocationToViewportTransformer getLocationToViewport() {
		return toViewportTransformer;
	}
}
