package raptor.engine.ui;

public class UIAction {
	private int mouseX;
	private int mouseY;
	private String action;

	public UIAction(final int mouseX, final int mouseY, final String action) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.action = action;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public String getAction() {
		return action;
	}
}
