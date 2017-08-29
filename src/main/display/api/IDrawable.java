package display.api;

/**
 * Any object that specifies how to draw itself, using the given IDrawer.
 */
public interface IDrawable {
	public void draw(IDrawer drawer);
}
