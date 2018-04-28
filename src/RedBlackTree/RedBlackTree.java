package RedBlackTree;

import BST.LinkedNodesBST;

public class RedBlackTree<E extends Comparable<E>> extends LinkedNodesBST<E> {

  public RedBlackTree(E element) {
    root = new RedBlackNode<>(element);
  }

  public RedBlackTree() {
    root = null;
  }

  @Override
  public boolean add(E element) {
   return false;
  }

  @Override
  public boolean remove(E element) {
    return false;
  }

  @Override
  public boolean contains(E element) {
    return false;
  }

  private class RedBlackNode<T> extends Node<T> {

    private Colour colour;
    private RedBlackNode<T> parent;

    public RedBlackNode(T element) {
      super(element);
      this.colour = Colour.RED;
    }

    public Colour getColour() {
      return colour;
    }

    public void setColour(Colour colour) {
      this.colour = colour;
    }

    public RedBlackNode<T> getParent() {
      return parent;
    }

    public void setParent(RedBlackNode<T> parent) {
      this.parent = parent;
    }
  }
}
