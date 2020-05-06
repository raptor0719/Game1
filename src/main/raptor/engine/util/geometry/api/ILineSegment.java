package raptor.engine.util.geometry.api;

public interface ILineSegment {
	IPoint getStart();
	IPoint getEnd();
	float getLength();
}
