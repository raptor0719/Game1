package raptor.engine.game.ui.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InputMap implements IInputSwitch, IInputPoll {
	private final Map<String, Float> inputMap;

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
}
