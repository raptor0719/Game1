package raptor.engine.display.render;

public class LocationToViewportTransformer {
	private final IViewport vp;

	public LocationToViewportTransformer(final IViewport vp) {
		this.vp = vp;
	}

	public Integer transformX(final Integer in) {
		return in - vp.getXPosition();
	}

	public Integer transformY(final Integer in) {
		return in - vp.getYPosition();
	}
}
