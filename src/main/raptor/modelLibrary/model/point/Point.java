package raptor.modelLibrary.model.point;

public class Point implements IPointReader, IPointWriter {
	private int x;
	private int y;

	public Point(final int initX, final int initY) {
		this.x = initX;
		this.y = initY;
	}

	@Override
	public void setX(final int newX) {
		this.x = newX;
	}

	@Override
	public void setY(final int newY) {
		this.y = newY;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

}
