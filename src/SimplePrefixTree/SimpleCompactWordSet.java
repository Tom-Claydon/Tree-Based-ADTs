package SimplePrefixTree;

import java.util.List;

public interface SimpleCompactWordSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    if (word == null || word.length() == 0) {
      throw new InvalidWordException("Word cannot be null or empty.");
    }
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) < 'a' || word.charAt(i) > 'z') {
        throw new InvalidWordException("Word must only contain characters between 'a' and 'z'.");
      }
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();
}
