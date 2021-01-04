package raptor.engine.ui;

import raptor.engine.display.render.IColor;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.valueProviders.IntegerValueProvider;

public class UIFillBarElement extends UIElement {
	private final IColor barColor;

	private boolean useProvider = false;
	private IntegerValueProvider<?> valueProvider;
	private int value;

	public UIFillBarElement(final IColor color, final IntegerValueProvider<?> valueProvider, int x, int y, UIAnchorPoint anchor, int width, int height, int depth) {
		super(x, y, anchor, width, height, depth);
		this.barColor = color;
		this.useProvider = valueProvider != null;
		this.valueProvider = valueProvider;
		this.value = 0;
	}

	public UIFillBarElement(final IColor color, int x, int y, UIAnchorPoint anchor, int width, int height, int depth) {
		this(color, null, x, y, anchor, width, height, depth);
	}

	@Override
	protected void drawThis(final IGraphics graphics) {
		// TODO implement this
	}

	public IColor getBarColor() {
		return barColor;
	}

	public void setValueProvider(final IntegerValueProvider<?> newProvider) {
		this.valueProvider = newProvider;
	}

	public void useProvider(final boolean useProvider) {
		this.useProvider = useProvider;
	}

	public boolean isUseProvider() {
		return useProvider;
	}

	public void setValue(final int newValue) {
		this.value = newValue;
	}

	public int getValue() {
		if (useProvider)
			return valueProvider.getValue();
		return value;
	}
}
