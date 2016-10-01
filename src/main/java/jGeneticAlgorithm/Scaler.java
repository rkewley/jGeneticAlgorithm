package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

public interface Scaler {

  /**
   * Performs fitness scaling on and evaluated population by reading raw
   * fitness for each geneome in the population and setting the scaled fitness
   * for each genome in the population.  This allows the modeler to account
   * for cases where the differences in fitness are either too small or too large
   * to yield appropriate probabilities of selection using the
   * RoulletteWheelSelector.
   */
  public void scale(Population pop) throws GAException;
}