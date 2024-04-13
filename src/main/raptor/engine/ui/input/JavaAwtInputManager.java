package raptor.engine.ui.input;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.Queue;

import raptor.engine.display.render.IViewport;
import raptor.engine.display.render.ViewportToLocationTransformer;
import raptor.engine.ui.UIAction;
import raptor.engine.util.geometry.Point;

public class JavaAwtInputManager implements IInputManager, IMousePositionPoll, MouseListener, MouseMotionListener, KeyListener {
	private final IViewport viewport;
	private final ViewportToLocationTransformer toLocationTransformer;

	private final Robot physicalMouseMover;
	private final Point virtualMousePosition;

	private IInputMap inputMap;
	private Queue<UIAction> actionQueue;

	public JavaAwtInputManager(final GraphicsDevice primaryGraphicsDevice, final IViewport viewport) throws AWTException {
		this.viewport = viewport;
		this.toLocationTransformer = new ViewportToLocationTransformer(viewport);

		this.physicalMouseMover = new Robot(primaryGraphicsDevice);
		physicalMouseMover.mouseMove(getPhysicalMouseX(), getPhysicalMouseY());
		this.virtualMousePosition = new Point(getPhysicalMouseX(), getPhysicalMouseY());

		this.inputMap = null;
		this.actionQueue = null;
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
	public void mouseMoved(final MouseEvent event) {
		resolvePhysicalMouseMove(event);
	}

	@Override
	public void mouseDragged(final MouseEvent event) {
		resolvePhysicalMouseMove(event);
	}

	@Override
	public int getMousePositionX() {
		return virtualMousePosition.getX();
	}

	@Override
	public int getMousePositionY() {
		return virtualMousePosition.getY();
	}

	@Override
	public void mousePressed(final MouseEvent event) {
		createActions(inputMap.getMappedActions(JavaAwtInputTranslator.translateMouseInput(event.getButton()), KeyAction.PRESSED), getMousePositionX(), getMousePositionY());
	}

	@Override
	public void mouseReleased(final MouseEvent event) {
		createActions(inputMap.getMappedActions(JavaAwtInputTranslator.translateMouseInput(event.getButton()), KeyAction.RELEASED), getMousePositionX(), getMousePositionY());
	}

	@Override
	public void keyPressed(final KeyEvent event) {
		createActions(inputMap.getMappedActions(JavaAwtInputTranslator.translateKeyInput(event.getKeyCode()), KeyAction.PRESSED), getMousePositionX(), getMousePositionY());
	}

	@Override
	public void keyReleased(final KeyEvent event) {
		createActions(inputMap.getMappedActions(JavaAwtInputTranslator.translateKeyInput(event.getKeyCode()), KeyAction.RELEASED), getMousePositionX(), getMousePositionY());
	}

	// INTERNALS

	private int getPhysicalMouseX() {
		return viewport.getWidth()/2;
	}

	private int getPhysicalMouseY() {
		return viewport.getHeight()/2;
	}

	private void resolvePhysicalMouseMove(final MouseEvent event) {
		if (event.getXOnScreen() == getPhysicalMouseX() && event.getYOnScreen() == getPhysicalMouseY())
			return;

		virtualMousePosition.setX(Math.min(Math.max(virtualMousePosition.getX() + event.getXOnScreen() - getPhysicalMouseX(), 0), viewport.getWidth()));
		virtualMousePosition.setY(Math.min(Math.max(virtualMousePosition.getY() + event.getYOnScreen() - getPhysicalMouseY(), 0), viewport.getHeight()));
		physicalMouseMover.mouseMove(getPhysicalMouseX(), getPhysicalMouseY());
	}

	private void createActions(final Collection<String> actions, final int mousePositionX, final int mousePositionY) {
		for (final String action : actions)
			actionQueue.add(createAction(mousePositionX, mousePositionY, action));
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
