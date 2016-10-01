package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

/**
 * Objects which implement this interface may have instances as specific
 * values at locations along a genome.
 */
public interface AlleleValue {
  /**
   * Returns an independent deep copy of the allele value
   */
  public AlleleValue copy();

  /**
   * Returns a string displaying the allele value
   */
  public String display();
}

