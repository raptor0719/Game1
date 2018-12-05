package raptor.engine.test.main;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import raptor.engine.game.ui.input.KeyboardInput;
import raptor.engine.util.transformer.ITransformer;

public class JavaAwtKeyTranslator implements ITransformer<Integer, KeyboardInput> {
	private final Map<Integer, KeyboardInput> keyMap;

	public JavaAwtKeyTranslator() {
		keyMap = new HashMap<Integer, KeyboardInput>();

		keyMap.put(KeyEvent.VK_W, KeyboardInput.W_KEY);
		keyMap.put(KeyEvent.VK_A, KeyboardInput.A_KEY);
		keyMap.put(KeyEvent.VK_S, KeyboardInput.S_KEY);
		keyMap.put(KeyEvent.VK_D, KeyboardInput.D_KEY);

		keyMap.put(KeyEvent.VK_UP, KeyboardInput.UP_ARROW);
		keyMap.put(KeyEvent.VK_DOWN, KeyboardInput.DOWN_ARROW);
		keyMap.put(KeyEvent.VK_LEFT, KeyboardInput.LEFT_ARROW);
		keyMap.put(KeyEvent.VK_RIGHT, KeyboardInput.RIGHT_ARROW);
	}

	@Override
	public KeyboardInput transform(final Integer in) {
		return (keyMap.containsKey(in)) ? keyMap.get(in) : null;
	}

}
