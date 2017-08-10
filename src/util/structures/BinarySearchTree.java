package util.structures;

import java.util.Comparator;

public class BinarySearchTree<T> {

	private Comparator<T> comparator;
	private Node<T> root;

	public BinarySearchTree(final Comparator<T> comparator) {
		this.comparator = comparator;
		this.root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Inserts this node into the BST using the given comparator. For any given
	 * sub-tree, all elements in the left sub-tree will be less then the root.
	 * All elements in the right sub-tree will be greater than or equal to the
	 * root.
	 */
	public void insert(final T data) {
		if (data == null)
			return;

		if (root == null)
			root = new Node<T>(data);

		insertRecursive(data, root);
	}

	private void insertRecursive(final T data, final Node<T> current) {
		if (comparator.compare(data, current.data) < 0) {
			if (current.leftChild != null)
				insertRecursive(data, current.leftChild);
			else
				current.leftChild = new Node<T>(data);
		} else {
			if (current.rightChild != null)
				insertRecursive(data, current.rightChild);
			else
				current.rightChild = new Node<T>(data);
		}
	}

	/**
	 * Descends the BST searching for the target using the given comparator.
	 * Returns null if the tree is empty or if the target is not found in
	 * the tree.
	 */
	public T search(final T target) {
		if (target == null)
			return null;

		if (root == null)
			return null;

		return searchRecursive(target, root);
	}

	private T searchRecursive(final T target, final Node<T> current) {
		if (current == null)
			return null;

		if (comparator.compare(target, current.data) == 0)
			return current.data;

		if (comparator.compare(target, current.data) < 0)
			return searchRecursive(target, current.leftChild);
		else
			return searchRecursive(target, current.rightChild);
	}
	
	// TODO: Need to add remove functionality

	private static class Node<T> {
		public Node<T> leftChild;
		public Node<T> rightChild;

		public T data;

		public Node(final T data) {
			this.data = data;
		}
	}
}
