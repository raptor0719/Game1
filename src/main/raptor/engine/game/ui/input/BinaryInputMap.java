package raptor.engine.game.ui.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BinaryInputMap<K> {

	private final Map<K, Boolean> inputMap;

	public BinaryInputMap(final Collection<K> inputs) {
		inputMap = new HashMap<K, Boolean>(inputs.size());

		for (final K k : inputs)
			inputMap.put(k, false);
	}

	public void setKeyState(final K input, final boolean state) {
		inputMap.put(input, state);
	}

	public boolean getKeyState(final K input) {
		return (inputMap.containsKey(input)) ? inputMap.get(input) : false;
	}
}
