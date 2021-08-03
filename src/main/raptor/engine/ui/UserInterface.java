package raptor.engine.ui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.ui.elements.UIElement;
import raptor.engine.ui.input.IActionHandler;
import raptor.engine.ui.input.IInputManager;
import raptor.engine.ui.input.IMousePositionPoll;
import raptor.engine.util.IdProvider;
import raptor.engine.util.geometry.Point;

public class UserInterface implements IDrawable {
	public static final String ACTIVATE_BUTTON_ACTION = "ACTIVATE";

	public static final IdProvider UI_ID_PROVIDER = new IdProvider();

	private final Queue<UIAction> receiveActionQueue;
	private final IInputManager inputManager;
	private final IMousePositionPoll mousePositionPoll;

	private UIState currentState;

	public UserInterface(final UIState startState, final IInputManager inputManager, final IMousePositionPoll mousePositionPoll) {
		this.receiveActionQueue = new LinkedList<>();
		this.inputManager = inputManager;
		this.mousePositionPoll = mousePositionPoll;
		this.currentState = startState;

		inputManager.setActionQueue(receiveActionQueue);
		inputManager.setInputMap(currentState.getInputMap());
	}

	public void processActions() {
		UIAction current = receiveActionQueue.poll();
		while (current != null) {
			final UIState potentialDestination = currentState.getDestination(current.getAction());

			if (potentialDestination != null) {
				receiveActionQueue.clear();
				currentState = potentialDestination;
				inputManager.setInputMap(currentState.getInputMap());
				return;
			}

			// Look for ACTIVATE actions
			// If there is one consume it
			// - get the buttons from the current state
			// - check if we activated any
			// - if so call the activation method

			final IActionHandler handler = currentState.getActionHandler(current.getAction());
			if (handler != null)
				handler.handleAction(current.getMouseX(), current.getMouseY());
			current = receiveActionQueue.poll();
		}
	}

	public boolean transition(final String action) {
		final UIState potentialDestination = currentState.getDestination(action);

		if (potentialDestination != null) {
			receiveActionQueue.clear();
			currentState = potentialDestination;
			inputManager.setInputMap(currentState.getInputMap());
			return true;
		}

		return false;
	}

	public void setState(final UIState newState) {
		this.currentState = newState;
		inputManager.setInputMap(currentState.getInputMap());
	}

	public Point getMousePosition() {
		return mousePositionPoll.getMousePosition();
	}

	@Override
	public void draw(final IGraphics graphics) {
		final Iterator<UIElement> sorted = currentState.getElements();

		if (!sorted.hasNext())
			return;

		UIElement current = null;
		while (sorted.hasNext()) {
			current = sorted.next();
			current.draw(graphics);
		}
	}
}
