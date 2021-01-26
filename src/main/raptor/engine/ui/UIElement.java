package raptor.engine.ui;

import raptor.engine.display.render.IDrawable;

public abstract class UIElement implements IDrawable {
	private final long id;
	private UIElement parent;

	private int x;
	private int y;
	private UIAnchorPoint anchor;
	private int width;
	private int height;
	private int depth;

	public UIElement(final int x, final int y, final UIAnchorPoint anchor, final int width, final int height, final int depth) {
		this.id = UserInterface.UI_ID_PROVIDER.get();
		this.parent = null;

		this.x = x;
		this.y = y;
		this.anchor = anchor;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public long getId() {
		return id;
	}

	public UIElement getParent() {
		return parent;
	}

	protected void setParent(final UIElement newParent) {
		this.parent = newParent;
	}

	public int getAbsoluteX() {
		final int xToUse = (parent == null) ? x : parent.getAbsoluteX() + x;
		return anchor.translateX(xToUse, width);
	}

	public int getRelativeX() {
		return x;
	}

	public void setX(final int newX) {
		this.x = newX;
	}

	public int getAbsoluteY() {
		final int yToUse = (parent == null) ? y : parent.getAbsoluteY() + y;
		return anchor.translateY(yToUse, height);
	}

	public int getRelativeY() {
		return y;
	}

	public void setY(final int newY) {
		this.y = newY;
	}

	public UIAnchorPoint getAnchor() {
		return anchor;
	}

	public void setAnchor(final UIAnchorPoint newAnchor) {
		this.anchor = newAnchor;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int newWidth) {
		this.width = newWidth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int newHeight) {
		this.height = newHeight;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(final int newDepth) {
		this.depth = newDepth;
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof UIElement))
			return false;

		final UIElement casted = (UIElement)o;

		return casted.getId() == this.getId();
	}
}
