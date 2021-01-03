package raptor.engine.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TreeNode<T> {
	private final List<TreeNode<T>> children;
	private T data;

	public TreeNode(final T data) {
		this.children = new ArrayList<>();
		this.data = data;
	}

	public TreeNode() {
		this(null);
	}

	public T getData() {
		return data;
	}

	public void setData(final T newData) {
		data = newData;
	}

	public Collection<TreeNode<T>> getChildren() {
		return children;
	}

	public void remove(final int index) {
		if (index < children.size() && index > -1)
			children.remove(index);
	}

	public void remove(final T data) {
		children.remove(data);
	}
}
