package raptor.engine.ui.input;

import java.util.Collection;

public interface IInputMap {
	void mapInput(PhysicalInput input, KeyAction keyAction, String action);
	void unmapInput(PhysicalInput input, KeyAction keyAction, String action);
	boolean inputIsMapped(PhysicalInput input, KeyAction keyAction);
	Collection<String> getMappedActions(PhysicalInput input, KeyAction keyAction);
}
