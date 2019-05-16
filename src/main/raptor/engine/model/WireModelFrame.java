package raptor.engine.model;

public class WireModelFrame {
	private HardpointPosition[] hardpointPositions;

	public WireModelFrame(final HardpointPosition[] hardpointPositions) {
		this.hardpointPositions = hardpointPositions;
	}

	public HardpointPosition[] getHardpointPositions() {
		return hardpointPositions;
	}
}
