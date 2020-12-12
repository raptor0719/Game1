package raptor.engine.display.render;

public class Viewport {
	private final int width;
	private final int height;
	private int posX;
	private int posY;

	public Viewport(final int width, final int height, final int posX, final int posY) {
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getXpos() {
		return posX;
	}

	public int getYpos() {
		return posY;
	}

	public void setXpos(final int newX) {
		posX = newX;
	}

	public void setYpos(final int newY) {
		posY = newY;
	}
}
