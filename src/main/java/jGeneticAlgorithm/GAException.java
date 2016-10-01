package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

/**
 * This type of exceptioin is thrown when the jGeneticAlgorithm package
 * detects an error specific to genetic algorithms.
 */

public class GAException extends Exception {

  public GAException() {
    super();
  }

  public GAException(String s) {
    super(s);
  }
}