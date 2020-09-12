package raptor.engine.display.render;

import java.util.Iterator;

public interface IRenderer {
	void draw(Iterator<IDrawable> drawables);
}
