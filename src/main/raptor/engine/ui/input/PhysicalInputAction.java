package raptor.engine.ui.input;

public class PhysicalInputAction {
	private final PhysicalInput input;
	private final KeyAction keyAction;

	public PhysicalInputAction(final PhysicalInput input, final KeyAction keyAction) {
		this.input = input;
		this.keyAction = keyAction;
	}

	public PhysicalInput getInput() {
		return input;
	}

	public KeyAction getKeyAction() {
		return keyAction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((input == null) ? 0 : input.hashCode());
		result = prime * result + ((keyAction == null) ? 0 : keyAction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhysicalInputAction other = (PhysicalInputAction) obj;
		if (input != other.input)
			return false;
		if (keyAction != other.keyAction)
			return false;
		return true;
	}
}
