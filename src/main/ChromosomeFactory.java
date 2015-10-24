package main;

import java.util.Random;


public class ChromosomeFactory {

	public Chromosome generateRandomChromosome() {
		Chromosome chrom = new Chromosome();
		Random rnd = new Random();
		int prevGene = 0;
		int gene;
		chrom.getChromosome().add(0);
		for (int i = 0; i < HybGenAl.CHROMOSOME_SIZE; i++) {
			gene = rnd.nextInt(Problem.customersNumber);
				if (prevGene != gene) {
					chrom.getChromosome().add(gene);
					prevGene = gene;
				}
			}
		if (prevGene != 0) {
			chrom.getChromosome().add(0);
		}
		chrom.evaluateTotalLength();
		return chrom;
	}
}

