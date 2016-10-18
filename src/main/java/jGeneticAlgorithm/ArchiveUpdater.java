package jGeneticAlgorithm;

import java.util.Vector;

/**
 * Interface to update an archive of elite members based on a new population
 */
public abstract interface ArchiveUpdater {

    /**
     * Updates an archive of elite members based on a new population
     * @param genomes The new population
     * @param archive The archive of elite members
     * @param maxArchiveSize The maximim size of the archive
     */
    abstract void updateArchive(Vector<Genome> genomes, Vector<Genome> archive, int maxArchiveSize);



}
