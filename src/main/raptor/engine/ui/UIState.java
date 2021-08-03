package raptor.engine.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import raptor.engine.ui.elements.UIButton;
import raptor.engine.ui.elements.UIElement;
import raptor.engine.ui.input.IInputHandler;

public class UIState {
	private static final Comparator<UIElement> ELEMENT_COMPARATOR = new DepthSortComparator();

	private final List<UIElement> elements;
	private final Map<String, UIState> transitions;
	private final Map<String, IInputHandler> handlers;

	private final List<UIButton> buttons;

	public UIState() {
		this.elements = new ArrayList<>();
		this.transitions = new HashMap<>();
		this.handlers = new HashMap<>();
		this.buttons = new ArrayList<>();
	}

	public void addElement(final UIElement newElement) {
		elements.remove(newElement);

		elements.add(newElement);
		elements.sort(ELEMENT_COMPARATOR);

		if (newElement instanceof UIButton) {
			final UIButton button = (UIButton)newElement;
			buttons.remove(button);
			buttons.add(button);
		}
	}

	public UIElement removeElement(final long id) {
		for (int i = 0; i < elements.size(); i++) {
			final UIElement element = elements.get(i);
			if (element.getId() == id) {
				elements.remove(element);

				if (element instanceof UIButton) {
					final UIButton button = (UIButton)element;
					buttons.remove(button);
				}

				return element;
			}
		}

		return null;
	}

	public Iterator<UIElement> getElements() {
		return elements.iterator();
	}

	public void addTransition(final String action, final UIState destination) {
		transitions.put(action, destination);
	}

	public UIState removeTransition(final String action) {
		return transitions.remove(action);
	}

	public UIState getDestination(final String action) {
		return transitions.get(action);
	}

	public void addInputHandler(final String action, final IInputHandler handler) {
		handlers.put(action, handler);
	}

	public IInputHandler removeInputHandler(final String action) {
		return handlers.remove(action);
	}

	public IInputHandler getInputHandler(final String action) {
		return handlers.get(action);
	}

	private static class DepthSortComparator implements Comparator<UIElement> {
		@Override
		public int compare(final UIElement o1, final UIElement o2) {
			if (o1.getDepth() < o2.getDepth())
				return -1;
			else if (o1.getDepth() > o2.getDepth())
				return 1;
			return 0;
		}
	}
}
