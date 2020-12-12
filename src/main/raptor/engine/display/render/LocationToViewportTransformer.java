package raptor.engine.display.render;

public class LocationToViewportTransformer {
	private final Viewport vp;
	private final int scale;

	public LocationToViewportTransformer(final Viewport vp, final int scale) {
		this.vp = vp;
		this.scale = scale;
	}

	public Integer transformX(final Integer in) {
		return (in / scale) - vp.getXpos();
	}

	public Integer transformY(final Integer in) {
		return (in / scale) - vp.getYpos();
	}
}
