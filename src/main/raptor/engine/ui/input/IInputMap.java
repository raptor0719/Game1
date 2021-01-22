package raptor.engine.ui.input;

import java.util.Collection;

public interface IInputMap {
	void mapInput(String physicalInput, String logicalInput);
	void unmapInput(String physicalInput, String logicalInput);
	boolean physicalIsMapped(String physicalInput);
	boolean logicalIsMapped(String logicalInput);
	Collection<String> getMappedLogicalInputs(String physicalInput);
	Collection<String> getMappedPhysicalInputs(String logicalInput);
}
