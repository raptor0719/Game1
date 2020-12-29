package raptor.engine.ui.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class JavaAwtKeyboardListener implements KeyListener {
	private final Map<KeyboardInput, String> inputMapping;
	private final IInputSwitch inputSwitch;
	private final JavaAwtKeyTranslator keyTranslator;

	public JavaAwtKeyboardListener(final Map<KeyboardInput, String> inputMapping, final IInputSwitch inputSwitch) {
		this.inputMapping = inputMapping;
		this.inputSwitch = inputSwitch;
		this.keyTranslator = new JavaAwtKeyTranslator();
	}

	@Override
	public void keyPressed(final KeyEvent keyvent) {
		inputSwitch.setInput(inputMapping.get(keyTranslator.transform(keyvent.getKeyCode())), 1.0F);
	}

	@Override
	public void keyReleased(final KeyEvent keyvent) {
		inputSwitch.setInput(inputMapping.get(keyTranslator.transform(keyvent.getKeyCode())), 0.0F);
	}

	@Override
	public void keyTyped(final KeyEvent arg0) {
		/* no-op */
	}
}
