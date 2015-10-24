package ñommon;

import main.HybGenAl;


public class Vehicle {
	private double max_capacity = HybGenAl.MAX_CAPACITY;
    private double goods;
    
    public Vehicle(int id, double max_capacity) {
    	this.goods = max_capacity;
    	this.max_capacity = max_capacity;
    }
     
    public void setCapacity(double c) {
        this.goods = c;
    }
    
    public void loadFullGoods() {
        this.goods = max_capacity;
    }

    public boolean isFull() {
        return this.goods >= this.max_capacity;
    }

    public double getGoods() {
        return this.goods;
    }
    
    public double unloadGoods(double c){
    	return this.goods -= c;
    }

    public double getMaxLoad() {
        return this.max_capacity;
    }
    
}
