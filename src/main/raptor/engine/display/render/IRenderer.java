package raptor.engine.display.render;

import java.util.Iterator;

public interface IRenderer {
	void queueDrawable(final IDrawable drawable);
	void queueDrawables(final Iterator<IDrawable> drawables);
	void draw();
	IViewport getViewport();
}
