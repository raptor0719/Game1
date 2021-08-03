package raptor.engine.ui;

import java.util.Iterator;
import java.util.Queue;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.ui.elements.UIElement;
import raptor.engine.ui.input.IInputHandler;
import raptor.engine.util.IdProvider;

public class UserInterface implements IDrawable {
	public static final IdProvider UI_ID_PROVIDER = new IdProvider();

	private final Queue<UIAction> receiveActionQueue;

	private UIState currentState;

	public UserInterface(final UIState startState, final Queue<UIAction> receiveActionQueue) {
		this.receiveActionQueue = receiveActionQueue;
		this.currentState = startState;
	}

	public void processInputs() {
		UIAction current = receiveActionQueue.poll();
		while (current != null) {
			final UIState potentialDestination = currentState.getDestination(current.getAction());

			if (potentialDestination != null) {
				receiveActionQueue.clear();
				currentState = potentialDestination;
				return;
			}

			final IInputHandler handler = currentState.getInputHandler(current.getAction());
			if (handler != null)
				handler.handleInput(current.getMouseX(), current.getMouseY());
			current = receiveActionQueue.poll();
		}
	}

	public void setState(final UIState newState) {
		this.currentState = newState;
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
