package raptor.modelLibrary.model;

import raptor.engine.display.api.IDrawable;
import raptor.engine.util.geometry.Point;
import raptor.modelLibrary.model.point.IPointReader;

public class Sprite implements IDrawable {
	private IPointReader referencePoint;
	private Point dimensions;

	public Sprite(final int x, final int y) {
		dimensions = new Point(x, y);
	};

	public void setPosition(final IPointReader ref) {
		referencePoint = ref;
	}

	@Override
	public IPointReader getPosition() {
		return referencePoint;
	}

	@Override
	public IPointReader getDimensions() {
		return dimensions;
	}
}
