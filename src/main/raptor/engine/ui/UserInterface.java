package raptor.engine.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.IGraphics;
import raptor.engine.util.ListSortingIterator;

public class UserInterface implements IDrawable {
	private static final Comparator<UIElement> ELEMENT_COMPARATOR = new DepthSortComparator();

	private final int width;
	private final int height;

	private final List<UIElement> elements;

	public UserInterface(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.elements = new ArrayList<>();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Collection<UIElement> getElements() {
		return elements;
	}

	public void addElement(final UIElement newElement) {
		elements.add(newElement);
	}

	public void removeElement(final UIElement element) {
		elements.remove(element);
	}

	public void removeElement(final int index) {
		if (index < elements.size() && index > -1)
			elements.remove(index);
	}

	@Override
	public void draw(final IGraphics graphics) {
		final Iterator<UIElement> sorted = new ListSortingIterator<>(elements, ELEMENT_COMPARATOR);

		if (!sorted.hasNext())
			return;

		UIElement current = sorted.next();
		while (sorted.hasNext())
			current.draw(graphics);
	}

	private static class DepthSortComparator implements Comparator<UIElement> {
		@Override
		public int compare(final UIElement o1, final UIElement o2) {
			if (o1.getDepth() < o2.getDepth())
				return -1;
			else if (o1.getDepth() > o2.getDepth())
				return 1;
			return 0;
		}
	}
}
