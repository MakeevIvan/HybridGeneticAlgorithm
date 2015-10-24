package main;


import java.util.Collections;
import java.util.List;

import сommon.Customer;

public class HybGenAl {

	public static final int POPULATION_SIZE = 1000;
	public static int GENERATION_SPAN = 100;
	public static final double CROSSOVER_RATE = 0.5;
	public static final double SELECTION_RATE = 0.5;
	public static double MAX_CAPACITY = 200;
	public static final double CHROMOSOME_SIZE = 10;
	public static final double ELITE_RATE = 0.1;

	
	public void go() {
		/*int j = 0;
    	//String csvFile = "C:/Users/Ivan/Desktop/A/A-n32-k5.csv";
		String csvFile = "C:/Users/Ivan/workspace/HybridGeneticAlgorithm/src/resources/R106.csv";
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ";";
    	//int capacity = 0;
    	double[][] data = new double[101][3];
    	try {
    		 
    		br = new BufferedReader(new FileReader(csvFile));
    		line = br.readLine();
    		//capacity = Integer.parseInt(line.substring(0, line.indexOf(cvsSplitBy)));
    		while ((line = br.readLine()) != null) {
     
    		        // use comma as separator
    			String[] customer = line.split(cvsSplitBy);
    			for (int k = 0; k < 3; k++) {
    				data[j][k] = Double.parseDouble(customer[k]);
    			}
    			j++;
     
    		}
     
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}*/
    	
		double[][] testData = { 
				{ 0.00, 50.00, 0.00}, // depot
				{ 45.00, 68.00, 10.00}, // 1
				{ 45.00, 70.00, 30.00}, // 2
				{ 42.00, 66.00, 10.00}, // 3
				{ 42.00, 68.00, 10.00}, // 4
				{ 36.00, 69.00, 10.00}, // 5
				{ 34.00, 69.00, 20.00}, // 6
				{ 38.00, 69.00, 20.00}, // 7
				{ 0.00, 52.00, 0.00}, // depot
				{ 45.00, 78.00, 10.00}, // 1
				{ 45.00, 780.00, 30.00}, // 2
				{ 42.00, 63.00, 10.00}, // 3
				{ 42.00, 64.00, 10.00}, // 4
				{ 36.00, 52.00, 10.00}, // 5
				{ 34.00, 41.00, 20.00}, // 6
				{ 38.00, 64.00, 20.00}, // 7
				{ 0.00, 52.00, 0.00}, // depot
				{ 45.00, 78.00, 10.00}, // 1
				{ 45.00, 780.00, 30.00}, // 2
				{ 42.00, 69.00, 10.00}, // 3
				{ 42.00, 63.00, 10.00}, // 4
				{ 36.00, 32.00, 10.00}, // 5
				{ 34.00, 34.00, 20.00}, // 6
				{ 38.00, 35.00, 20.00}, // 7
		};
	
		//double[][] testData = Problem.CreatRand(25);
		Problem.InitCustomers(testData);
		
		/*System.out.println(Problem.getCustomer(1).getId());
		System.out.println(Problem.getCustomer(2).getId());
		System.out.println("Х");
		
		System.out.println(Problem.getCustomer(1).getX());
		System.out.println(Problem.getCustomer(2).getX());
		
		System.out.println("У");
		System.out.println(Problem.getCustomer(1).getY());
		System.out.println(Problem.getCustomer(2).getY());
		System.out.println("деманд");
		
		System.out.println(Problem.getCustomer(1).getCurrentDemand());
		System.out.println(Problem.getCustomer(2).getDemand());*/
		
		//System.out.println(Problem.customersNumber);
		//System.out.println(Problem.customers);
		//System.out.println(Problem.getCustomer(7).receiveGoods(7));
		// System.out.println(Problem.getCustomer(9).getX());
	/*	System.out.println(Problem.getCustomer(74).getDemand());
		System.out.println(Problem.getCustomer(74).getId());
		System.out.println(Problem.getCustomer(74).getY());*/
		//System.out.println(Problem.getCustomer(7).getCurrentDemand());
		//System.out.println(Problem.getCustomer(5).getNearestMaxCustomerId());
		//System.out.println(Problem.getCustomer(7).getNearestMaxCustomerId());

		Population population = new Population();
		population.initialize();
	//	System.out.println("_______________инициализация___________________");
	//	population.show();
		Collections.sort(population);
	//	System.out.println("_______________сортировка___________________");
		//population.show();
	//	System.out.println("_______________50 лучших___________________");
		population.selectionFiftyPC();
		//System.out.println("_______________кроссинговер___________________");
		/*//population.crossover();
		population.show();
		Collections.sort(population);
		System.out.println("_______________сортировка кроссинговера___________________");
		population.show();
		System.out.println("_______________эвристика___________________");
		population.routeConstruction();
		System.out.println("_______________сортировка эвристики___________________");
		Collections.sort(population);
		population.show();*/
		
		for (int i = 0; i < GENERATION_SPAN; i++) {
			System.out.println(" -------------------------------------" +i+" -------------------------------------");
			population.futureGenerator();
			Collections.sort(population);
			population.routeConstruction();
			Collections.sort(population);
			population.show();
			
		}
		population.bestRoute();
		Chromosome b = population.get(0);
		System.out.println("dfghj"+b);
		List<Customer> bb = b.chTocust();
		System.out.println("dfghjdsfg"+bb);
		int zero=0;
		for (int k=0; k<bb.size();k++){
		if (bb.get(k).getId()==0){
			zero=zero+1;
		}
		}
		System.out.println("dfghjdsfg"+ (zero-1));
	}

	public static void main(String[] args) {
		new HybGenAl().go();
	}
	
	/*public static void main(String args[]) {
        new MainPanel();
    }*/
}