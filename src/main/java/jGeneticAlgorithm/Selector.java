package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import cern.jet.random.engine.RandomEngine;

/**
 * All implementations of the Selector class select Genomes from a population
 * in order to produce offspring for the subsequent generation.
 */
public abstract class Selector {

  /**
   * A random discrete distribution from cern.jet.random.EmpiricalWalker
   */
  RandomEngine randomEngine;

  /**
   * Constructor
   * @param e A random number generator from cern.jet.random.engine library
   */
  public Selector(RandomEngine e) {
    super();
    randomEngine = e;
  }

  /**
   * Initializes the selector to perform selections on the given population
   * with its current fitness values.  In this abstract class, this method
   * does nothing.  However, some selectors, such as the roullette wheel
   * selector, must be initialized with each population before use.  In that
   * case, the roullette wheel selector overrides this method with the
   * appropriate initialization routine.
   */
  public void initializeWith(Population p) {
  }

  /**
   * Returns a selected genome from the given population
   */
  public abstract Genome selectFrom(Population p) throws GAException;
}