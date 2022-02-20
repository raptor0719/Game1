package raptor.engine.ui.elements;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.display.render.IDrawable;
import raptor.engine.ui.UIAnchorPoint;
import raptor.engine.ui.UserInterface;

public abstract class UIElement implements IDrawable {
	private final long id;
	private final List<UIElement> children;
	private UIElement parent;

	private int relativeX;
	private int relativeY;
	private UIAnchorPoint anchor;
	private int width;
	private int height;
	private int depth;

	private int absoluteX;
	private int absoluteY;

	public UIElement(final int x, final int y, final UIAnchorPoint anchor, final int width, final int height, final int depth) {
		this.id = UserInterface.UI_ID_PROVIDER.get();
		this.children = new ArrayList<>();
		this.parent = null;

		this.relativeX = x;
		this.relativeY = y;
		this.anchor = anchor;
		this.width = width;
		this.height = height;
		this.depth = depth;

		updateAbsolutePosition();
	}

	public long getId() {
		return id;
	}

	public UIElement getParent() {
		return parent;
	}

	public List<UIElement> getChildren() {
		return children;
	}

	public void addChild(final UIElement uiElement) {
		if (children.contains(uiElement))
			return;
		children.add(uiElement);
		uiElement.setParent(this);
	}

	protected void setParent(final UIElement newParent) {
		if (parent != null)
			parent.children.remove(this);

		this.parent = newParent;
		updateAbsolutePosition();
	}

	public int getAbsoluteX() {
		return absoluteX;
	}

	public int getRelativeX() {
		return relativeX;
	}

	public void setX(final int newX) {
		this.relativeX = newX;
		updateAbsolutePosition();
	}

	public int getAbsoluteY() {
		return absoluteY;
	}

	public int getRelativeY() {
		return relativeY;
	}

	public void setY(final int newY) {
		this.relativeY = newY;
		updateAbsolutePosition();
	}

	public UIAnchorPoint getAnchor() {
		return anchor;
	}

	public void setAnchor(final UIAnchorPoint newAnchor) {
		this.anchor = newAnchor;
		updateAbsolutePosition();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int newWidth) {
		this.width = newWidth;
		updateAbsolutePosition();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int newHeight) {
		this.height = newHeight;
		updateAbsolutePosition();
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(final int newDepth) {
		this.depth = newDepth;
	}

	public boolean positionIsInElement(final int posX, final int posY) {
		return posX >= absoluteX && posX <= absoluteX + width && posY >= absoluteY && posY <= absoluteY + height;
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof UIElement))
			return false;

		final UIElement casted = (UIElement)o;

		return casted.getId() == this.getId();
	}

	protected void updateAbsolutePosition() {
		this.absoluteX = calculateAbsoluteX();
		this.absoluteY = calculateAbsoluteY();

		for (final UIElement child : children)
			child.updateAbsolutePosition();
	}

	private int calculateAbsoluteX() {
		final int xToUse = (parent == null) ? relativeX : parent.getAbsoluteX() + relativeX;
		return anchor.translateX(xToUse, width);
	}

	public int calculateAbsoluteY() {
		final int yToUse = (parent == null) ? relativeY : parent.getAbsoluteY() + relativeY;
		return anchor.translateY(yToUse, height);
	}
}
