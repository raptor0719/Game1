package raptor.engine.ui.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputMap implements IInputMap {
	private final Map<PhysicalInput, List<String>> physicalToLogical;
	private final Map<String, List<PhysicalInput>> logicalToPhysical;

	public InputMap() {
		physicalToLogical = new HashMap<>();
		logicalToPhysical = new HashMap<>();
	}

	@Override
	public void mapInput(final PhysicalInput physicalInput, final String logicalInput) {
		if (!physicalToLogical.containsKey(physicalInput))
			physicalToLogical.put(physicalInput, new ArrayList<>());

		if (!logicalToPhysical.containsKey(logicalInput))
			logicalToPhysical.put(logicalInput, new ArrayList<>());

		if (!physicalToLogical.get(physicalInput).contains(logicalInput))
			physicalToLogical.get(physicalInput).add(logicalInput);

		if (!logicalToPhysical.get(logicalInput).contains(physicalInput))
			logicalToPhysical.get(logicalInput).add(physicalInput);
	}

	@Override
	public void unmapInput(final PhysicalInput physicalInput, final String logicalInput) {
		if (physicalToLogical.containsKey(physicalInput))
			if (physicalToLogical.get(physicalInput).contains(logicalInput))
				physicalToLogical.get(physicalInput).remove(logicalInput);

		if (logicalToPhysical.containsKey(logicalInput))
			if (logicalToPhysical.get(logicalInput).contains(physicalInput))
				logicalToPhysical.get(logicalInput).remove(physicalInput);
	}

	@Override
	public boolean physicalIsMapped(final PhysicalInput physicalInput) {
		return physicalToLogical.containsKey(physicalInput) && !physicalToLogical.get(physicalInput).isEmpty();
	}

	@Override
	public boolean logicalIsMapped(final String logicalInput) {
		return logicalToPhysical.containsKey(logicalInput) && !logicalToPhysical.get(logicalInput).isEmpty();
	}

	@Override
	public Collection<String> getMappedLogicalInputs(final PhysicalInput physicalInput) {
		if (!physicalToLogical.containsKey(physicalInput))
			return Collections.emptyList();

		return physicalToLogical.get(physicalInput);
	}

	@Override
	public Collection<PhysicalInput> getMappedPhysicalInputs(final String logicalInput) {
		if (!logicalToPhysical.containsKey(logicalInput))
			return Collections.emptyList();

		return logicalToPhysical.get(logicalInput);
	}
}
