package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import cern.jet.random.engine.RandomEngine;

/**
 * This allele set randomly generates integer AlleleValues in the
 * inclusive interval [min, max].
 */

public  class IntegerAlleleSet extends AlleleSet {

  /**
   * The minimum value in the allele set (inclusive)
   *
   */
  int min;

  /**
   * The maximum value in the allele set (inclusive)
   */
  int max;

  /**
   * Constructor
   * @param GA A GA object in order to access random generators
   * @param mn The minimum integer value in the allele set
   * @param mx The maximum interger value in the allele set
   */
  public IntegerAlleleSet(RandomEngine engine, int mn, int mx) throws GAException {
    super(engine);
    min = mn;
    max = mx;

    if (min >= max) {
      throw new GAException(
        "When constructing an IntegerAlleleSet, min must be > max");
    }
  }

  /**
   * Returns a uniformly distributed IntegerAlleleValue from min to max
   */
  public AlleleValue getRandomValue() {
    return new IntegerAlleleValue(uniform.nextIntFromTo(min, max));
  }

  /**
   * Returns a string description of this object
   */
  public String describeParameters() {
    return "Range: " + Integer.toString(min) + " "
      + Integer.toString(max);
  }

  /**
   * Returns debugging information about this object
   */
  public String debug() {
    return super.toString() + "  " + this.describeParameters();
  }

}



