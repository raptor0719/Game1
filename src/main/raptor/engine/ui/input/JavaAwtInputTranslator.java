package raptor.engine.ui.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class JavaAwtInputTranslator {
	private static final Map<Integer, PhysicalInput> KEY_INPUT_MAP;
	private static final Map<Integer, PhysicalInput> MOUSE_INPUT_MAP;

	static {
		KEY_INPUT_MAP = new HashMap<Integer, PhysicalInput>();

		KEY_INPUT_MAP.put(KeyEvent.VK_Q, PhysicalInput.Q_KEY);
		KEY_INPUT_MAP.put(KeyEvent.VK_W, PhysicalInput.W_KEY);
		KEY_INPUT_MAP.put(KeyEvent.VK_E, PhysicalInput.E_KEY);
		KEY_INPUT_MAP.put(KeyEvent.VK_R, PhysicalInput.R_KEY);

		KEY_INPUT_MAP.put(KeyEvent.VK_A, PhysicalInput.A_KEY);
		KEY_INPUT_MAP.put(KeyEvent.VK_S, PhysicalInput.S_KEY);
		KEY_INPUT_MAP.put(KeyEvent.VK_D, PhysicalInput.D_KEY);
		KEY_INPUT_MAP.put(KeyEvent.VK_F, PhysicalInput.F_KEY);

		KEY_INPUT_MAP.put(KeyEvent.VK_UP, PhysicalInput.UP_ARROW);
		KEY_INPUT_MAP.put(KeyEvent.VK_DOWN, PhysicalInput.DOWN_ARROW);
		KEY_INPUT_MAP.put(KeyEvent.VK_LEFT, PhysicalInput.LEFT_ARROW);
		KEY_INPUT_MAP.put(KeyEvent.VK_RIGHT, PhysicalInput.RIGHT_ARROW);

		MOUSE_INPUT_MAP = new HashMap<Integer, PhysicalInput>();

		MOUSE_INPUT_MAP.put(MouseEvent.BUTTON1, PhysicalInput.LEFT_MOUSE);
		MOUSE_INPUT_MAP.put(MouseEvent.BUTTON2, PhysicalInput.RIGHT_MOUSE);
		MOUSE_INPUT_MAP.put(MouseEvent.BUTTON3, PhysicalInput.MIDDLE_MOUSE);
	}

	public static PhysicalInput translateKeyInput(final Integer in) {
		return (KEY_INPUT_MAP.containsKey(in)) ? KEY_INPUT_MAP.get(in) : null;
	}

	public static PhysicalInput translateMouseInput(final Integer in) {
		return (MOUSE_INPUT_MAP.containsKey(in)) ? MOUSE_INPUT_MAP.get(in) : null;
	}
}
