package raptor.engine.util.geometry;

import raptor.engine.util.geometry.api.ILineSegment;
import raptor.engine.util.geometry.api.IPoint;

public class OffsetPoint implements IPoint {
	private final IPoint originPoint;
	private final int offsetX;
	private final int offsetY;

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
}
