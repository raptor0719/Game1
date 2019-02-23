package raptor.engine.main;

import java.util.Arrays;

import raptor.engine.display.drawer.Drawer;
import raptor.engine.display.drawer.LocationToViewportTransformer;
import raptor.engine.game.Game;
import raptor.engine.game.Level;
import raptor.engine.game.Viewport;
import raptor.engine.game.ui.input.BinaryInputMap;
import raptor.engine.game.ui.input.KeyboardInput;
import raptor.engine.test.main.TestFrame;

public class Main {
	public static void main(final String[] args) {
		final BinaryInputMap<KeyboardInput> kbi = new BinaryInputMap<KeyboardInput>(Arrays.asList(KeyboardInput.values()));
		final TestFrame frame = new TestFrame(800, 600, "Raptor Engine", kbi);
		final Level initLevel = new Level(500, 500);
		final Viewport vp = new Viewport(800, 600, 0, 0);
		final Game game = new Game(initLevel, kbi, vp);
		final Drawer g = new Drawer(800, 600, frame.getGraphics(), new LocationToViewportTransformer(vp, 100));

		long previousTime = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();
			long timePassed = currentTime - previousTime;

			if (timePassed >= 17) {
				previousTime = currentTime;
				game.advanceFrame(timePassed);
			}

			g.draw(game.getDrawables());
		}
	}
}
