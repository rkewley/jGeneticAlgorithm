package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import java.util.Vector;
import java.util.Enumeration;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.Uniform;
import cern.jet.random.Binomial;


/**
 * The purpose of a species object is to manage a set of populations of identically
 * structured genomes.  Members of a population evolve together, and members of the
 * different populations migrate to other populations according to the migration
 * rate of the species.
 */
public class Species {

  /**
   * A vector of populations of identically structured genomes.  Genomes in each population
   * should be able to evolve and mate with genomes in all of the other populations.
   * They are in the same "species."
   */
  Vector populations;

  /**
   * A uniform distribution object from the cern.jet.random library
   */
  Uniform uniform;

  /**
   * A binomial distribution object
   */
  Binomial binomial;

  /**
   * This is the rate at which genomes of one population migrate out to another population
   * when this species performs migration.
   */
  double migrationRate;

  /**
   * Constructor
   * @param vectorOfPopulations A vector of Population objects for this species
   * @param aMigrationRate The rate at which genomes from one population migrate to another population
   */

  public Species(Vector vectorOfPopulations, double aMigrationRate, RandomEngine e) throws GAException {
    if (aMigrationRate < 0 || aMigrationRate > 1) {
      throw new GAException(
          "The migration rate for a species must be between 0 and 1.");
    }
    migrationRate = aMigrationRate;
    populations = vectorOfPopulations;
    uniform = new Uniform(e);
    binomial = new Binomial(2, .1, e);
  }

  /**
   * Moves members of one population to other populations according to the
   * migration rate.
   */
  public void migrate() {
    int i,j;

    // no migration done if there is only one population or migration rate is 0
    if (populations.size() <= 1 || migrationRate <= 0.0) {
      return;
    }

    Vector migrants = new Vector();
    for (i = 0; i < populations.size(); i++) {
      migrants.add(new Vector());
    }

    for (i = 0; i < populations.size(); i++) {
      Population sourcePop = (Population)populations.get(i);
      Vector genomes = sourcePop.getGenomes();
      int numMigrants = binomial.nextInt(genomes.size(), migrationRate);
      for (j = 0; j < numMigrants; j++) {
        Genome migrant = (Genome)genomes.remove(0);
        int destination = i;
        while (destination != i) {
          destination = uniform.nextIntFromTo(0, populations.size());
        }
        Vector destinationPopulation = (Vector)migrants.get(destination);
        destinationPopulation.add(migrant);
      }
    }

    for(i = 0; i < populations.size(); i++) {
      Vector newMembers = (Vector)migrants.get(i);
      Population newPop = (Population)populations.get(i);
      Vector newGenomes = newPop.getGenomes();
      Enumeration enu = newMembers.elements();
      while (enu.hasMoreElements()) {
        Genome g = (Genome)enu.nextElement();
        newGenomes.add(g);
      }
      newPop.sort();
    }
  }

  /**
   * Returns the Vector of Populations in this Species
   */
  public Vector getPopulations() {
    return populations;
  }

  /**
   * Sets the migration rate for the species, the fraction of members of a
   * population that will migrate to another population each generation.
   * This value must be between 0 and 1.
   */
  public void setMigrationRate(double r) throws GAException {
    if (r < 0 || r > 1) {
      throw new GAException(
          "The migration rate for a species must be between 0 and 1.");
    }
    migrationRate = r;
  }

  /**
   * Returns the migration rate for the species.
   */
  public double getMigrationRate() {
    return migrationRate;
  }

  /**
   * This method tells all populations in the species to evolve the next generations
   */
  public void evolveAllPopulations() throws GAException {
    Enumeration enu = populations.elements();
    while (enu.hasMoreElements()) {
      Population pop = (Population)enu.nextElement();
      pop.evolveNextGeneration();
    }
  }
}