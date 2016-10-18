package jGeneticAlgorithm;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import java.lang.Cloneable;
import java.util.*;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.Uniform;

/**
 * The Population is a set of Genomes whose phenotypes will compete with
 * each other, based on fitness values, to generate offspring to produce
 * the subsequent population.  A Species in a GA may have one or more
 * populations.
 */
public class Population implements Cloneable {

  /**
   * Vector of genomes for this population
   */
  Vector<Genome> genomes;

  public Vector<Genome> getArchive() {
    return archive;
  }

  public void setArchive(Vector<Genome> archive) {
    this.archive = archive;
  }

  /**
   * Vector of archived best genomes for this population
   */
  Vector<Genome> archive;

  /**
   * A random number generator from the cern.jet.random.engine library.
   * Required for the population to perform random genetic operations.
   */
  RandomEngine randomGenerator;

  /**
   * A uniform distribution object from the cern.jet.random library
   */
  Uniform uniform;

  /**
   * Object to perform selection for the population
   */
  Selector selectionObject;

  /**
   * Object to perform fitness scaling for the population
   */
  Scaler scalingObject = null;

  /**
   * Reference number (usually corresponds to generation) for this population
   */
  int generation = 1;

  /**
   * The crossover rate to use when generating a new population from this one
   */
  double crossoverRate = 1.0;

  /**
   * The fraction of the current population to be repleced when forming a
   * new generation
   */
  double replacementRate = 1.0;

  /**
   * The number of best ever genomes to keep in the archive
   */
  int archiveSize = 0;

  public double getArchiveReplacementRate() {
    return archiveReplacementRate;
  }

  public void setArchiveReplacementRate(double archiveReplacementRate) {
    if (archiveReplacementRate > replacementRate) {
      archiveReplacementRate = replacementRate;
    }
    this.archiveReplacementRate = archiveReplacementRate;
  }

  /**
   * The fraction of the new population to be formed by copying the best genomes from the archive
   * Note that archiveReplacementRate cannot be greater than replacementRate
   */
  double archiveReplacementRate = 0.0;

  public ArchiveUpdater getArchiveUpdater() {
    return archiveUpdater;
  }

  public void setArchiveUpdater(ArchiveUpdater archiveUpdater) {
    this.archiveUpdater = archiveUpdater;
  }

  /**
   * The archive updater for this population
   */
  ArchiveUpdater archiveUpdater = new EliteArchiveUpdater();

  /**
   * The best genome evaluated in this population so far
   */
  Genome best = null;

  /**
   * Constructor
   * Generates a new population with no scaling object and the default
   * roullette wheel selector.
   * @param e A RandomEngine random number generator from the
   * cern.jet.random.engine library
   */
  public Population(RandomEngine e) {
    super();
    genomes = new Vector();
    randomGenerator = e;
    uniform = new Uniform(randomGenerator);
    selectionObject = new RoulletteWheelSelector(randomGenerator);
  }

  /**
   * Sets the scaling object to be used by the population to scale the raw
   * fitness values of each genome in the population and store them as the
   * scaledFitness of each genome in the population.
   * @param scaler An object that implements the Scaler interface
   */
  public void setScalingObject(Scaler scaler) {
    scalingObject = scaler;
  }

  /**
   * Sets the selection object that the population uses to select individuals
   * from the population in order to produce offspring for subseqeunt
   * generations.
   * @param selector An subclass of the Selector class
   */
  public void setSelectionObject(Selector selector) {
    selectionObject = selector;
  }

  /**
   * Sets the crossover rate for the population.
   * @param r The crossover rate must be between 0 and 1.
   */
  public void setCrossoverRate(double r) throws GAException {
    if (r < 0 || r > 1) {
      throw new GAException(
          "The crossover rate for a population must be between 0 and 1.");
    }
    crossoverRate = r;
  }

  /**
   * Sets the replacement rate for the population.
   * @param r The replacement rate must be between 0 and 1.
   */
  public void setReplacementRate(double r) throws GAException {
    if (r < 0 || r > 1) {
      throw new GAException(
          "The replacement rate for a population must be between 0 and 1.");
    }
    replacementRate = r;
    if (archiveReplacementRate > replacementRate) {
      archiveReplacementRate = replacementRate;
    }
  }

  public int getArchiveSize() {
    return archiveSize;
  }

  public void setArchiveSize(int archiveSize) {
    this.archiveSize = archiveSize;
  }

