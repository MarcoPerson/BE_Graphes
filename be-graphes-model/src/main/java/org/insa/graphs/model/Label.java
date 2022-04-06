package org.insa.graphs.model;

public class Label {
	private int sommetCourant ;
	private boolean marque ;
	private double cout;
	private int pere;
	
	public Label(int sommetCourant, boolean marque, int pere) {
		this.sommetCourant = sommetCourant;
		this.marque = marque;
		this.cout = Double.POSITIVE_INFINITY;
		this.pere = pere;
	}
	
	public double getCost() {
		return this.cout;
	}
	
	public boolean getMarque() {
		return this.marque;
	}
	
	public void setCost(double cost) {
		this.cout = cost;
	}
	
	public double compareTo(Label autre) {
		return this.cout - autre.getCost();
	}

}
