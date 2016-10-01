package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import java.util.*;

/**
 * The RankScaler scales a Population so that the scaledFitness of each
 * genome will be (Count - rank + 1) where Count is the number of Genomes
 * in the Population, and rank is the fitness rank of the Genome within
 * the Population.  This widely used scaling method causes the probability
 * of selection to be scaled appropriately, regardless of the raw fitness
 * difference between members of the Population.
 */
public class RankScaler implements Scaler{

  /**
   * Constructor
   */
  public RankScaler() {
    super();
  }

  /**
   * Performs fitness scaling on and evaluated population by reading raw
   * fitness for each geneome in the population and setting the scaled fitness
   * equal to (Count - rank + 1) where Count is the number of Genomes
   * in the Population, and rank is the fitness rank of the Genome within
   * the Population.
   */
  public void scale(Population pop) throws GAException {
    double i;
    Vector genomes;          // population's vector of genomes
    Enumeration genomeList;  // an enumeration of the genomes in this population
    Genome g;                // a genome in the population
		int count;               // the number of genomes in the population

    genomes = pop.getGenomes();
		count = pop.getGenomes().size();
    Collections.sort(genomes);
    genomeList = genomes.elements();
    i = 0;
    while (genomeList.hasMoreElements()) {  // for each genome
      i = i + 1.0;
      g = (Genome)genomeList.nextElement();
      if (g.isEvaluated == false) {
        throw new GAException(
          "You must evaluate a genome before scaling it.");
      }
      g.setScaledFitness(count - i + 1);
    }
  }

}