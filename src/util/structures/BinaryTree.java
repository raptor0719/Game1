package util.structures;

public class BinaryTree<T> {
	private Node<T> root;
	private Node<T> current;

	// Create/Destroy Methods

	/**
	 * Sets the data at the current active node's
	 * left child to the given object.
	 * @param data the data to set with
	 */
	public void setLeft(final T data) {
		if (current == null || data == null)
			return;

		if (current.leftChild == null) {
			final Node<T> newNode = new Node<T>(data);
			newNode.parent = current;
			newNode.isLeft = true;
			current.leftChild = new Node<T>(data);
			return;
		}

		current.leftChild.data = data;
	}

	/**
	 * Sets the data at the current active node's
	 * right child to the given object.
	 * @param data the data to set with
	 */
	public void setRight(final T data) {
		if (current == null || data == null)
			return;

		if (current.rightChild == null) {
			final Node<T> newNode = new Node<T>(data);
			newNode.parent = current;
			newNode.isLeft = false;
			current.rightChild = new Node<T>(data);
			return;
		}

		current.rightChild.data = data;
	}

	/**
	 * Sets the data at the current active node to the
	 * given object.
	 * @param data the data to set with
	 */
	public void setCurrent(final T data) {
		if (current == null) {
			final Node<T> newNode = new Node<T>(data);
			current = newNode;
			root = current;
			return;
		}

		current.data = data;
	}

	/**
	 * Deletes the current active node and set's the
	 * current active node to it's own parent. Does
	 * nothing if the tree is empty.
	 */
	public void deleteCurrent() {
		if (current == null)
			return;

		if (current.parent == null) {
			root = null;
			current = null;
		}

		boolean isLeft = current.isLeft;
		current = current.parent;

		if (isLeft)
			current.leftChild = null;
		else
			current.rightChild = null;
	}

	// Navigation Methods

	/**
	 * Returns what is at the current active node. Returns null if the
	 * tree is empty.
	 * @return Data at the current active node
	 */
	public T getCurrent() {
		if (current == null)
			return null;

		return current.data;
	}

	/**
	 * Resets the current active node to the root of the tree. Does
	 * nothing if the tree has no elements.
	 */
	public void resetToRoot() {
		current = root;
	}

	/**
	 * Moves the current active node to it's own left child. Does
	 * nothing if the left child is null.
	 */
	public void moveToLeft() {
		if (current == null || current.leftChild == null)
			return;

		current = current.leftChild;
	}

	/**
	 * Moves the current active node to it's own right child. Does
	 * nothing if the right child is null.
	 */
	public void moveToRight() {
		if (current == null || current.rightChild == null)
			return;

		current = current.rightChild;
	}

	/**
	 * Moves the current active node to it's own parent. Nothing
	 * will happen if the parent is null.
	 */
	public void moveToParent() {
		if (current == null || current.parent == null)
			return;

		current = current.parent;
	}

	// Internal
	private static class Node<NT> {
		private NT data;
		private Node<NT> leftChild;
		private Node<NT> rightChild;
		private Node<NT> parent;
		private boolean isLeft;

		private Node(final NT data) {
			this.data = data;
		}
	}
}
