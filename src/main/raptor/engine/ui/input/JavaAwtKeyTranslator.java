package raptor.engine.ui.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import raptor.engine.util.ITransformer;

public class JavaAwtKeyTranslator implements ITransformer<Integer, KeyboardInput> {
	private final Map<Integer, KeyboardInput> keyMap;

	public JavaAwtKeyTranslator() {
		keyMap = new HashMap<Integer, KeyboardInput>();

		keyMap.put(KeyEvent.VK_Q, KeyboardInput.Q_KEY);
		keyMap.put(KeyEvent.VK_W, KeyboardInput.W_KEY);
		keyMap.put(KeyEvent.VK_E, KeyboardInput.E_KEY);
		keyMap.put(KeyEvent.VK_R, KeyboardInput.R_KEY);

		keyMap.put(KeyEvent.VK_A, KeyboardInput.A_KEY);
		keyMap.put(KeyEvent.VK_S, KeyboardInput.S_KEY);
		keyMap.put(KeyEvent.VK_D, KeyboardInput.D_KEY);
		keyMap.put(KeyEvent.VK_F, KeyboardInput.F_KEY);

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
