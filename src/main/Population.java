package main;

import java.util.ArrayList;
import java.util.Random;


public class Population extends ArrayList<Chromosome> {
	public ArrayList<ArrayList<Double>> chromosomes = new ArrayList<ArrayList<Double>>();
	public ArrayList<Double> totalLengthList = new ArrayList<Double>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Population() {
		super(HybGenAl.POPULATION_SIZE);
	}

	public Population(Population population) {
		super(population);
	}

	public void initialize() {
		int i;
		ChromosomeFactory chromosomeFactory = new ChromosomeFactory();
		for (i = 0; i < HybGenAl.POPULATION_SIZE; i++) {
			add(chromosomeFactory.generateRandomChromosome());
		}
	}

	public void selectionFiftyPC() {
		Population childrenSel = new Population(this);
		this.clear();
		int i;
		for (i = 0; i <= childrenSel.size() * HybGenAl.SELECTION_RATE-1; i++) {
			this.add(childrenSel.get(i));
			System.out.println(i + ": " + this.get(i));
		}
	}

	public Population elite() {
		Population elite = new Population(this);
		this.clear();
		int i;
		for (i = 0; i <= elite.size() * HybGenAl.ELITE_RATE + 2; i++) {
			this.add(elite.get(i));
			System.out.println(i + ": " + this.get(i));

		}
		return this;
	}
	
	public Population futureGenerator() {
		Random rnd = new Random();
		Population popul = new Population(this);
		this.clear();
		int i;
		int chromLen;
		int point;
		int temp;
		for (i = 0; i < popul.size() * HybGenAl.ELITE_RATE; i++) {
			this.add(popul.get(i));
		}
		for (int g = this.size(); g <= 48; g++) {
			Chromosome parent1 = popul.get(rnd.nextInt(popul.size()));
			Chromosome parent2 = popul.get(rnd.nextInt(popul.size()));
			Chromosome child1 = parent1.clone();
			Chromosome child2 = parent2.clone();
			if (child1.getSize() >= child2.getSize()) {
				chromLen = child2.getSize();
			} else {
				chromLen = child1.getSize();
			}
			if (chromLen > 1) {
				if (chromLen == 2) {
					point = 1;
				} else {
					point = rnd.nextInt(chromLen - 1);
				}
				for (i = 0; i < point; i++) {
					temp = parent1.getGene(i);
					child1.setGene(i, parent2.getGene(i));
					child2.setGene(i, temp);
					child1.evaluateTotalLength();
					child2.evaluateTotalLength();
				}
				this.add(g, child1);
				this.add((g + 1), child2);
				g++;
			}
		}
		return this;
	}

	public void routeConstruction() {
		Population childrenSel = new Population(this);
		this.clear();
		int i;
		for (i = 0; i < childrenSel.size(); i++) {
			Chromosome chrom = childrenSel.get(i);
			chrom.routeConstruction();
			chrom.evaluateTotalLength();
			this.add(chrom);
			//System.out.println(i + ": " + this.get(i));
		}
	}
	
/*	public Population corrector() {
		Population childrenSel = new Population(this);
		this.clear();
		int i;
		for (i = 0; i < childrenSel.size(); i++) {
			Chromosome chrom = childrenSel.get(i);
			for (int j = 1; j<chrom.getSize(); j++){
				int prevGene = chrom.getGene(j-1);
				int gene = chrom.getGene(j);
				if ( gene == prevGene){
					chrom.setGene(gene, prevGene);
				}
			}
			chrom.evaluateTotalLength();
			this.add(chrom);
		}
		return this;
	}*/
	
	public void bestRoute(){
		System.out.println("------------------------Best route-------------------");
		System.out.println(this.get(0));
		}
	
	public void show() {
		for (int i = 0; i < this.size(); i++) {
			System.out.println(i + ": " + this.get(i));
		}
	}

}