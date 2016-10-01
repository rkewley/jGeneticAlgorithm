package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import java.lang.Cloneable;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.Uniform;

/**
 * This is an abstract class from which all AlleleSets descend.  An AlleleSet
 * is simply a set of possible values (instances of objects) for a genome to have at a location
 * along its string.
 */

public abstract class AlleleSet {
  /**
   * A random number generator from the cern.jet.random.engine library.
   * Required for the allele set to function.
   */
  RandomEngine randomGenerator;

  /**
   * A uniform distribution object from the cern.jet.random library
   */
  Uniform uniform;

  public AlleleSet(RandomEngine engine) {
    super();
    randomGenerator = engine;
    uniform = new Uniform(engine);
  }

  public abstract AlleleValue getRandomValue ();
	public abstract String debug();
}
