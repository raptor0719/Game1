package raptor.engine.ui.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputMap implements IInputMap {
	private final Map<PhysicalInputAction, List<String>> inputToActions;

	public InputMap() {
		this.inputToActions = new HashMap<>();
	}

	@Override
	public void mapInput(final PhysicalInput input, final KeyAction keyAction, final String action) {
		final PhysicalInputAction inputAction = new PhysicalInputAction(input, keyAction);

		if (!inputToActions.containsKey(inputAction))
			inputToActions.put(inputAction, new ArrayList<>());

		inputToActions.get(inputAction).remove(action);
		inputToActions.get(inputAction).add(action);
	}

	@Override
	public void unmapInput(final PhysicalInput input, final KeyAction keyAction, final String action) {
		final PhysicalInputAction inputAction = new PhysicalInputAction(input, keyAction);

		if (!inputToActions.containsKey(inputAction))
			return;

		inputToActions.get(inputAction).remove(action);
	}

	@Override
	public boolean inputIsMapped(final PhysicalInput input, final KeyAction keyAction) {
		final PhysicalInputAction inputAction = new PhysicalInputAction(input, keyAction);

		return inputToActions.containsKey(inputAction) && inputToActions.get(inputAction).size() > 0;
	}

	@Override
	public Collection<String> getMappedActions(final PhysicalInput input, final KeyAction keyAction) {
		final PhysicalInputAction inputAction = new PhysicalInputAction(input, keyAction);

		if (!inputToActions.containsKey(inputAction))
			return Collections.emptyList();

		return inputToActions.get(inputAction);
	}
}
