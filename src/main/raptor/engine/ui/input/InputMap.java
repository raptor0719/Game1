package raptor.engine.ui.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InputMap implements IInputSwitch, IInputPoll, IMousePositionSwitch, IMousePositionPoll {
	private final Map<String, Float> inputMap;
	private int mouseX;
	private int mouseY;

	public InputMap(final Collection<String> inputs) {
		inputMap = new HashMap<String, Float>();
		for (final String s : inputs)
			inputMap.put(s, 0.0F);
	}

	@Override
	public float getInputValue(final String input) {
		return inputMap.get(input);
	}

	@Override
	public void setInput(final String name, final float value) {
		inputMap.put(name, value);
	}

	@Override
	public int getMousePositionX() {
		return mouseX;
	}

	@Override
	public int getMousePositionY() {
		return mouseY;
	}

	@Override
	public void setMouseX(final int x) {
		mouseX = x;
	}

	@Override
	public void setMouseY(final int y) {
		mouseY = y;
	}
}
