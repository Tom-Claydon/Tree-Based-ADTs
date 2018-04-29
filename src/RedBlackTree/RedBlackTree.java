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
    //TO IMPLEMENT
    return false;
  }

  @Override
  public boolean remove(E element) {
    //TO IMPLEMENT
    return false;
  }

  @Override
  public boolean contains(E element) {
    //TO IMPLEMENT
    return false;
  }

  private RedBlackNode<E> findNode(E element) {
    Node<E> current = root;
    Node<E> parent = null;
    while (current != null) {
      parent = current;
      int comparison = element.compareTo(current.getElement());
      if (comparison < 0) {
        current = current.getLeftSubtree();
      } else if (comparison == 0) {
        break;
      } else {
        assert comparison > 0;
        current = current.getRightSubtree();
      }
    }
    assert current != null;
    return new RedBlackNode<>(current.getElement(), (RedBlackNode<E>) parent);
  }

  private class RedBlackNode<T> extends Node<T> {

    private Colour colour;
    private RedBlackNode<T> parent;

    public RedBlackNode(T element, RedBlackNode<T> parent) {
      this(element);
      this.parent = parent;
    }

    public RedBlackNode(T element) {
      super(element);
      this.colour = Colour.RED;
    }

    public boolean isBlack() {
      return colour == Colour.BLACK;
    }

    public boolean isRed() {
      return colour == Colour.RED;
    }

    public void setBlack() {
      colour = Colour.BLACK;
    }

    public void setRed() {
      colour = Colour.RED;
    }

    public RedBlackNode<T> getParent() {
      return parent;
    }

    public void setParent(RedBlackNode<T> parent) {
      this.parent = parent;
    }
  }
}
