package raptor.engine.util.geometry;

import raptor.engine.util.geometry.api.ILineSegment;
import raptor.engine.util.geometry.api.IPoint;

public class OffsetPoint implements IPoint {
	private final IPoint originPoint;
	private int offsetX;
	private int offsetY;

	public OffsetPoint(final IPoint originPoint) {
		this.originPoint = originPoint;
		this.offsetX = 0;
		this.offsetY = 0;
	}

	public OffsetPoint(final IPoint originPoint, final int offsetX, final int offsetY) {
		this.originPoint = originPoint;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	@Override
	public int getX() {
		return originPoint.getX() + offsetX;
	}

	@Override
	public int getY() {
		return originPoint.getY() + offsetY;
	}

	@Override
	public boolean isOnLineSegment(final ILineSegment ls) {
		return Point.pointIsOnLineSegment(new Point(getX(), getY()), ls);
	}

	public void setOffsetX(final int offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(final int offsetY) {
		this.offsetY = offsetY;
	}

	public IPoint getOrigin() {
		return originPoint;
	}
}
