package org.insa.graphs.model;

public class Label implements Comparable<Label>{
	private Node sommetCourant ;
	private boolean marque ;
	private double cout;
	private Node pere;
	
	public Label(Node sommetCourant, boolean marque, Node pere) {
		this.setSommetCourant(sommetCourant);
		this.setMarque(marque);
		this.cout = Double.POSITIVE_INFINITY;
		this.setPere(pere);
	}
	
	public double getCost() {
		return this.cout;
	}
	
	public boolean getMarque() {
		return this.marque;
	}
	
	public void setMarque(boolean marque) {
		this.marque = marque;
	}
	
	public void setCost(double cost) {
		this.cout = cost;
	}
	
	public int compareTo(Label autre) {
		int result;
		if(this.cout == autre.getCost()){
			result = 0;
		} else if( this.cout >autre.getCost()){
			result = 1;
		}else{
			result = -1;
		}
		return result;
	}

	public Node getSommetCourant() {
		return sommetCourant;
	}

	public void setSommetCourant(Node sommetCourant) {
		this.sommetCourant = sommetCourant;
	}

	public Node getPere() {
		return pere;
	}

	public void setPere(Node pere) {
		this.pere = pere;
	}

}
