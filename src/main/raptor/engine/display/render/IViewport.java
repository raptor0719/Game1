package raptor.engine.display.render;

public interface IViewport {
	int getWidth();
	int getHeight();
	int getXPosition();
	int getYPosition();
	void setXPosition(int newXPosition);
	void setYPosition(int newYPosition);
}
