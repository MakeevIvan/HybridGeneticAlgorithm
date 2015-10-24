package ñommon;

import java.util.Random;

import ñommon.Customer;
import main.Problem;

public class Customer {
	private final double demand;
    private double current_demand;
    protected double xs_display, ys_display;
	private final double x;
	private final double y;
	private final int id;

	final Random random = new Random();
	
    public Customer(int id, double x, double y, double demand) {
    	this.id = id;
    	this.x = x;
        this.y = y;
        this.demand = demand;
    }

    public double setX(double x) {
        this.xs_display = x;
        return xs_display;
    }

    public double setY(double y) {
        this.ys_display = y;
        return ys_display;
    }
    
    public double getDisplayX() {
        return xs_display;
    }
    public double getDisplayY() {
        return ys_display;
    }
    public int getId(){
		return id;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getXid(int p){
		double s = 0;
		if (p == this.id){
			s = x;
		}
		return s;
	}
	
	public double getDemand(){
		return current_demand;
	}
	
	public double getCurrentDemand(){
		this.current_demand = this.demand;
		return current_demand;
	}
	
	public double receiveGoods(double c) {
        return this.current_demand -= c;
        
    }
	
	public void resetDemand() {
		this.current_demand = this.demand;
	}
	
	public String toString() {
		return String.valueOf(this.id);	
	}
	
	public double distanceTo(Customer p) {
		return Math.sqrt(Math.pow(this.y-p.y, 2)+Math.pow(this.x-p.x, 2));
	}
	
	public double findMaxPointX() {
        double maxX = 0;
        for (Customer c: Problem.customers) {
            if (c.getX() > maxX) maxX = c.getX();
         }
        return maxX;
    }
	
	public double findMaxPointY() {
        double maxY = 0;
        for (Customer c: Problem.customers) {
            if (c.getY() > maxY) maxY = c.getY();
         }
        return maxY;
    }
	

	
	public int getNearestMaxCustomerId() {
		double dist;
		double dem;
	    double minDist = Double.MAX_VALUE;
	    int minId = -1;
	    double max_demand = 0;
	    for (Customer p : Problem.customers) {
	    	dist = this.distanceTo(p);
	    	dem = p.getDemand();
	    	if (dist > 0 && dem > 0){ // not self
	    		if (dist < minDist) {
	    			max_demand = 0;
    				minDist = dist;
    				minId = p.getId();
    				max_demand = dem;
	    		} else if (dist == minDist){
	    			if (dem > max_demand){
	    				minDist = dist;
	    				minId = p.getId();
	    				max_demand = dem;
	    			}
	    		}
	    	}
	    }
	    return minId;
	}
	
}