  /**
   * Returns a copy of the population object without the genomes.  It has an
   * empty genome list instead
   */
  public Population copyAllButGenomes()
      throws CloneNotSupportedException {

    Population copy;
    copy = (Population)this.clone();
    copy.genomes = new Vector();
    return copy;
  }


  /**
   * Returns a deep copy of this population to include a deep copy of the
   * genome list containing copies of the current genomes
   */
  public Population copy() throws CloneNotSupportedException {

    Population copy;
    Vector copiedGenomes;
    Enumeration e;

    copy = (Population)this.clone();
    copiedGenomes = new Vector();
    e = genomes.elements();
    while (e.hasMoreElements()) {
      copiedGenomes.addElement(((Genome)e.nextElement()).copy());
    }
    copy.genomes = copiedGenomes;
    return copy;
  }

  /**
   * Returns a cern.colt.list.DoubleArrayList of raw fitness for use with
   * descriptive statistics from cern.jet.stat.Descriptive calls
   */
  public DoubleArrayList getRawFitnessValues() {
    DoubleArrayList l;
    Enumeration e;

    l = new DoubleArrayList();
    e = genomes.elements();
    while (e.hasMoreElements()) {
      l.add(((Genome)e.nextElement()).rawFitness);
    }
    return l;
  }

  /**
   * Returns a cern.colt.list.DoubleArrayList of scaled fitness for use with
   * descriptive statistics from cern.jet.stat.Descriptive calls
   */
  public DoubleArrayList getScaledFitnessValues() {
    DoubleArrayList l;
    Enumeration e;

    l = new DoubleArrayList();
    e = genomes.elements();
    while (e.hasMoreElements()) {
      l.add(((Genome)e.nextElement()).scaledFitness);
    }
    return l;
  }

  /**
   * Returns a cern.colt.list.DoubleArrayList of scaled fitness for use with
   * descriptive statistics from cern.jet.stat.Descriptive calls
   */
  public DoubleArrayList getTempFitnessValues() {
    DoubleArrayList l;
    Enumeration e;

    l = new DoubleArrayList();
    e = genomes.elements();
    while (e.hasMoreElements()) {
      l.add(((Genome)e.nextElement()).tempFitness);
    }
    return l;
  }

  /**
   * Returns the crossover rate for this population
   */
  public double getCrossoverRate() {
    return crossoverRate;
  }

  /**
   * Returns the replacement rate for this population
   */
  public double getReplacementRate() {
    return replacementRate;
  }

  /**
   * Returns a java.util.Vector object containing the genomes in the population
   */
  public Vector getGenomes() {
    return genomes;
  }

  /**
   * Initializes the population by creating n copies of the passed in Genome
   * object and initializing the value of each genome to a new random value
   * by calling the initialize() method of each genome.
   * @param g A Genome object copied and initialized to create the initial
   * population
   * @param n The number of genomes in the initial population
   */
  public void initializeWith(Genome g, int n) throws
  CloneNotSupportedException {
    int i;
    Genome copy;

    genomes.clear();
    for (i = 0; i < n; i++) {
      copy = g.copy();
      copy.initialize();
      genomes.addElement(copy);
    }
    best = null;
  }

  /**
   * Sets the generation for this population
   * @param gen The generation, an positive integer
   */
  public void setGeneration(int gen) throws GAException {
    if (gen < 0) {
      throw new GAException(
          "The generation must be a positive integer.");
    }
    generation = gen;
  }

  /**
   * Returns the generation number for this population
   */
  public int getGeneration() {
    return generation;
  }

  /**
   * This method sorts the evaluated population according to fitness.  It also
   * assigns a rank value to each member of the population.  Finally, it updates
   * the best genome.
   */

  public void sort() {
    Enumeration e;
    Genome g;
    int i;

    // Sort the population by fitness
    Collections.sort(genomes);

    // assign a rank to each genome
    e = genomes.elements();
    i = 0;
    while(e.hasMoreElements()) {
      g = (Genome)e.nextElement();
      i++;
      g.rank = i;
    }

    // Updaate the archive

    // update the best genome
    try {
      if (best == null) {
        best = ((Genome)genomes.firstElement()).copy();
      }
      else {
        g = (Genome)genomes.firstElement();
        if (best.compareTo(g) > 0) {
          best = g.copy();
        }
      }
    }
    catch (CloneNotSupportedException exception) {
      System.err.println(exception.getMessage());
      exception.printStackTrace();
      System.exit(1);
    }

  }

