package raptor.engine.display.render;

public class ViewportToLocationTransformer {
	private final IViewport viewport;

	public ViewportToLocationTransformer(final IViewport viewport) {
		this.viewport = viewport;
	}

	public Integer transformX(final Integer x) {
		return x + viewport.getXPosition();
	}

	public Integer transformY(final Integer y) {
		return y + viewport.getYPosition();
	}
}
