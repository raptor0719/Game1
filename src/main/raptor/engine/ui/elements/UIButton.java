package raptor.engine.ui.elements;

import raptor.engine.ui.UIAnchorPoint;

public abstract class UIButton extends UIElement {
	public UIButton(final int x, final int y, final UIAnchorPoint anchor, final int width, final int height, final int depth) {
		super(x, y, anchor, width, height, depth);
	}

	public abstract void activate();
}
