package raptor.engine.display.render;

import java.awt.Image;

import raptor.engine.model.Sprite;

public interface IGraphics {
	void drawSprite(Sprite sprite, int x, int y, int rotation);
	void drawImage(Image image, int x, int y);
	void drawOval(int x, int y, int width, int height, final boolean fill, IColor color);
	void drawRectangle(int x, int y, int width, int height, final boolean fill, IColor color);
	void drawLine(int startX, int startY, int endX, int endY, int thickness, IColor color);
	IGraphics getViewportRenderer();
}
