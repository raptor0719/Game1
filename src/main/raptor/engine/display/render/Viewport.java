package raptor.engine.display.render;

public class Viewport implements IViewport {
	private final int width;
	private final int height;
	private int positionX;
	private int positionY;

	public Viewport(final int width, final int height, final int positionX, final int positionY) {
		this.width = width;
		this.height = height;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getXPosition() {
		return positionX;
	}

	@Override
	public int getYPosition() {
		return positionY;
	}

	@Override
	public void setXPosition(final int newXPosition) {
		this.positionX = newXPosition;
	}

	@Override
	public void setYPosition(final int newYPosition) {
		this.positionY = newYPosition;
	}
}
