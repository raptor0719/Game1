package raptor.engine.util.geometry.shape.api;

import java.util.List;

import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.Point;

public interface IShape {
	public double getArea();
	public List<Point> getPoints();
	public List<LineSegment> getLines();
	public boolean intersects(final IShape shape);
}
