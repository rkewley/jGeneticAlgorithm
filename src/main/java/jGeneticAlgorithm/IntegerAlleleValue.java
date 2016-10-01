package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * Copyright:    Copyright (c) 2002
 * @version 1.0
 */



import java.lang.Integer;

/**
 * An IntegerAlleleValue is an integer value that can reside as an AlleleValue
 * on a Genome.
 */
public class IntegerAlleleValue implements AlleleValue {

  /**
   * The value of the integer being stored on the Genome.
   */
  public int value;


  /**
   * Constructor
   * @param i The integer value this AlleleValue will take
   */
  public IntegerAlleleValue(int i) {
    value = i;
  }

  /**
   * Returns a copy of this AlleleValue
   */
  public AlleleValue copy() {
    return new IntegerAlleleValue(value);
  }

  /**
   * Returns a String representation of the IntegerAlleleValue
   */
  public String display() {
    return Integer.toString(value);
  }
}