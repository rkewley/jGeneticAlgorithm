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

/**
 * A list genome has a variable number of allele values, but all of the allele
 * values can take on possible values from the same allele set, so it
 * needs only one allele set, as opposed to an allele set array
 */
public class ListGenome extends Genome {

  /**
   * A variable length vector of allele values for this genome
   */
  protected Vector alleleValues = new Vector();

  /**
   * The minumum number of allele values on the genome
   */
  protected int minValues;

  /**
   * The maximum number of allele values on the genome
   */
  protected int maxValues;

  /**
   * The allele set of possible values for each allele value on the genome
   */
  protected AlleleSet alleleSet;

  /**
   * Constructor
   * @param engine A RandomEngine from the ern.jet.random.engine library
   * @param minimumValues The minimum number of allele values on the genome (used for initialization)
   * @param maximumValues The maximum number of allele values on the genome (used for initialization)
   * @param alleles An allele set of possible allele values for each position on the genome
   */
  public ListGenome(RandomEngine engine, int minimumValues, int maximumValues, AlleleSet alleles) {
    super(engine);
    minValues = minimumValues;
    maxValues = maximumValues;
    alleleSet = alleles;
  }

  /**
   * Protected constructor used by subclasses
   */
  protected ListGenome(RandomEngine engine) {
    super(engine);
  }

  /**
   * Performs mutation on the genome by randomly testing each element in
   * alleleValues for mutation using the mutationRate.  If a test for
   * mutation is successful, replaces the value at alleleValues with a
   * new AlleleValue object generated by a call to alleleSets.getRandomValue()
   */
  public void mutate() {
    int i;

//		System.out.println("Before mutation: \n" + this.debug());
    for (i = 0; i < alleleValues.size(); i++) {
      if (uniform.nextDoubleFromTo(0, 1) < mutationRate) {
//				System.out.println("Mutating allele " + i);
        // add a point, replace a point, or delete a point with equal probability
        int action = uniform.nextIntFromTo(1, 3);
        if (action == 1) {
          //System.out.println("Replacing element " + i);
          alleleValues.setElementAt(alleleSet.getRandomValue(), i);
        }
        else if (action == 2) {
          //System.out.println("Removing element " + i);
          alleleValues.remove(i);
        }
        else if (action == 3) {
          //System.out.println("Adding new element " + i);
          alleleValues.add(i, alleleSet.getRandomValue());
        }
        else {
          System.out.println("Illegal action value: " + action + " in ListGenome.mutate()");
          GAException e = new GAException();
          e.printStackTrace();
          System.exit(1);
        }
      }
    }
//		System.out.println("After mutation: \n" + this.debug());
  }

  /**
   * Performs single point crossover using this genome and the dad Genome passed
   */
  public Genome[] crossWith (Genome dad) {
    Vector dadValues;
    Vector momValues;
    Vector childValues;
    ListGenome child = null;    // A child to produce
    ListGenome mommy, daddy;  // Variables used to cast mom and dad to
    //   ObjectGenome
    int momCrossPoint;
    int dadCrossPoint;
    Genome[] returnValue;       // The array of children to return
    int i,j,k;

    mommy = (ListGenome)this;
    daddy = (ListGenome)dad;
    dadValues = daddy.getAlleleValues();
    momValues = alleleValues;

    returnValue = new Genome[numChildren];

    for (i = 0; i < numChildren; i++) {
      // create the child
      try {
        child = (ListGenome)this.copy();
      }
      catch (CloneNotSupportedException e) {
        System.out.print("Clone not supported exception: " + e.getMessage());
        System.exit(1);
      }
      child.setIsEvaluated(false);
      childValues = child.getAlleleValues();
      childValues.removeAllElements();

      // generate the crossover points
      momCrossPoint = uniform.nextIntFromTo(0, mommy.getAlleleValues().size());
      dadCrossPoint = uniform.nextIntFromTo(0, daddy.getAlleleValues().size());

      // Copy the mom prior to the mom's crossover point
      for (j = 0; j < momCrossPoint; j++) {
        AlleleValue momValue = (AlleleValue)momValues.get(j);
        childValues.add(momValue.copy());
      }

      // Copy the dad after the dad'd crossover point
      for (j = dadCrossPoint; j < dadValues.size(); j++) {
        AlleleValue dadValue = (AlleleValue)dadValues.get(j);
        childValues.add(dadValue.copy());
      }
      returnValue[i] = child;
    }
    return returnValue;
  }

  /**
   * Initializes the genome by generating a random number (between minValues and maxValues)
   * of random allele values
   */
  public void initialize() {
    int i, s;

    this.setIsEvaluated(false);
    s = uniform.nextIntFromTo(minValues, maxValues);
    for (i = 0; i < s; i++) {
      alleleValues.add(alleleSet.getRandomValue());
    }
  }

  /**
   * Returns a deep copy of the ListGenome.
   */
  public Genome copy() throws CloneNotSupportedException {
    ListGenome copy;

    int i;

    copy = (ListGenome)(this.clone());
    copy.alleleValues = new Vector();
    for (i = 0; i < alleleValues.size(); i++) {
      AlleleValue value = (AlleleValue)alleleValues.get(i);
      copy.alleleValues.add(value.copy());
    }
    return copy;
  }

  /**
   * Returns the variable length vector of AlleleValues for this ListGenome
   */
  public Vector getAlleleValues() {
    return alleleValues;
  }

  /**
   * Returns a string description of this object's parameters
   */
  public String describeParameters() {
    String description;
    int i;

    description = super.describeParameters();
    description += "minimum length: " + minValues + " maximum length: " + maxValues + "\n";

    for(i = 0; i < alleleValues.size(); i++) {
      AlleleValue alleleValue = (AlleleValue)alleleValues.get(i);
      description += alleleValue.display() + " ";
    }
    description += "\n";

    return description;

  }
}