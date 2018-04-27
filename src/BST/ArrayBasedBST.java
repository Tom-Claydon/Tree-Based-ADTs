package BST;

public class ArrayBasedBST<E extends Comparable<E>> implements BST<E> {

  private static final int DEFAULT_CAPACITY = 512;

  private E[] elements;
  private int size;

  @SuppressWarnings("unchecked")
  public ArrayBasedBST(int capacity) {
    elements = (E[]) new Object[capacity];
    size = 0;
  }

  public ArrayBasedBST() {
    this(DEFAULT_CAPACITY);
  }

  @Override
  public boolean add(E element) {
    return add(0, element);
  }

  private boolean add(int index, E element) {
    if (indexOutOfBounds(index)) {
      throw new ArrayIndexOutOfBoundsException("Index " + index +
          " is out of the range of the array.");
    }
    if (elements[index] == null) {
      elements[index] = element;
      incrementSize();
      return true;
    } else {
      if (element.compareTo(elements[index]) == 0) {
        //the element is already in the set
        return false;
      } else if (element.compareTo(elements[index]) < 0) {
        //recursively try to add to the left subtree
        int leftSubtree = getLeftChild(index);
        return add(leftSubtree, element);
      } else {
        //recursively try to add to the right subtree
        int rightSubtree = getRightChild(index);
        return add(rightSubtree, element);
      }
    }
  }

  @Override
  public boolean remove(E element) {
    return false;
  }

  @Override
  public boolean contains(E element) {
    return contains(0, element);
  }

  private boolean contains(int index, E element) {
    if (indexOutOfBounds(index)) {
      throw new ArrayIndexOutOfBoundsException("Index " + index +
          " is out of the range of the array.");
    }
    if (elements[index] == null) {
      return false;
    } else {
      if (element.compareTo(elements[index]) == 0) {
        return true;
      } else if (element.compareTo(elements[index]) < 0) {
        int leftSubtree = getLeftChild(index);
        return contains(leftSubtree, element);
      } else {
        int rightSubtree = getRightChild(index);
        return contains(rightSubtree, element);
      }
    }
  }

  private void incrementSize() {
    size++;
  }

  private int getLeftChild(int index) {
    return 2 * index + 1;
  }

  private int getRightChild(int index) {
    return 2 * index + 2;
  }

  private boolean indexOutOfBounds(int index) {
    return index >= elements.length;
  }

}
