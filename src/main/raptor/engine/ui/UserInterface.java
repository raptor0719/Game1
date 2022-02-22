package raptor.engine.ui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.ui.elements.UIButton;
import raptor.engine.ui.elements.UIElement;
import raptor.engine.ui.input.IActionHandler;
import raptor.engine.ui.input.IInputManager;
import raptor.engine.ui.input.IMousePositionPoll;
import raptor.engine.util.IdProvider;
import raptor.engine.util.geometry.Point;

public class UserInterface implements IDrawable {
	public static final String ACTIVATE_BUTTON_ACTION = "ACTIVATE";

	public static final IdProvider UI_ID_PROVIDER = new IdProvider();

	public static final UIState EMPTY_STATE = new UIState();

	private final Queue<UIAction> receiveActionQueue;
	private final IInputManager inputManager;
	private final IMousePositionPoll mousePositionPoll;

	private UIState currentState;

	public UserInterface(final IInputManager inputManager, final IMousePositionPoll mousePositionPoll) {
		this.receiveActionQueue = new LinkedList<>();
		this.inputManager = inputManager;
		this.mousePositionPoll = mousePositionPoll;
		this.currentState = EMPTY_STATE;

		inputManager.setActionQueue(receiveActionQueue);
		inputManager.setInputMap(currentState.getInputMap());
	}

	public void processActions() {
		UIAction currentAction = receiveActionQueue.poll();
		while (currentAction != null) {
			final UIState potentialDestination = currentState.getDestination(currentAction.getAction());

			if (potentialDestination != null) {
				receiveActionQueue.clear();
				currentState = potentialDestination;
				inputManager.setInputMap(currentState.getInputMap());
				return;
			}

			boolean buttonWasActivated = false;
			if (ACTIVATE_BUTTON_ACTION.equals(currentAction.getAction())) {
				final UIButton activatedButton = currentState.getActivatedButton(currentAction.getScreenMouseX(), currentAction.getScreenMouseY());

				if (activatedButton != null) {
					activatedButton.activate();
					buttonWasActivated = true;
				}
			}

			if (!buttonWasActivated) {
				final IActionHandler handler = currentState.getActionHandler(currentAction.getAction());
				if (handler != null)
					handler.handleAction(currentAction.getGameMouseX(), currentAction.getGameMouseY());
			}

			currentAction = receiveActionQueue.poll();
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
		this.currentState = (newState == null) ? EMPTY_STATE : newState;
		inputManager.setInputMap(currentState.getInputMap());
	}

	public Point getMousePosition() {
		return mousePositionPoll.getMousePosition();
	}

	@Override
	public void draw(final IGraphics translatedGraphics) {
		final IGraphics graphics = translatedGraphics.getViewportRenderer();

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
