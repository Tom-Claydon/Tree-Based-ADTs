package AVL;

import java.util.HashSet;
import java.util.Set;

public class AVLTree<E extends Comparable<E>> {

  /*
  AVL Invariant: for every node, the difference between the heights of its left and right child
  sub-trees is at most 1
  A BST of height h is balanced if:
    - all nodes at levels <= h - 2 have 2 children
    - nodes at level h - 1 have 0, 1 or 2 children
    - nodes at level h have no children (are leaves)
  Check after insertion or removal whether the tree became unbalanced and then rebalance if needed
   */

  private AVLNode<E> root;

  public AVLTree(E element) {
    root = new AVLNode<>(element);
  }

  public AVLTree() {
    root = null;
  }

  @SuppressWarnings("unchecked")
  private AVLNode<E> rightRotation(AVLNode<E> subTree) {
    AVLNode tempTree = subTree.getLeftSubTree();
    subTree.setLeftSubTree(tempTree.getRightSubTree());
    tempTree.setRightSubTree(subTree);
    subTree.setHeight(Math.max(height(subTree.getLeftSubTree()), height(subTree.getRightSubTree())) + 1);
    tempTree.setHeight(Math.max(height(tempTree.getLeftSubTree()), subTree.getHeight()) + 1);
    return tempTree;
  }

  @SuppressWarnings("unchecked")
  private AVLNode<E> leftRotation(AVLNode<E> subTree) {
    AVLNode tempTree = subTree.getRightSubTree();
    subTree.setRightSubTree(tempTree.getLeftSubTree());
    tempTree.setLeftSubTree(subTree);
    subTree.setHeight(Math.max(height(subTree.getLeftSubTree()), height(subTree.getRightSubTree())) + 1);
    tempTree.setHeight(Math.max(height(tempTree.getRightSubTree()), subTree.getHeight()) + 1);
    return tempTree;
  }

  private AVLNode<E> leftRightRotation(AVLNode<E> subTree) {
    subTree.setLeftSubTree(leftRotation(subTree.getLeftSubTree()));
    return rightRotation(subTree);
  }

  private AVLNode<E> rightLeftRotation(AVLNode<E> subTree) {
    subTree.setRightSubTree(rightRotation(subTree.getRightSubTree()));
    return leftRotation(subTree);
  }

  private void rebalance(AVLNode<E> node) {
    int deltaHeight = deltaHeight(node);
    if (deltaHeight == 2) {
      //tree must be unbalanced to the left
      if (height(node.getLeftSubTree().getLeftSubTree()) >=
          height(node.getLeftSubTree().getRightSubTree())) {
        rightRotation(node);
      } else {
        leftRightRotation(node);
      }
    } else if (deltaHeight == -2) {
      if (height(node.getRightSubTree().getRightSubTree()) >=
          height(node.getRightSubTree().getLeftSubTree())) {
        leftRotation(node);
      } else {
        rightLeftRotation(node);
      }
    }
  }

  private int height(AVLNode<E> node) {
    return node == null ? -1 : node.getHeight();
  }

  private int deltaHeight(AVLNode<E> node) {
    return height(node.getLeftSubTree()) - height(node.getRightSubTree());
  }

  public boolean add(E element) {
    if (getRoot() == null) {
      setRoot(element);
      return true;
    } else {
      return add(root, element);
    }
  }


  private boolean add(AVLNode<E> subTree, E element) {
    if (element.compareTo(subTree.getElement()) == 0) {
      //element is already in the tree
      return false;
    } else if (element.compareTo(subTree.getElement()) < 0) {
      //element is in the left subtree
      if (subTree.getLeftSubTree() == null) {
        //reached the leaves
        subTree.setLeftSubTree(new AVLNode<>(element));
        rebalance(subTree);
        return true;
      } else {
        //recursively add to the left subtree
        return add(subTree.getLeftSubTree(), element);
      }
    } else {
      //element is in the right subtree
      if (subTree.getRightSubTree() == null) {
        //reached the leaves
        subTree.setRightSubTree(new AVLNode<>(element));
        rebalance(subTree);
        return true;
      } else {
        //recursively add to the right subtree
        return add(subTree.getRightSubTree(), element);
      }
    }
  }

