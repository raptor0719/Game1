package raptor.engine.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.ListSortingIterator;

public abstract class UIElement implements IDrawable {
	private final long id;
	private final List<UIElement> children;
	private UIElement parent;

	private int x;
	private int y;
	private UIAnchorPoint anchor;
	private int width;
	private int height;
	private int depth;
	private boolean useAbsolutePositioning;

	public UIElement(final int x, final int y, final UIAnchorPoint anchor, final int width, final int height, final int depth) {
		this.id = UserInterface.UI_ID_PROVIDER.get();
		this.children = new ArrayList<>();
		this.parent = null;

		this.x = x;
		this.y = y;
		this.anchor = anchor;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.useAbsolutePositioning = false;
	}

	public long getId() {
		return id;
	}

	public void addChild(final UIElement newChild) {
		children.add(newChild);
		newChild.setParent(this);
	}

	public UIElement removeChild(final int id) {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getId() == id) {
				final UIElement removedChild = children.remove(i);
				removedChild.setParent(null);
				return removedChild;
			}
		}
		return null;
	}

	public UIElement getParent() {
		return parent;
	}

	protected void setParent(final UIElement newParent) {
		this.parent = newParent;
	}

	public int getAbsoluteX() {
		final int xToUse = (useAbsolutePositioning) ? x : parent.getAbsoluteX() + x;
		return anchor.translateX(xToUse, width);
	}

	public int getX() {
		return x;
	}

	public void setX(final int newX) {
		this.x = newX;
	}

	public int getAbsoluteY() {
		final int yToUse = (useAbsolutePositioning) ? y : parent.getAbsoluteY() + y;
		return anchor.translateY(yToUse, height);
	}

	public int getY() {
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

	public boolean isUseAbsolutePositioning() {
		return useAbsolutePositioning;
	}

	public void useAbsolutePositioning(final boolean set) {
		this.useAbsolutePositioning = set;
	}

	@Override
	public void draw(final IGraphics graphics) {
		drawThis(graphics);

		final Iterator<UIElement> sorted = new ListSortingIterator<>(children, UserInterface.ELEMENT_COMPARATOR);

		if (!sorted.hasNext())
			return;

		UIElement current = null;
		while (sorted.hasNext()) {
			current = sorted.next();
			current.draw(graphics);
		}
	}

	protected abstract void drawThis(IGraphics graphics);

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof UIElement))
			return false;

		final UIElement casted = (UIElement)o;

		return casted.getId() == this.getId();
	}
}
