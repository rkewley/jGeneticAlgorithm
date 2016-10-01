package jGATest;

/**
 * Title:        JGeneticAlgorithm
 * Description:  A Java implementation of genetic algorithms
 * @author Robert Kewley
 * @version 1.0
 */

import jGeneticAlgorithm.*;
import java.util.*;
import java.lang.*;
import cern.jet.random.engine.*;

/**
 * Test class not intended for use external to the jGeneticAlgorithm package
 */
public class TestGenome {

  public static void main(String argv[]) throws GAException, CloneNotSupportedException {
    ObjectGenome mom, dad, child;
    Genome[] kids;
    RandomEngine engine;
    AlleleSet alleleSet;
    AlleleSet[] alleleSets;
    int i;
    int genomeLength = 5;
    int seed = 1;
    AlleleValue rValue;

    engine = new MersenneTwister(seed);
    alleleSet = new RealAlleleSet(engine, 0, 10);

		System.out.println(alleleSet.debug());
    System.out.println("A list of random real numbers from 1 to 10\n");
    for (i = 0; i < 10; i++) {
      rValue = (RealAlleleValue)alleleSet.getRandomValue();
      System.out.println(rValue.display());
    }

    alleleSets = new RealAlleleSet[genomeLength];
    for (i = 0; i < genomeLength; i++) {
      alleleSets[i] = alleleSet;
    }

    mom = new ObjectGenome(alleleSets, engine);
    mom.setMutationRate(0.5);
		mom.setNumChildren(5);
		mom.setCrossoverPoints(1);
    mom.initialize();
    System.out.println("Before mutation\n");
    System.out.println(mom.debug());
    mom.mutate();
    System.out.println("After mutation");
    System.out.println(mom.debug());

    dad = (ObjectGenome)mom.copy();
    System.out.println("Dad is a copy of mom:\n" + dad.debug());
    dad.initialize();
    System.out.println("Dad after initialization: \n" + dad.debug());
    System.out.println("Mom after dad's initialization: \n" + mom.debug());

    kids = mom.crossWith(dad);
    System.out.println("Children after crossover:\n");
    for (i = 0; i < kids.length; i++) {
			child = (ObjectGenome)kids[i];
      System.out.println(child.debug());
    }

  }
}