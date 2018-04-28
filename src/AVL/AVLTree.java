package AVL;

import BST.LinkedNodesBST;
import java.util.HashSet;
import java.util.Set;

public class AVLTree<E extends Comparable<E>> extends LinkedNodesBST<E> {

  /*
  AVL Invariant: for every node, the difference between the heights of its left and right child
  sub-trees is at most 1
  A BST of height h is balanced if:
    - all nodes at levels <= h - 2 have 2 children
    - nodes at level h - 1 have 0, 1 or 2 children
    - nodes at level h have no children (are leaves)
  Check after insertion or removal whether the tree became unbalanced and then rebalance if needed
   */

  public AVLTree(E element) {
    super(element);
  }

  public AVLTree() {
    super(null);
  }

  @SuppressWarnings("unchecked")
  private AVLNode<E> rightRotation(AVLNode<E> subTree) {
    AVLNode tempTree = (AVLNode<E>) subTree.getLeftSubtree();
    subTree.setLeftSubtree(tempTree.getRightSubtree());
    tempTree.setRightSubtree(subTree);
    subTree.setHeight(Math.max(height((AVLNode<E>) subTree.getLeftSubtree()), height((AVLNode<E>) subTree.getRightSubtree())) + 1);
    tempTree.setHeight(Math.max(height((AVLNode<E>) tempTree.getLeftSubtree()), subTree.getHeight()) + 1);
    return tempTree;
  }

  @SuppressWarnings("unchecked")
  private AVLNode<E> leftRotation(AVLNode<E> subTree) {
    AVLNode tempTree = (AVLNode<E>) subTree.getRightSubtree();
    subTree.setRightSubtree(tempTree.getLeftSubtree());
    tempTree.setLeftSubtree(subTree);
    subTree.setHeight(Math.max(height((AVLNode<E>) subTree.getLeftSubtree()), height((AVLNode<E>) subTree.getRightSubtree())) + 1);
    tempTree.setHeight(Math.max(height((AVLNode<E>) tempTree.getRightSubtree()), subTree.getHeight()) + 1);
    return tempTree;
  }

  private AVLNode<E> leftRightRotation(AVLNode<E> subTree) {
    subTree.setLeftSubtree(leftRotation((AVLNode<E>) subTree.getLeftSubtree()));
    return rightRotation(subTree);
  }

  private AVLNode<E> rightLeftRotation(AVLNode<E> subTree) {
    subTree.setRightSubtree(rightRotation((AVLNode<E>) subTree.getRightSubtree()));
    return leftRotation(subTree);
  }

  private void rebalance(AVLNode<E> node) {
    int deltaHeight = deltaHeight(node);
    if (deltaHeight == 2) {
      //tree must be unbalanced to the left
      if (height((AVLNode<E>) node.getLeftSubtree().getLeftSubtree()) >=
          height((AVLNode<E>) node.getLeftSubtree().getRightSubtree())) {
        rightRotation(node);
      } else {
        leftRightRotation(node);
      }
    } else if (deltaHeight == -2) {
      if (height((AVLNode<E>) node.getRightSubtree().getRightSubtree()) >=
          height((AVLNode<E>) node.getRightSubtree().getLeftSubtree())) {
        leftRotation(node);
      } else {
        rightLeftRotation(node);
      }
    }
  }

  private int height(AVLNode<E> node) {
    return node == null ? -1 : node.getHeight();
  }

  private int deltaHeight(Node<E> node) {
    return height((AVLNode<E>) node.getLeftSubtree()) - height((AVLNode<E>) node.getRightSubtree());
  }

  public boolean add(E element) {
    if (getRoot() == null) {
      setRoot(element);
      return true;
    } else {
      return add(root, element);
    }
  }


  private boolean add(Node<E> subTree, E element) {
    if (element.compareTo(subTree.getElement()) == 0) {
      //element is already in the tree
      return false;
    } else if (element.compareTo(subTree.getElement()) < 0) {
      //element is in the left subtree
      if (subTree.getLeftSubtree() == null) {
        //reached the leaves
        subTree.setLeftSubtree(new AVLNode<>(element));
        rebalance((AVLNode<E>) subTree);
        return true;
      } else {
        //recursively add to the left subtree
        return add(subTree.getLeftSubtree(), element);
      }
    } else {
      //element is in the right subtree
      if (subTree.getRightSubtree() == null) {
        //reached the leaves
        subTree.setRightSubtree(new AVLNode<>(element));
        rebalance((AVLNode<E>) subTree);
        return true;
      } else {
        //recursively add to the right subtree
        return add(subTree.getRightSubtree(), element);
      }
    }
  }

  public boolean remove(E element) {
    Set<Node<E>> deletedNode = new HashSet<>();
    root = remove(root, element, deletedNode);
    return !deletedNode.isEmpty();
  }

  private Node<E> remove(Node<E> subTree, E element, Set<Node<E>> deletedNode) {
    if (subTree == null) {
      //the node is not in the set
      return null;
    } else if (element.compareTo(subTree.getElement()) < 0) {
      subTree.setLeftSubtree(remove(subTree.getLeftSubtree(), element, deletedNode));
      rebalance((AVLNode<E>) subTree);
    } else if (element.compareTo(subTree.getElement()) > 0) {
      subTree.setRightSubtree(remove(subTree.getRightSubtree(), element, deletedNode));
      rebalance((AVLNode<E>) subTree);
    } else {
      deletedNode.add(subTree);
      subTree = deleteNode(subTree);
    }
    return subTree;
  }

  public Node<E> getRoot() {
    return root;
  }

  public void setRoot(E element) {
    root = new AVLNode<>(element);
  }

  private class AVLNode<T> extends Node<T> {

    private int height;

    private AVLNode(T element) {
      super(element);
      this.height = 0;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }
  }

}
