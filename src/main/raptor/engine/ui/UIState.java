package raptor.engine.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import raptor.engine.ui.elements.UIButton;
import raptor.engine.ui.elements.UIElement;
import raptor.engine.ui.input.IActionHandler;
import raptor.engine.ui.input.IInputMap;

public class UIState {
	private static final Comparator<UIElement> ELEMENT_COMPARATOR = new DepthSortComparator();

	private final List<UIElement> elements;
	private final Map<String, UIState> transitions;
	private final Map<String, IActionHandler> actionHandlers;

	private final List<UIButton> buttons;

	private IInputMap inputMap;

	public UIState() {
		this.elements = new ArrayList<>();
		this.transitions = new HashMap<>();
		this.actionHandlers = new HashMap<>();
		this.buttons = new ArrayList<>();
		this.inputMap = null;
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

	public Iterator<UIButton> getButtons() {
		return buttons.iterator();
	}

	public void clearElements() {
		elements.clear();
		buttons.clear();
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

	public void clearTransitions() {
		transitions.clear();
	}

	public void addActionHandler(final String action, final IActionHandler handler) {
		actionHandlers.put(action, handler);
	}

	public IActionHandler removeActionHandler(final String action) {
		return actionHandlers.remove(action);
	}

	public IActionHandler getActionHandler(final String action) {
		return actionHandlers.get(action);
	}

	public void clearActionHandlers() {
		actionHandlers.clear();
	}

	public void setInputMap(final IInputMap inputMap) {
		this.inputMap = inputMap;
	}

	public IInputMap getInputMap() {
		return inputMap;
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
