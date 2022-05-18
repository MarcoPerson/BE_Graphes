package org.insa.graphs.model;

import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {

	private double coutDestination;
	
	public LabelStar(Node sommetCourant, boolean marque, Node pere, double coutDestination) {
		super(sommetCourant, marque, pere);
		this.coutDestination = coutDestination;
	}
	
	public double getTotalCost() {
		return this.getCost() + this.getCoutDestination();
	}
	
	public double getCoutDestination() {
		return this.coutDestination;
	}
	
	public int compareTo(Label autre) {
		int result = super.compareTo(autre);
		if(result == 0) {
			if(this.getCoutDestination()>autre.getCoutDestination()) {
				result = -1;
			}else if(this.getCoutDestination()<autre.getCoutDestination()){
				result = 1;
			}else {
				result = 0;
			}
		}
		return result;
	}

}
