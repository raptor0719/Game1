package raptor.engine.display.render;

import java.util.Iterator;

public interface IRenderer {
	void draw(Iterator<IDrawable> drawables);
	int getRenderingSpaceWidth();
	int getRenderingSpaceHeight();
	void setViewportX(int x);
	void setViewportY(int y);
}
