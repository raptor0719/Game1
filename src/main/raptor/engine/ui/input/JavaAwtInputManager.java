package raptor.engine.ui.input;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Queue;

import raptor.engine.display.render.ViewportToLocationTransformer;
import raptor.engine.ui.UIAction;
import raptor.engine.util.geometry.Point;

public class JavaAwtInputManager implements IInputManager, IMousePositionPoll, MouseListener, KeyListener {
	private final ViewportToLocationTransformer toLocationTransformer;

	private IInputMap inputMap;
	private Queue<UIAction> actionQueue;

	public JavaAwtInputManager(final ViewportToLocationTransformer toLocationTransformer) {
		this.inputMap = null;
		this.actionQueue = null;
		this.toLocationTransformer = toLocationTransformer;
	}

	@Override
	public void setInputMap(final IInputMap inputMap) {
		this.inputMap = inputMap;
	}

	@Override
	public void setActionQueue(final Queue<UIAction> actionQueue) {
		this.actionQueue = actionQueue;
	}

	@Override
	public Point getMousePosition() {
		final java.awt.Point point = MouseInfo.getPointerInfo().getLocation();
		return new Point(point.x, point.y);
	}

	@Override
	public void mousePressed(final MouseEvent event) {
		final Collection<String> actions = inputMap.getMappedActions(JavaAwtInputTranslator.translateMouseInput(event.getButton()), KeyAction.PRESSED);
		final Point mousePosition = getMousePosition();

		for (final String action : actions)
			actionQueue.add(createAction(mousePosition.getX(), mousePosition.getY(), action));
	}

	@Override
	public void mouseReleased(final MouseEvent event) {
		final Collection<String> actions = inputMap.getMappedActions(JavaAwtInputTranslator.translateMouseInput(event.getButton()), KeyAction.RELEASED);
		final Point mousePosition = getMousePosition();

		for (final String action : actions)
			actionQueue.add(createAction(mousePosition.getX(), mousePosition.getY(), action));
	}

	@Override
	public void keyPressed(final KeyEvent event) {
		final Collection<String> actions = inputMap.getMappedActions(JavaAwtInputTranslator.translateKeyInput(event.getKeyCode()), KeyAction.PRESSED);
		final Point mousePosition = getMousePosition();

		for (final String action : actions)
			actionQueue.add(createAction(mousePosition.getX(), mousePosition.getY(), action));
	}

	@Override
	public void keyReleased(final KeyEvent event) {
		final Collection<String> actions = inputMap.getMappedActions(JavaAwtInputTranslator.translateKeyInput(event.getKeyCode()), KeyAction.RELEASED);
		final Point mousePosition = getMousePosition();

		for (final String action : actions)
			actionQueue.add(createAction(mousePosition.getX(), mousePosition.getY(), action));
	}

	private UIAction createAction(final int viewportMousePositionX, final int viewportMousePositionY, final String action) {
		return new UIAction(viewportMousePositionX, viewportMousePositionY, toLocationTransformer.transformX(viewportMousePositionX), toLocationTransformer.transformY(viewportMousePositionY), action);
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
