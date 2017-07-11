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

	public boolean

	private static class Node<T> {
		public Node<T> leftChild;
		public Node<T> rightChild;

		public T data;

		public Node(final T data) {
			this.data = data;
		}
	}
}
