package raptor.engine.util.geometry.api;

public interface IPoint {
	int getX();
	int getY();
	boolean isOnLineSegment(final ILineSegment ls);
}
