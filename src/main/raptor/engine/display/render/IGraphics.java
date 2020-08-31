package raptor.engine.display.render;

import java.awt.Image;

public interface IGraphics {
	void drawImage(Image image, int x, int y);
	void drawOval(int x, int y, int width, int height, IColor color);
	void drawRectangle(int x, int y, int width, int height, IColor color);
}