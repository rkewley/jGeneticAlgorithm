package jGATest;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * Copyright:    Copyright (c) 2002
 * @version 1.0
 */

import jGeneticAlgorithm.*;
import java.util.*;
import java.lang.*;
import cern.jet.random.engine.*;

class Evaluator implements Evaluate {
  public Evaluator() {
  }

  public void evaluate(Genome g) {
    double fitness = 0;
    ObjectGenome genome;
    genome = (ObjectGenome)g;
    int i;

    for (i = 0; i < genome.getAlleleValues().length; i++) {
      fitness += Math.abs(((RealAlleleValue)genome.getAlleleValues()[i]).value - i);
    }
    genome.setRawFitness(fitness);

  }

  public void evaluateListGenome(Genome genome) {
    int idealLength = 10;
    double penaltyHit = 10.0;
    double fitness = 0;
    ListGenome g = (ListGenome)genome;

    Vector alleleValues = g.getAlleleValues();
    int numValues = Math.min(idealLength, alleleValues.size());
    for (int i = 0; i < numValues; i++) {
      RealAlleleValue value = (RealAlleleValue)alleleValues.get(i);
      fitness += Math.abs(value.value - i);
    }

    int penalty = Math.abs(alleleValues.size() - idealLength);
    fitness += penalty * penaltyHit;
    g.setRawFitness(fitness);
  }

  public void evaluate(Vector species) {
    Species spec = (Species)species.firstElement();
    Population pop = (Population)spec.getPopulations().firstElement();
    Vector genomes = pop.getGenomes();
    Enumeration enu = genomes.elements();
    while (enu.hasMoreElements()) {
      Genome genome = (Genome)enu.nextElement();
      this.evaluateListGenome(genome);
    }
  }

}

/**
 * A test class not intended for use external to the jGeneticAlgorithm package
 */
public class PopulationTest {
  public static void main(String[] args) throws GAException, CloneNotSupportedException {
    testListGenome();
  }

  public static void testListGenome() throws GAException, CloneNotSupportedException {
    ListGenome mom, genome;
    RandomEngine engine;
    AlleleSet alleleSet;
    AlleleSet[] alleleSets;
    int i;
    int genomeLength = 5;
    int seed = 55;
    Population pop;
    Enumeration e;
    int popSize = 50;
    int generations = 100;

    engine = new MersenneTwister(seed);
    alleleSet = new RealAlleleSet(engine, 0, 10);

    alleleSets = new RealAlleleSet[genomeLength];
    for (i = 0; i < genomeLength; i++) {
      alleleSets[i] = alleleSet;
    }

    mom = new ListGenome(engine, 0, 7, alleleSet);
    mom.setMutationRate(0.1);
    //mom.setCrossoverPoints(1);
    mom.minimize = true;
    mom.initialize();

    pop = new Population(engine);
    pop.setReplacementRate(.9);
    pop.setCrossoverRate(0.8);
    pop.initializeWith(mom, popSize);


    pop.setScalingObject(new RankScaler());
    pop.setSelectionObject(new TournamentSelector(engine, 2));
    //pop.setSelectionObject(new RoulletteWheelSelector(engine));
    //pop.scale();
    //e = pop.getGenomes().elements();
    //while (e.hasMoreElements()) {
    //	genome = (ObjectGenome)e.nextElement();
    //	System.out.println(genome.debug());
    //}

    //pop.initializeSelector();

    Vector populations = new Vector();
    populations.add(pop);
    Species species = new Species(populations, 0.0, engine);
    Vector specs = new Vector();
    specs.add(species);
    GA ga = new GA(generations, new Evaluator(), specs);
    for (i = 0; i < generations; i++) {
      ga.stepGeneration();
      System.out.println("Generation number " + ga.getGeneration());
      System.out.println("Best genome\n");
      System.out.println(pop.getBest().debug());
    }

    ga.evaluate();
    species = (Species)ga.getSpecies().firstElement();
    pop = (Population)species.getPopulations().firstElement();
    pop.scale();
    pop.sort();
    System.out.println(pop.debug());
    System.out.println("Best genome\n");
    Genome bestGenome = pop.getBest();
    System.out.println(pop.getBest().debug());


    //testArrays();

  }


  public static void testArrays() {
    int[][] array;
    int[] v;
    int i,j;

    v = new int[3];
    v[0] = 0;
    v[1] = 1;
    v[2] = 2;

    array = new int[2][];
    array[0] = v;

    v = new int[2];
    v[0] = 10;
    v[1] = 20;
    array[1] = v;

    for (i = 0; i < array.length; i++) {
      for (j = 0; j < array[i].length; j++) {
        System.out.println(array[i][j]);
      }
    }
  }

  public static void testObjectGenome() throws GAException, CloneNotSupportedException {
    ObjectGenome mom, genome;
    RandomEngine engine;
    AlleleSet alleleSet;
    AlleleSet[] alleleSets;
    int i;
    int genomeLength = 10;
    int seed = 55;
    Population pop;
    Enumeration e;
    int popSize = 50;
    int generations = 100;

    engine = new MersenneTwister(seed);
    alleleSet = new RealAlleleSet(engine, 0, 20);

    alleleSets = new RealAlleleSet[genomeLength];
    for (i = 0; i < genomeLength; i++) {
      alleleSets[i] = alleleSet;
    }

    mom = new ObjectGenome(alleleSets, engine);
    mom.setMutationRate(0.1);
    mom.setCrossoverPoints(2);
    mom.minimize = true;
    mom.initialize();

    pop = new Population(engine);
    pop.setReplacementRate(.9);
    pop.setCrossoverRate(0.5);
    pop.initializeWith(mom, popSize);


    pop.setScalingObject(new RankScaler());
    pop.setSelectionObject(new TournamentSelector(engine, 2));
    //pop.setSelectionObject(new RoulletteWheelSelector(engine));
    //pop.scale();
    //e = pop.getGenomes().elements();
    //while (e.hasMoreElements()) {
    //	genome = (ObjectGenome)e.nextElement();
    //	System.out.println(genome.debug());
    //}

    //pop.initializeSelector();

    Vector populations = new Vector();
    populations.add(pop);
    Species species = new Species(populations, 0.0, engine);
    Vector specs = new Vector();
    specs.add(species);
    GA ga = new GA(generations, new Evaluator(), specs);
    for (i = 0; i < generations; i++) {
      ga.stepGeneration();
      System.out.println("Generation number " + ga.getGeneration());
      System.out.println("Best genome\n");
      System.out.println(pop.getBest().debug());
    }

    ga.evaluate();
    species = (Species)ga.getSpecies().firstElement();
    pop = (Population)species.getPopulations().firstElement();
    pop.scale();
    pop.sort();
    System.out.println(pop.debug());
    System.out.println("Best genome\n");
    System.out.println(pop.getBest().debug());


    //testArrays();

  }
}