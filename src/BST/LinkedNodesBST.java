package BST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinkedNodesBST<E extends Comparable<E>> implements BST<E> {

  protected Node<E> root;

  public LinkedNodesBST(E element) {
    root = new Node<>(element);
  }

  public LinkedNodesBST() {
    root = null;
  }

  @Override
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
      //the element is already in the set
      return false;
    } else if (element.compareTo(subTree.getElement()) < 0) {
      //element is in the left subtree
      if (subTree.getLeftSubtree() == null) {
        //reached the leaves
        subTree.setLeftSubtree(new Node<>(element));
        return true;
      } else {
        //haven't reached the leaves so recursively add to left subtree
        return add(subTree.getLeftSubtree(), element);
      }
    } else {
      //element is in the right subtree
      if (subTree.getRightSubtree() == null) {
        //reached the leaves
        subTree.setRightSubtree(new Node<>(element));
        return true;
      } else {
        //haven't reached the leaves yet so recursively add to right subtree
        return add(subTree.getRightSubtree(), element);
      }
    }
  }

  @Override
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
    } else if (element.compareTo(subTree.getElement()) > 0) {
      subTree.setRightSubtree(remove(subTree.getRightSubtree(), element, deletedNode));
    } else {
      deletedNode.add(subTree);
      subTree = deleteNode(subTree);
    }
    return subTree;
  }

  protected Node<E> deleteNode(Node<E> subTree) {
    if (subTree.getLeftSubtree() == null && subTree.getRightSubtree() == null) {
      //Case 1: no children - the subTree node is a leaf node
      return null;
    } else if (subTree.getLeftSubtree() != null && subTree.getRightSubtree() == null) {
      //Case 2: 1 child - the subTree node has a left child
      return subTree.getLeftSubtree();
    } else if (subTree.getLeftSubtree() == null && subTree.getRightSubtree() != null) {
      //Case 3: 1 child = the subTree node has a right child
      return subTree.getLeftSubtree();
    } else {
      //Case 4: 2 children - replace the subTree node with the min node from the right child
      Node<E> newSubTreeNode = findMinNode(subTree.getRightSubtree());
      newSubTreeNode.setLeftSubtree(subTree.getLeftSubtree());
      newSubTreeNode.setRightSubtree(removeMinNode(subTree.getRightSubtree()));
      return newSubTreeNode;
    }
  }

  private Node<E> removeMinNode(Node<E> subTree) {
    if (subTree.getLeftSubtree() == null) {
      return subTree.getRightSubtree();
    } else {
      subTree.setLeftSubtree(removeMinNode(subTree.getLeftSubtree()));
      return subTree;
    }
  }

  private Node<E> findMinNode(Node<E> subTree) {
    if (subTree.getLeftSubtree() == null) {
      //if the left child is null we have found the smallest element in the subTree
      return subTree;
    } else {
      //recursively continue the search for the smallest element
      return findMinNode(subTree.getLeftSubtree());
    }
  }

  @Override
  public boolean contains(E element) {
    return contains(root, element);
  }

  private boolean contains(Node<E> subTree, E element) {
    if (subTree == null) {
      //element is not in the tree
      return false;
    } else if (element.compareTo(subTree.getElement()) == 0) {
      //found the element
      return true;
    } else if (element.compareTo(subTree.getElement()) < 0) {
      //keep searching recursively in the left subtree
      return contains(subTree.getLeftSubtree(), element);
    } else {
      //keep searching recursively in the right subtree
      return contains(subTree.getRightSubtree(), element);
    }
  }

  public Node<E> getRoot() {
    return root;
  }

  public void setRoot(E element) {
    root = new Node<>(element);
  }

  public List<E> preOrderTraversal() {
    List<E> traversal = new ArrayList<>();
    preOrderTraversal(root, traversal);
    return traversal;
  }

  private void preOrderTraversal(Node<E> subTree, List<E> traversal) {
    if (subTree == null) {
      return;
    }
    traversal.add(subTree.getElement());
    preOrderTraversal(subTree.getLeftSubtree(), traversal);
    preOrderTraversal(subTree.getRightSubtree(), traversal);
  }

  public List<E> inOrderTraversal() {
    List<E> traversal = new ArrayList<>();
    inOrderTraversal(root, traversal);
    return traversal;
  }

  private void inOrderTraversal(Node<E> subTree, List<E> traversal) {
    if (subTree == null) {
      return;
    }
    inOrderTraversal(subTree.getLeftSubtree(), traversal);
    traversal.add(subTree.getElement());
    inOrderTraversal(subTree.getRightSubtree(), traversal);
  }

  public List<E> postOrderTraversal() {
    List<E> traversal = new ArrayList<>();
    postOrderTraversal(root, traversal);
    return traversal;
  }

  private void postOrderTraversal(Node<E> subTree, List<E> traversal) {
    if (subTree == null) {
      return;
    }
    postOrderTraversal(subTree.getLeftSubtree(), traversal);
    postOrderTraversal(subTree.getRightSubtree(), traversal);
    traversal.add(subTree.getElement());
  }


  protected class Node<T> {

    private T element;
    private Node<T> leftSubtree;
    private Node<T> rightSubtree;

    public Node(T element) {
      this.element = element;
    }

    public T getElement() {
      return element;
    }

    public Node<T> getLeftSubtree() {
      return leftSubtree;
    }

    public Node<T> getRightSubtree() {
      return rightSubtree;
    }

    public void setLeftSubtree(Node<T> leftSubtree) {
      this.leftSubtree = leftSubtree;
    }

    public void setRightSubtree(Node<T> rightSubtree) {
      this.rightSubtree = rightSubtree;
    }
  }
}
