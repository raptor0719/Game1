package raptor.modelLibrary.model;

import raptor.modelLibrary.model.point.IPointReader;
import raptor.modelLibrary.model.point.Point;

public class Hardpoint implements IHardpointPosition {
	private int x;
	private int y;
	private int rotation;

	private final IPointReader referencePoint;

	public Hardpoint(final int initX, final int initY, final int initRotation) {
		x = initX;
		y = initY;
		rotation = initRotation;
		this.referencePoint = new Point(0, 0);
	}

	public Hardpoint(final int initX, final int initY, final int initRotation, final IPointReader referencePoint) {
		x = initX;
		y = initY;
		rotation = initRotation;
		this.referencePoint = (referencePoint != null) ? referencePoint : new Point(0, 0);
	}

	@Override
	public int getX() {
		return referencePoint.getX() + x;
	}

	@Override
	public int getY() {
		return referencePoint.getY() + y;
	}

	@Override
	public int getRotation() {
		return rotation;
	}

	public void setX(final int newX) {
		x = newX;
	}

	public void setY(final int newY) {
		y = newY;
	}

	public void setRotation(final int newRotation) {
		rotation = newRotation;
	}
}
