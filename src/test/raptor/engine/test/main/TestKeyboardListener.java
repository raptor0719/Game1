package raptor.engine.test.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import raptor.engine.game.ui.input.BinaryInputMap;
import raptor.engine.game.ui.input.JavaAwtKeyTranslator;
import raptor.engine.game.ui.input.KeyboardInput;

public class TestKeyboardListener implements KeyListener {
	private final BinaryInputMap<KeyboardInput> inputs;
	private final JavaAwtKeyTranslator translator;

	public TestKeyboardListener(final BinaryInputMap<KeyboardInput> inputs) {
		this.inputs = inputs;
		translator = new JavaAwtKeyTranslator();
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		activateKey(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		deactivateKey(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// NO OP
	}

	private void activateKey(final Integer kbi) {
		nullSafeKeyState(kbi, true);
	}

	private void deactivateKey(final Integer kbi) {
		nullSafeKeyState(kbi, false);
	}

	private void nullSafeKeyState(final Integer kbi, final boolean state) {
		if (kbi == null)
			return;
		inputs.setKeyState(translator.transform(kbi), state);
	}
}
