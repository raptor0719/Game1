package raptor.engine.util.geometry.api;

import java.util.Collection;

import raptor.engine.util.geometry.LineSegment;

public interface IRectangle {
	Collection<LineSegment> getLineSegments();
	Collection<IPoint> getPoints();
	boolean containsPoint(IPoint point);
}
