package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import java.io.Serializable;

/**
 * Objects which implement this interface may have instances as specific
 * values at locations along a genome.
 */
public interface AlleleValue extends Serializable {
  /**
   * Returns an independent deep copy of the allele value
   * @return a copy of the current AlleleValue
   */
  public AlleleValue copy();

  /**
   * Returns a string displaying the allele value
   * @return The display string for the allele value
   */
  public String display();
}

