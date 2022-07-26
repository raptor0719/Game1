package raptor.engine.display.render;

public class Viewport implements IViewport {
	private final int width;
	private final int height;

	private int positionX;
	private int positionY;

	private float velocityX;
	private float velocityY;
	private float xAccumulator;
	private float yAccumulator;

	public Viewport(final int width, final int height, final int positionX, final int positionY) {
		this.width = width;
		this.height = height;

		this.positionX = positionX;
		this.positionY = positionY;

		this.velocityX = 0.0F;
		this.velocityY = 0.0F;
		this.xAccumulator = 0.0F;
		this.yAccumulator = 0.0F;
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

	@Override
	public float getXVelocity() {
		return velocityX;
	}

	@Override
	public float getYVelocity() {
		return velocityY;
	}

	@Override
	public void setXVelocity(final float xVelocity) {
		this.velocityX = xVelocity;
	}

	@Override
	public void setYVelocity(final float yVelocity) {
		this.velocityY = yVelocity;
	}

	@Override
	public void update() {
		final float totalXUnits = velocityX + xAccumulator;
		final int xUnitsToMove = (int)totalXUnits;
		xAccumulator = totalXUnits - xUnitsToMove;

		setXPosition(getXPosition() + xUnitsToMove);

		final float totalYUnits = velocityY + yAccumulator;
		final int yUnitsToMove = (int)totalYUnits;
		yAccumulator = totalYUnits - yUnitsToMove;

		setYPosition(getYPosition() + yUnitsToMove);
	}
}
