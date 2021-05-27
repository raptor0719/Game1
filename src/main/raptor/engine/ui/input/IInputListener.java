package raptor.engine.ui.input;

public interface IInputListener {
	void assignPressedHandler(String logicalInput, IInputHandler handler);
	void assignReleasedHandler(String logicalInput, IInputHandler handler);
	void unassignPressedHandler(String logicalInput);
	void unassignReleasedHandler(String logicalInput);
}
