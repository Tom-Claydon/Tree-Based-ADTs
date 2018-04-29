package Heaps;

public interface IMinHeap<E extends Comparable<E>> {

  void add(E element) throws HeapException;

  E removeMin();

  boolean isEmpty();

  int size();
}
