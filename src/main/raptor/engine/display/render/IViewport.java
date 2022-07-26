package raptor.engine.display.render;

public interface IViewport {
	int getWidth();
	int getHeight();
	int getXPosition();
	int getYPosition();
	float getXVelocity();
	float getYVelocity();
	void setXPosition(int newXPosition);
	void setYPosition(int newYPosition);
	void setXVelocity(float xVelocity);
	void setYVelocity(float yVelocity);
	void update();
}
