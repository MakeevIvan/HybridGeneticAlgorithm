package main;

import java.util.List;
import java.util.ArrayList;

import ñommon.Customer;
import ñommon.Vehicle;


public class Chromosome implements Comparable<Chromosome> {

	List<Integer> chromosome;
	private double totalLength = 0;
	public Vehicle vehicle = new Vehicle(0, HybGenAl.MAX_CAPACITY);
	public ArrayList<ArrayList<Double>> chromosomes = new ArrayList<ArrayList<Double>>();
	public ArrayList<Double> totalLengthList = new ArrayList<Double>();

	public Chromosome() {
		chromosome = new ArrayList<Integer>(Problem.customersNumber);
	}

	public Chromosome clone() {
		Chromosome clone = new Chromosome();
		clone.chromosome = new ArrayList<Integer>(this.chromosome); 
		clone.totalLength = this.totalLength;
		return clone;
	}

	public void setChromosome(List<Integer> newChromosome) {
		this.chromosome = new ArrayList<Integer>(newChromosome);
	}
	

	public boolean isCorrect() {
		for (Customer cust : Problem.customers) {
			if (!this.contains(cust.getId())) {
				return false;
			}
		}
		return true;
	}

	public void evaluateTotalLength() {
		double currentLength = Problem.getCustomer(0).distanceTo(Problem.getCustomer(chromosome.get(1)));
		totalLength = currentLength;
		Customer customer = null;
		int prevGene = 0;
		for (int gene : chromosome) {
			customer = Problem.getCustomer(gene);
			if (gene != chromosome.get(1)) {
				currentLength += Problem.getCustomer(prevGene).distanceTo(
						customer);
				totalLength += Problem.getCustomer(prevGene).distanceTo(
						customer);
			}
			prevGene = gene;
		}
		totalLength += customer.distanceTo(Problem.getCustomer(0));
	}
		
/*	public void corrector(){
		int prevGene = 0;
		Customer customer = null;
		Customer customerPrev = null;
		for (int gene : chromosome){
			
			customer = Problem.getCustomer(gene);
			customerPrev = Problem.getCustomer(prevGene);
				if (customer == customerPrev){
					
					chromosome.set(prevGene, gene);
				}
				prevGene = gene;
		}
	}*/

	public void routeConstruction() {
		Customer customer = null;
		Problem.resetAllDemands();
		for (int gene : chromosome) {
			customer = Problem.getCustomer(gene);
			if (gene == 0) {
				vehicle.loadFullGoods();
			} else {
				if ((customer.getDemand()) <= vehicle.getGoods()) {
					vehicle.unloadGoods(customer.getDemand());
					customer.receiveGoods(customer.getDemand());
				} else {
					customer.receiveGoods(vehicle.getGoods());
					vehicle.unloadGoods(vehicle.getGoods());
				}
			}
		}
		int nearestCust = customer.getNearestMaxCustomerId();
		while (nearestCust != -1) {
			customer = Problem.getCustomer(nearestCust);
			chromosome.add(nearestCust);
			if ((customer.getDemand()) <= vehicle.getGoods()) {
				vehicle.unloadGoods(customer.getDemand());
				customer.receiveGoods(customer.getDemand());
			} else {
				customer.receiveGoods(vehicle.getGoods());
				vehicle.unloadGoods(vehicle.getGoods());
			}
			nearestCust = customer.getNearestMaxCustomerId();
			if (vehicle.getGoods() == 0) {
				chromosome.add(0);
				vehicle.loadFullGoods();
				nearestCust = Problem.getCustomer(0).getNearestMaxCustomerId();
			}
		}
		int lastGene = chromosome.lastIndexOf(0);
		if (lastGene != chromosome.size() - 1) {
			chromosome.add(0);
		}
	}

	public boolean contains(int gene) {
		for (int i : chromosome) {
			if (i == gene) {
				return true;
			}
		}
		return false;
	}

	List<Integer> getChromosome() {
		return this.chromosome;
	}

	public int getSize() {
		return chromosome.size();
	}

	public double getTotalLength() {
		return totalLength;
	}

	public String toString() {
		StringBuilder str = new StringBuilder(chromosome.size() * 2);
		for (int i : chromosome) {
			str.append(i);
			str.append(" ");
		}
		str.append(" -> " + this.getTotalLength());
		return str.toString();
	}

	public int compareTo(Chromosome anotherCh) {
		return (int) (100 * (this.getTotalLength() - anotherCh.getTotalLength()));
	}

	public int getGene(int i) {
		int gene = chromosome.get(i);
		return gene;
	}

	public List<Customer> chTocust() {
		ArrayList<Customer> bb =  new ArrayList<Customer>();
		for (int i : chromosome) {
			Customer customer = Problem.getCustomer(i);
			bb.add(customer);
			
			}
		return bb;
		}
				
	public void setGene(int a, int b) {
		chromosome.set(a, b);
	}

}