package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import cern.jet.random.engine.RandomEngine;
import cern.jet.random.Uniform;
import java.util.Vector;
import java.util.Collections;

/**
 * This Selector selects a genome from a Population in order to produce
 * offspring.  It does so in a "tournamemt" of N randomly selected
 * genomes, where the most fit one is selected.
 */
public class TournamentSelector extends Selector {

  /**
   * The number of genomes to select for comparison in the tournament
   */
  int numberInTournament;

  /**
   * A uniform distribution object from the cern.jet.random library
   */
  Uniform uniform;

  /**
   * Constructor
   * @param e A RanomEngine object from the cern.jet.random.engine library
   * @param n The number of genome in the tournament.  Must be a positive
   * integer.
   */
  public TournamentSelector(RandomEngine e, int n) throws GAException {
    super(e);
    uniform = new Uniform(e);
    this.setNumberInTournament(n);
  }

  /**
   * Sets the number of genomes to select for the tournament
   * @param n The number of genomes to select
   */
  public void setNumberInTournament(int n) throws GAException {
    if (n <= 0) {
      throw new GAException(
          "The number of genomes in the tournament must be a positive integer");
    }
    numberInTournament = n;
  }

  /**
   * Returns the the number of genomes in the tournament.
   */
  public int getNumberInTournament() {
    return numberInTournament;
  }


  /**
   * Returns a selected genome from the given population using tournament
   * selection.  This algorithm randomly selects n = numberInTournament
   * genomes at random from the population.  It then compares fitness on
   * those genomes and returns the most fit as the selected genome.  The
   * greater the number of genomes in the tournament, the more pressure there
   * will be to select the most fit genomes.
   */
  public Genome selectFrom(Population p) throws GAException {
    Vector genomes;      // the vector of genomes in the population
    Vector selectedGenomes;  // those genomes randomly selected for the tournament
    Genome g;
    int maxIndex;        // The max index of the genomes vector
    int i;

    selectedGenomes = new Vector(numberInTournament);
    genomes = p.getGenomes();
    maxIndex = genomes.size() - 1;

    for (i = 0; i < numberInTournament; i++) {
      g = (Genome)genomes.elementAt(uniform.nextIntFromTo(0, maxIndex));
      selectedGenomes.add(g);
    }

    // Sort the selected genomes by fitness
    Collections.sort(selectedGenomes);
    return (Genome)selectedGenomes.firstElement();

  }
}