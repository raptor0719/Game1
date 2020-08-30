package raptor.engine.display.render;

public class BasicColor implements IColor {
	private final int red;
	private final int green;
	private final int blue;
	private final int alpha;

	public BasicColor(final int red, final int green, final int blue, final int alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	@Override
	public int getRed() {
		return red;
	}

	@Override
	public int getGreen() {
		return green;
	}

	@Override
	public int getBlue() {
		return blue;
	}

	@Override
	public int getAlpha() {
		return alpha;
	}

}
