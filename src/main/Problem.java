package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ñommon.Customer;

public class Problem {

	public static int customersNumber;
	public static List<Customer> customers;

	public static void InitCustomers(double[][] data) {
		customersNumber = data.length;
		customers = new ArrayList<Customer>(customersNumber);
		for (int i = 0; i < customersNumber; i++) {
			customers.add(new Customer(i, data[i][0], data[i][1], data[i][2]));
		}
	}

	public static double[][] CreatRand(int custNumber) {
		double[][] customers = new double[custNumber][3];
		Random random = new Random();
		for (int i = 0; i < custNumber; i++) {
			for (int j = 0; j < 3; j++) {
				customers[i][j] = Math.round(random.nextDouble() * 50);
			}
		}
		for (int i = 0; i < custNumber; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.println(customers[i][j] + "\t");
			}
			System.out.println();
		}
		return customers;
	}

	public static Customer getCustomer(int i) {
		return customers.get(i);
	}

	public double findMaxPointX() {
		double maxX = 0;
		for (Customer c : customers) {
			if (c.getX() > maxX)
				maxX = c.getX();
		}
		return maxX;
	}

	public double findMaxPointY() {
		double maxY = 0;
		for (Customer c : customers) {
			if (c.getY() > maxY)
				maxY = c.getY();
		}
		return maxY;
	}

	public int getCustomersCount() {
		return customers.size();
	}
	
/*	public void transform(double scale, double xoff, double yoff) {
		for (int i = this.length(); --i >= 0;) {
			Problem.getCustomer(i).setDisplayX(
					Problem.getCustomer(i).getX() * scale + xoff);
			Problem.getCustomer(i).setDisplayY(
					Problem.getCustomer(i).getY() * scale + yoff);
		}
	}*/
	
	public int length() {
		return customers.size();
	}

	public static void resetAllDemands() {
		for (Customer cust : customers) {
			cust.resetDemand();
		}
	}

	public Problem(int customersNumber) {
		customers = new ArrayList<>();
		for (int i = 0; i < customersNumber; i++) {
			customers.add(new Customer(i, 30, 35, 2));
		}
	}

	public List<Customer> getCustomers() {
		return customers;
	}

}