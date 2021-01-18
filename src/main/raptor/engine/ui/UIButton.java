package raptor.engine.ui;

public abstract class UIButton extends UIElement {
	public UIButton(final int x, final int y, final UIAnchorPoint anchor, final int width, final int height, final int depth) {
		super(x, y, anchor, width, height, depth);
	}

	protected abstract void onActivate();
}
