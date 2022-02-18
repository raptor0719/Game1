package raptor.engine.ui;

public class UIAction {
	private int screenMouseX;
	private int screenMouseY;
	private int gameMouseX;
	private int gameMouseY;
	private String action;

	public UIAction(final int screenMouseX, final int screenMouseY, final int gameMouseX, final int gameMouseY, final String action) {
		this.screenMouseX = screenMouseX;
		this.screenMouseY = screenMouseY;
		this.gameMouseX = gameMouseX;
		this.gameMouseY = gameMouseY;
		this.action = action;
	}

	public int getScreenMouseX() {
		return screenMouseX;
	}

	public int getScreenMouseY() {
		return screenMouseY;
	}

	public int getGameMouseX() {
		return gameMouseX;
	}

	public int getGameMouseY() {
		return gameMouseY;
	}

	public String getAction() {
		return action;
	}
}