  /**
   * Returns the best genome evaluated in this population so far
   */
  public Genome getBest() {
    return best;
  }

  /**
   * Scales the population using the assigned scaling object.  If there is
   * no scaling object, it will copy the rawFitness value of each genome to
   * the scaledFitness value.
   */
  public void scale() throws GAException {
    Enumeration e;
    Genome g;
    int i;


    if (scalingObject == null) {
      e = genomes.elements();
      while(e.hasMoreElements()) {
        g = (Genome)e.nextElement();
        g.scaledFitness = g.rawFitness;
      }
    }
    else {
      scalingObject.scale(this);
    }
  }

  /**
   * Initializes the roullette wheel selector for use with the population
   */
  public void initializeSelector() {
    selectionObject.initializeWith(this);
  }

  /**
   * Selects a member from the population using the assigned selector
   */
  public Genome select() throws GAException {
    return selectionObject.selectFrom(this);
  }

  /**
   * Replaces the genome list in this population with a set of new genomes
   * created by performing the scaling, selection, crossover, and mutation
   * operators using the parameters of this population.
   * @param n The number of members of the next generation
   */
  public void evolveNextGeneration(int n) throws GAException {
    Vector nextGeneration;   // a Vecor object holding the next generation's genomes
    int i;
    int numSurvivors;
    Genome mom, dad;
    Genome[] kids;

    this.scale();  // scale the population
    this.sort();
    this.initializeSelector();
    nextGeneration = new Vector();

    // copy a fraction of the genomes (1 - replacementRate) to the next generation
    numSurvivors = (int)(genomes.size() * (1 - replacementRate));
    for (i = 0; i < numSurvivors; i++) {
      Genome survivor = (Genome)genomes.elementAt(i);
      //System.out.println("Copying a survivor");
      try {
        nextGeneration.addElement(survivor.copy());
      }
      catch (CloneNotSupportedException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        System.exit(1);
      }
    }


    // copy a fraction of the archive (archiveReplacementRate) to the next generation
    int numReplacers = (int)(genomes.size() * archiveReplacementRate);
    for (i = 0; i < numReplacers; i++) {
      Genome replacer = (Genome)archive.elementAt(i);
      //System.out.println("Copying a replacer");
      try {
        nextGeneration.addElement(replacer.copy());
      }
      catch (CloneNotSupportedException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        System.exit(1);
      }
    }

    // Add new genomes using selection, crossover, and mutation
    // Select the dad
    while (nextGeneration.size() < n) {
      // select the parents
      dad = selectionObject.selectFrom(this);
      mom = selectionObject.selectFrom(this);

      if (uniform.nextDoubleFromTo(0, 1) < crossoverRate) { // perform crossover
        kids = mom.crossWith(dad);
      }
      else { // or simply copy parents to make kids
        kids = new Genome[2];
        kids[0] = mom;
        kids[1] = dad;
      }

      // add kids to next generation
      for (i = 0; i < kids.length; i++) {
        kids[i].mutate();
        nextGeneration.addElement(kids[i]);
        if (nextGeneration.size() == n) {
          break;
        }
      }
    }
    genomes = nextGeneration;
    generation++;
  }

  /**
   * Replaces the genome list in this population with a set of new genomes
   * created by performing the scaling, selection, crossover, and mutation
   * operators using the parameters of this population.
   */
  public void evolveNextGeneration() throws GAException {
    this.evolveNextGeneration(genomes.size());
  }

  /**
   * Returns a string description of this object's parameters
   */
  public String describeParameters() {
    String description;

    description = "generation: " + generation + "\n";
    description += "replacementRate: " + replacementRate + "\n";
    description += "crossoverRate: " + crossoverRate + "\n";
    if (scalingObject != null) {
      description += "scalingObject: " + scalingObject.toString() + "\n";
    }
    else {
      description += "No scalingObject\n";
    }
    description += "selectionObject: " + selectionObject.toString() + "\n";
    return description;
  }

  /**
   * Returns debugging information about the population and a list of debugging
   * information about each genome in the population
   */
  public String debug() {
    String debug;

    debug = super.toString() + "\n" + this.describeParameters();
    debug += "Genomes:\n";

    Enumeration enu = genomes.elements();
    while (enu.hasMoreElements()) {
      Genome item = (Genome)enu.nextElement();
      debug += item.rank + " " + item.debug() + "\n";
    }
    return debug;
  }


}