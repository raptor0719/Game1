package raptor.engine.display.render;

public class LocationToViewportTransformer {
	private final IViewport vp;
	private final int scale;

	public LocationToViewportTransformer(final IViewport vp, final int scale) {
		this.vp = vp;
		this.scale = scale;
	}

	public Integer transformX(final Integer in) {
		return (in / scale) - vp.getXPosition();
	}

	public Integer transformY(final Integer in) {
		return (in / scale) - vp.getYPosition();
	}
}
