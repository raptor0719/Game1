package raptor.engine.game.ui.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

public class JavaAwtMouseListener implements MouseListener, MouseMotionListener {
	private final Map<MouseInput, String> inputMapping;
	private final IInputSwitch inputSwitch;
	private final IMousePositionSwitch mousePosSwitch;
	private final JavaAwtMouseTranslator mouseTranslator;

	public JavaAwtMouseListener(final Map<MouseInput, String> inputMapping, final IInputSwitch inputSwitch, final IMousePositionSwitch mousePosSwitch) {
		this.inputMapping = inputMapping;
		this.inputSwitch = inputSwitch;
		this.mousePosSwitch = mousePosSwitch;
		this.mouseTranslator = new JavaAwtMouseTranslator();
	}

	@Override
	public void mousePressed(final MouseEvent mousevent) {
		inputSwitch.setInput(inputMapping.get(mouseTranslator.transform(mousevent.getButton())), 1.0F);
	}

	@Override
	public void mouseReleased(final MouseEvent mousevent) {
		inputSwitch.setInput(inputMapping.get(mouseTranslator.transform(mousevent.getButton())), 0.0F);
	}

	@Override
	public void mouseMoved(final MouseEvent mousevent) {
		mousePosSwitch.setMouseX(mousevent.getX());
		mousePosSwitch.setMouseY(mousevent.getY());
	}

	@Override
	public void mouseClicked(final MouseEvent arg0) {
		/* no-op */
	}

	@Override
	public void mouseEntered(final MouseEvent arg0) {
		/* no-op */
	}

	@Override
	public void mouseExited(final MouseEvent arg0) {
		/* no-op */
	}

	@Override
	public void mouseDragged(final MouseEvent arg0) {
		/* no-op */
	}
}
