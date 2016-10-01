package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */
import java.util.Vector;

/**
 * An object which evaluates the populations in a genetic algorithm
 * must implement this interface.  A genetic algorithm must be assigned
 * an object implementing this interface in order to function
 */
public interface Evaluate {
  /**
   * This method supports parallel evaluation of multiple populations of
   * multiple species.  The indices of the populations matrix represent
   * species and subpopulation of that species respectively.  For example, two
   * different species may be evaluated in a competitive environment.
   * Furthermore, each species may contain subpopulations which evolve
   * independently and are subject to migration.
   */
  public void evaluate(Vector species);
}