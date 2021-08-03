package raptor.engine.ui.input;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import raptor.engine.util.geometry.Point;

public class JavaAwtInputManager implements IInputListener, IMousePositionPoll, MouseListener, KeyListener {

	private final InputMap inputMap;

	private final Map<String, IInputHandler> pressedHandlers;
	private final Map<String, IInputHandler> releasedHandlers;

	public JavaAwtInputManager(final InputMap inputMap) {
		this.inputMap = inputMap;

		this.pressedHandlers = new HashMap<String, IInputHandler>();
		this.releasedHandlers = new HashMap<String, IInputHandler>();
	}

	@Override
	public void assignPressedHandler(final String logicalInput, final IInputHandler handler) {
		pressedHandlers.put(logicalInput, handler);
	}

	@Override
	public void assignReleasedHandler(final String logicalInput, final IInputHandler handler) {
		releasedHandlers.put(logicalInput, handler);
	}

	@Override
	public void unassignPressedHandler(final String logicalInput) {
		pressedHandlers.remove(logicalInput);
	}

	@Override
	public void unassignReleasedHandler(final String logicalInput) {
		releasedHandlers.remove(logicalInput);
	}

	@Override
	public Point getMousePosition() {
		final java.awt.Point point = MouseInfo.getPointerInfo().getLocation();
		return new Point(point.x, point.y);
	}

	@Override
	public void mousePressed(final MouseEvent event) {
		activateHandlers(JavaAwtInputTranslator.translateMouseInput(event.getButton()), pressedHandlers);
	}

	@Override
	public void mouseReleased(final MouseEvent event) {
		activateHandlers(JavaAwtInputTranslator.translateMouseInput(event.getButton()), releasedHandlers);
	}

	@Override
	public void keyPressed(final KeyEvent event) {
		activateHandlers(JavaAwtInputTranslator.translateKeyInput(event.getKeyCode()), pressedHandlers);
	}

	@Override
	public void keyReleased(final KeyEvent event) {
		activateHandlers(JavaAwtInputTranslator.translateKeyInput(event.getKeyCode()), releasedHandlers);
	}

	private void activateHandlers(final PhysicalInput input, final Map<String, IInputHandler> handlers) {
		final Collection<String> logicalInputs = inputMap.getMappedLogicalInputs(input);
		final Point mousePosition = getMousePosition();

		// TODO: Need to have a translation layer here
		for (final String logicalInput : logicalInputs)
			if (handlers.containsKey(logicalInput))
				handlers.get(logicalInput).handleInput(mousePosition.getX(), mousePosition.getY());
	}

	// NO-OP Methods

	@Override
	public void mouseClicked(final MouseEvent event) {
		// no-op
	}

	@Override
	public void mouseEntered(final MouseEvent event) {
		// no-op
	}

	@Override
	public void mouseExited(final MouseEvent event) {
		// no-op
	}

	@Override
	public void keyTyped(final KeyEvent event) {
		// no-op
	}
}
