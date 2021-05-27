package raptor.engine.ui.input;

import java.util.Collection;

public interface IInputMap {
	void mapInput(PhysicalInput physicalInput, String logicalInput);
	void unmapInput(PhysicalInput physicalInput, String logicalInput);
	boolean physicalIsMapped(PhysicalInput physicalInput);
	boolean logicalIsMapped(String logicalInput);
	Collection<String> getMappedLogicalInputs(PhysicalInput physicalInput);
	Collection<PhysicalInput> getMappedPhysicalInputs(String logicalInput);
}
