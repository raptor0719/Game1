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

	private int x;
	private int y;
	private UIAnchorPoint anchor;
	private int width;
	private int height;
	private int depth;

	public UIElement(final int x, final int y, final UIAnchorPoint anchor, final int width, final int height, final int depth) {
		this.id = UserInterface.UI_ID_PROVIDER.get();
		this.children = new ArrayList<>();

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

	public void addChild(final UIElement newChild) {
		children.add(newChild);
	}

	public UIElement removeChild(final int id) {
		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getId() == id)
				return children.remove(i);
		return null;
	}

	public int getX() {
		return x;
	}

	public void setX(final int newX) {
		this.x = newX;
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

	public void setWdith(final int newWidth) {
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
}
