package raptor.engine.ui;

import java.util.Collection;

import raptor.engine.display.render.IDrawable;

public interface UIElement extends IDrawable {
	int getId();
	int getX();
	int getY();
	int getDepth();
	UIAnchorPoint getAnchor();
	int getWidth();
	int getHeight();
	void onActivate();
	void addChild(UIElement newChild);
	void removeChild(UIElement childToRemove);
	Collection<UIElement> getChildren();
}
