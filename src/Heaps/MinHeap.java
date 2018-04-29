package Heaps;

public class MinHeap<E extends Comparable<E>> implements IMinHeap<E> {

  private static final int DEFAULT_CAPACITY = 512;

  private E[] elements;
  private int size;

  @SuppressWarnings("unchecked")
  public MinHeap(int capacity) {
    elements = (E[]) new Object[capacity];
    size = 0;
  }

  public MinHeap() {
    this(DEFAULT_CAPACITY);
  }

  public void incrementSize() {
    size++;
  }

  public void decrementSize() {
    size--;
  }

  private int parent(int index) {
    return (index - 1) / 2;
  }

  private int leftChild(int index) {
    return 2 * index + 1;
  }

  private int rightChild(int index) {
    return 2 * index + 2;
  }

  private void swap(int i, int j) {
    E temp = elements[i];
    elements[i] = elements[j];
    elements[j] = temp;
  }

  @Override
  public void add(E element) throws HeapException {
    if (size == elements.length) {
      throw new HeapException("Heap is full.");
    }
    elements[size] = element;
    int i = size;
    while (i > 0 && elements[i].compareTo(elements[parent(i)]) < 0) {
      swap(i, parent(i));
      i = parent(i);
    }
    incrementSize();
  }

  @Override
  public E removeMin() {
    if (isEmpty()) {
      throw new HeapException("Heap is empty.");
    }
    E min = elements[0];
    decrementSize();
    elements[0] = elements[size];
    rebuildHeap(0);
    return min;
  }

  private void rebuildHeap(int i) {
    int left = leftChild(i);
    int right = rightChild(i);
    int compareTo;
    if (right < size) {
      compareTo = elements[left].compareTo(elements[right]) <= left ? left : right;
    } else {
      compareTo = left;
    }
    if (left < size && elements[i].compareTo(elements[compareTo]) > 0) {
      swap(i, compareTo);
      rebuildHeap(compareTo);
    }
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  public E getMin() {
    if (isEmpty()) {
      return null;
    }
    return elements[0];
  }

}
