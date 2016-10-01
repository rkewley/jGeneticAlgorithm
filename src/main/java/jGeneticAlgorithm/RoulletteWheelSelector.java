package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Empirical;
import cern.jet.random.engine.RandomEngine;
import java.util.*;

/**
 * The RoulletteWheelSelector is the default Selector in the jGeneticAlgorithm
 * package.  This Selector selects Genomes for producing offspring where the
 * probability of selection for a Genome is equal to its scaled fitness
 * (or the inverse of its scaled fitness if minimizing).
 */
public class RoulletteWheelSelector extends Selector {

  /**
   * A random discrete distribution from cern.jet.random.EmpiricalWalker
   */
  EmpiricalWalker dist = null;

  public RoulletteWheelSelector(RandomEngine e) {
    super(e);
  }

  /**
   * Initializes the selector to perform selections on the given population
   * with its current fitness values.  This method must be called at each
   * generation before selection can begin with the selectFrom method.
   */
  public void initializeWith(Population p) {
    double[] pdf;        // the pdf for genome selection
    Enumeration e;       // an enumeration of genomes in the population
    Genome g;            // an individual genome in the population.
    double minFitness;   // the minimum scaled fitness in the population
    boolean b = true;
    int i = 0;

    // determine minimum fitness for the population at set temp fitness
    //   equal to scaled fitness
    e = p.getGenomes().elements();
    minFitness = ((Genome)p.getGenomes().firstElement()).scaledFitness;
    while (e.hasMoreElements()) {
      g = (Genome)e.nextElement();
      g.tempFitness = g.scaledFitness;
      if (g.scaledFitness < minFitness) {
        minFitness = g.scaledFitness;
      }
    }

    // if minFitness < 0, perform transformation
    if (minFitness < 0.0) {
      e = p.getGenomes().elements();
      while (e.hasMoreElements()) {
        g = (Genome)e.nextElement();
        g.tempFitness = g.tempFitness * -1.1;
      }
    }

    // if minimizing set temp fitness equal to its inverse.
    g = (Genome)p.getGenomes().firstElement();
    if (g.minimize) {
      e = p.getGenomes().elements();
      while (e.hasMoreElements()) {
        g = (Genome)e.nextElement();
        if (g.tempFitness != 0) {
          g.tempFitness = 1/g.tempFitness;
        }
      }
    }

    // now create the EmpiricalWalker distribution to use for the roullette
    //    wheel
    dist = new EmpiricalWalker(p.getTempFitnessValues().elements(),
      Empirical.NO_INTERPOLATION, randomEngine);

  }

  /**
   * Returns a selected genome from the given population.  The probability of
   * selection is proportional to the genome's scaled fitness if maximizing
   * and proportional to the inverse of scaled fitness if minimizing.  If the
   * minimum scaled fitness for any genome is less than 0, a transformation is
   * performed by adding (-1.1 * min) to each genome where min is the minimum
   * scaled fitness for the population.
   */
  public Genome selectFrom(Population p) throws GAException {
    if (dist == null) {
      throw new GAException(
        "You must initialize the RoulletteWheelSelector prior to use.");
    }
    return (Genome)p.getGenomes().elementAt(dist.nextInt());
  }
}