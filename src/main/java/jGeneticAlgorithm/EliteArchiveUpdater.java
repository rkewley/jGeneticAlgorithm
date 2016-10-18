package jGeneticAlgorithm;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Updates an archive by keeping the most elite members
 */
public class EliteArchiveUpdater implements ArchiveUpdater {
    @Override
    public void updateArchive(Vector<Genome> genomes, Vector<Genome> archive, int maxArchiveSize) {
        Enumeration<Genome> e = genomes.elements();
        while (e.hasMoreElements()) {
            Genome genome = e.nextElement();
            Genome worstArchive = archive.lastElement();
            if (genome.compareTo(worstArchive) == -1) { // If the genome from the population is more fit
                add(genome, archive, maxArchiveSize);
            } else {
                break;
            }
        }
    }

    void add(Genome genome, Vector<Genome> archive, int maxArchiveSize) {
        archive.add(genome);
        Collections.sort(archive);
        if(archive.size() > maxArchiveSize) {
            archive.remove(archive.lastElement());
        }
    }
}
