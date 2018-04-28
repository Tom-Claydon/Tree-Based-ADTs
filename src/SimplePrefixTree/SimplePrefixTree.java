package SimplePrefixTree;

import java.util.ArrayList;
import java.util.List;

public class SimplePrefixTree implements SimpleCompactWordSet {

  private SimplePrefixTreeNode root;
  private int size;

  public SimplePrefixTree() {
    root = new SimplePrefixTreeNode('"');
    size = 0;
  }

  private void incrementSize() {
    size++;
  }

  private void decrementSize() {
    size--;
  }

  @Override
  public synchronized boolean add(String word) throws InvalidWordException {
    SimpleCompactWordSet.checkIfWordIsValid(word);
    if (contains(word)) {
      return false;
    }
    SimplePrefixTreeNode node = getRoot();
    for (int i = 0; i < word.length(); i++) {
      char currentChar = word.charAt(i);
      if (!node.containsKey(currentChar)) {
        node.put(currentChar, new SimplePrefixTreeNode(currentChar));
      }
      node = node.getChild(currentChar);
    }
    node.setWord();
    incrementSize();
    return true;
  }

  @Override
  public synchronized boolean remove(String word) throws InvalidWordException {
    SimpleCompactWordSet.checkIfWordIsValid(word);
    if (contains(word)) {
      SimplePrefixTreeNode node = searchPrefix(word);
      assert node != null;
      node.setNotWord();
      decrementSize();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public synchronized boolean contains(String word) throws InvalidWordException {
    SimpleCompactWordSet.checkIfWordIsValid(word);
    SimplePrefixTreeNode node = searchPrefix(word);
    return node != null && node.isWord();
  }

  @Override
  public synchronized int size() {
    return size;
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    return new ArrayList<>(getRoot().findWords());
  }

  private SimplePrefixTreeNode searchPrefix(String word) {
    SimplePrefixTreeNode node = getRoot();
    for (int i = 0; i < word.length(); i++) {
      char curLetter = word.charAt(i);
      if (node.containsKey(curLetter)) {
        node = node.getChild(curLetter);
      } else {
        return null;
      }
    }
    return node;
  }

  public SimplePrefixTreeNode getRoot() {
    return root;
  }

  private class SimplePrefixTreeNode {

    private static final int SIZE_OF_ALPHABET = 26;

    private char label;
    private SimplePrefixTreeNode[] children;
    private boolean isWord;

    SimplePrefixTreeNode(char label) {
      this.label = label;
      children = new SimplePrefixTreeNode[SIZE_OF_ALPHABET];
      isWord = false;
    }

    public boolean isWord() {
      return isWord;
    }

    public void setWord() {
      isWord = true;
    }

    public void setNotWord() {
      isWord = false;
    }

    public SimplePrefixTreeNode getChild(char c) {
      return children[c - 'a'];
    }

    public SimplePrefixTreeNode getChild(int index) {
      return children[index];
    }

    public void put(char c, SimplePrefixTreeNode node) {
      children[c - 'a'] = node;
    }

    public boolean containsKey(char c) {
      return children[c - 'a'] != null;
    }

    public boolean containsKey(int index) {
      return children[index] != null;
    }

    public List<String> findWords() {
      List<String> result = new ArrayList<>();
      if (isWord()) {
        result.add("");
      }
      for (int i = 0; i < children.length; i++) {
        if (containsKey(i)) {
          List<String> childResult = getChild(i).findWords();
          char letter = (char) ('a' + i);
          for (String suffix : childResult) {
            result.add("" + letter + suffix);
          }
        }
      }
      return result;
    }

    public char getLabel() {
      return label;
    }

    public void setLabel(char label) {
      this.label = label;
    }

  }
}
