package raptor.engine.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ListSortingIterator<T> implements Iterator<T> {
	private final Iterator<T> sorted;

	public ListSortingIterator(final List<T> list, final Comparator<T> comparator) {
		final List<T> copy = new ArrayList<>(list);
		copy.sort(comparator);
		sorted = copy.iterator();
	}

	@Override
	public boolean hasNext() {
		return sorted.hasNext();
	}

	@Override
	public T next() {
		return sorted.next();
	}

}
