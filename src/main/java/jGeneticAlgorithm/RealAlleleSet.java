package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import cern.jet.random.engine.RandomEngine;

/**
 * This allele set randomly generates real AlleleValues in the
 * interval [min, max].
 */
public class RealAlleleSet extends AlleleSet {

  /**
   * The minimum value in the allele set
   *
   */
  double min;

  /**
   * The maximum value in the allele set
   */
  double max;

  /**
   * Constructor
   * @param engine The random engine for this FA
   * @param mn The minimum real value in the allele set
   * @param mx The maximum real value in the allele set
   */
  public RealAlleleSet(RandomEngine engine, double mn, double mx) throws GAException {
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
    return new RealAlleleValue(uniform.nextDoubleFromTo(min, max));
  }

  /**
   * Returns a string description of this object
   */
  public String describeParameters() {
    return "Range: " + Double.toString(min) + " "
      + Double.toString(max);
  }

  /**
   * Returns debugging information about this object
   */
  public String debug() {
    return super.toString() + "  " + this.describeParameters();
  }
}