  public boolean remove(E element) {
    Set<AVLNode<E>> deletedNode = new HashSet<>();
    root = remove(root, element, deletedNode);
    return !deletedNode.isEmpty();
  }

  private AVLNode<E> remove(AVLNode<E> subTree, E element, Set<AVLNode<E>> deletedNode) {
    if (subTree == null) {
      //the node is not in the set
      return null;
    } else if (element.compareTo(subTree.getElement()) < 0) {
      subTree.setLeftSubTree(remove(subTree.getLeftSubTree(), element, deletedNode));
      rebalance(subTree);
    } else if (element.compareTo(subTree.getElement()) > 0) {
      subTree.setRightSubTree(remove(subTree.getRightSubTree(), element, deletedNode));
      rebalance(subTree);
    } else {
      deletedNode.add(subTree);
      subTree = deleteNode(subTree);
    }
    return subTree;
  }

  private AVLNode<E> deleteNode(AVLNode<E> subTree) {
    if (subTree.getLeftSubTree() == null && subTree.getRightSubTree() == null) {
      //Case 1: no children - the subTree node is a leaf node
      return null;
    } else if (subTree.getLeftSubTree() != null && subTree.getRightSubTree() == null) {
      //Case 2: 1 child - the subTree node has a left child
      return subTree.getLeftSubTree();
    } else if (subTree.getLeftSubTree() == null && subTree.getRightSubTree() != null) {
      //Case 3: 1 child = the subTree node has a right child
      return subTree.getLeftSubTree();
    } else {
      //Case 4: 2 children - replace the subTree node with the min node from the right child
      AVLNode<E> newSubTreeNode = findMinNode(subTree.getRightSubTree());
      newSubTreeNode.setLeftSubTree(subTree.getLeftSubTree());
      newSubTreeNode.setRightSubTree(removeMinNode(subTree.getRightSubTree()));
      return newSubTreeNode;
    }
  }

  private AVLNode<E> removeMinNode(AVLNode<E> subTree) {
    if (subTree.getLeftSubTree() == null) {
      return subTree.getRightSubTree();
    } else {
      subTree.setLeftSubTree(removeMinNode(subTree.getLeftSubTree()));
      return subTree;
    }
  }

  private AVLNode<E> findMinNode(AVLNode<E> subTree) {
    if (subTree.getLeftSubTree() == null) {
      //if the left child is null we have found the smallest element in the subTree
      return subTree;
    } else {
      //recursively continue the search for the smallest element
      return findMinNode(subTree.getLeftSubTree());
    }
  }

  public boolean contains(E element) {
    return contains(root, element);
  }

  private boolean contains(AVLNode<E> subTree, E element) {
    if (subTree == null) {
      //element is not in the tree
      return false;
    } else if (element.compareTo(subTree.getElement()) == 0) {
      //found the element
      return true;
    } else if (element.compareTo(subTree.getElement()) < 0) {
      //keep searching recursively in the left subtree
      return contains(subTree.getLeftSubTree(), element);
    } else {
      //keep searching recursively in the right subtree
      return contains(subTree.getRightSubTree(), element);
    }
  }

  public AVLNode<E> getRoot() {
    return root;
  }

  public void setRoot(E element) {
    root = new AVLNode<>(element);
  }

  private class AVLNode<T> {

    private T element;
    private AVLNode<T> leftSubTree;
    private AVLNode<T> rightSubTree;
    private int height;


    private AVLNode(T element, AVLNode<T> leftSubTree, AVLNode<T> rightSubTree) {
      this.element = element;
      this.leftSubTree = leftSubTree;
      this.rightSubTree = rightSubTree;
      this.height = 0;
    }

    private AVLNode(T element) {
      this(element, null, null);
    }

    public T getElement() {
      return element;
    }

    public AVLNode<T> getLeftSubTree() {
      return leftSubTree;
    }

    public void setLeftSubTree(AVLNode<T> leftSubTree) {
      this.leftSubTree = leftSubTree;
    }

    public AVLNode<T> getRightSubTree() {
      return rightSubTree;
    }

    public void setRightSubTree(AVLNode<T> rightSubTree) {
      this.rightSubTree = rightSubTree;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }
  }

}
