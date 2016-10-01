package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import java.lang.Double;
import java.text.DecimalFormat;

/**
 * An RealAlleleValue is an real number value that can reside as an AlleleValue
 * on a Genome.
 */

public class RealAlleleValue implements AlleleValue {

  /**
   * The value of the real number being stored
   */
  public double value;

  /**
   * The decimal format, used for formating output of the display method.
   * The default format is #######.00
   */
  public DecimalFormat format;

  /**
   * Constructor
   * @param d The real value this AlleleValue will take
   */
  public RealAlleleValue(double d) {
    value = d;
    format = new DecimalFormat("#######.00");
  }

  /**
   * Returns a deep copy of this AlleleValue
   */
  public AlleleValue copy() {
    return new RealAlleleValue(value);
  }

  /**
   * Returns a String representation of this real value using the format
   * specified by the format property.
   */
  public String display() {
    return format.format(value);
  }
}