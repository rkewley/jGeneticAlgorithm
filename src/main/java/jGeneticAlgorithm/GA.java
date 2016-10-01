package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import java.util.Vector;
import java.util.Enumeration;

/**
 * The genetic algorithm object simply iterates evaluation, evolution, and migration
 * for the species in the algorithm.  An understanding of the notions species,
 * population, and parallel evolution is necessary in order to use this GA
 * object.
 *
 * A species is a collection of organisms that are allowed to mate with one another.
 * In this GA object, a species is a number of populations, all of the same species.
 * A population is a group of organisms of the same species that lives in close
 * proximity to one another and mate with one another, producing offspring
 * on a regular basis.  The only way that two member of a species from different
 * populations will produce offspring is for members of one population to migrate
 * to another population.  This is done by setting the migrationRate parameter
 * of this GA.
 *
 * This GA may evaluate member of different species in a competitive environment,
 * allowing co-evolution of parallel populations of different species.  In this
 * manner, several populations of different species can compete with one another
 * using the Evalute object in order to be assigned a fitness value, which determines
 * the organism's chances of producing offspring.
 *
 * In many optimization schemes, one species, with only one population, is sufficient.
 * However, multiple populations of the same species may allow the production
 * of different solutions to the optimization problem.  As migration between the
 * populations increases, the diversity becomes less.
 */
public class GA {
  /**
   * The different species objects evolved in parallel by this algorithm
   */
  Vector species;

  /**
   * Rate of migration between populations
   */
  double migrationRate;

  /**
   * Number of generations the for which the algorithm will evolve
   */
  int numGenerations;

  /**
   * The current generation
   */
  int generation;

  /**
   * Set this value to true to enable automatic recording of genomes.
   * Not yet implemented
   */
  boolean recordGenomes = false;

  /**
   * The object which evaluates all of species and their subpopulations
   */
  Evaluate evaluator;


  /**
   * Construcor
   * @param numberGenerations The number of generations for which this algorithm
   * will evolve
   * @param evaluationObject An object which implements the Evaluate Interface
   * to evaluate the species and populations in this GA
   * @param evaluatedSpecies A Vector of species to be evaluated.
   */
  public GA(int numberGenerations, Evaluate evaluationObject, Vector evaluatedSpecies) throws GAException {
    this.setNumGenerations(numberGenerations);
    evaluator = evaluationObject;
    species = evaluatedSpecies;
    generation = 1;
  }

  public void setNumGenerations(int numberGenerations) throws GAException {
    if (numberGenerations < 0) {
      throw new GAException(
          "The number of generations must not be negative");
    }
    numGenerations = numberGenerations;
  }

  public int getNumGenerations() {
    return numGenerations;
  }

  /**
   * This method evaluates all members of all populations in each species
   */
  public void evaluate() {
    evaluator.evaluate(species);
  }

  /**
   * This method tells all species to have all populations evolve the next generation
   */
  public void evolveAllSpecies() throws GAException {
    Enumeration enu = species.elements();
    while (enu.hasMoreElements()) {
      Species spec = (Species)enu.nextElement();
      spec.evolveAllPopulations();
    }
  }

  /**
   * This method performs migration for the species in the algorithm
   */
  public void performMigration() {
    Enumeration enu = species.elements();
    while (enu.hasMoreElements()) {
      Species spec = (Species)enu.nextElement();
      spec.migrate();
    }
  }

  /**
   * This method steps the algorithm by performing evaluation, evolution, and migration
   * in succession
   */
  public void stepGeneration() throws GAException {
    this.evaluate();
    this.evolveAllSpecies();
    this.performMigration();
    generation++;
  }

  public int getGeneration() {
    return generation;
  }

  /**
   * This method steps the algorithm a specific number of generations
   */
  public void stepNGenerations(int n) throws GAException {
    for (int i = 0; i < n; i++) {
      this.stepGeneration();
    }
  }

  /**
   * This method evolves the algorithm until the numberGenerations stopping
   * criterion is met
   */
  public void evolve() throws GAException {
    while (generation < numGenerations) {
      this.stepGeneration();
    }
  }

  public Vector getSpecies() {
    return species;
  }

}