package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.Collections;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {


	public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        
        System.out.println("MODE :"+data.getMode());
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        // Initialize array.
        Arc[] arcSuccessors = new Arc[nbNodes];
        
        //Initialize array of label 
        Label[] tabLabel = new Label[nbNodes];
        
        for(Node node : graph.getNodes()) {
        	tabLabel[node.getId()] = new Label(node, false, null);
        } 
        
        tabLabel[data.getOrigin().getId()] = new Label (data.getOrigin(), true, data.getOrigin());
        tabLabel[data.getOrigin().getId()].setCost(0);
        
        BinaryHeap<Label> heapLabel = new BinaryHeap<Label>();
        for (Label label : tabLabel) {
        	heapLabel.insert(label);
        }
        
        boolean destinationReached = false;
		        
        while(!isAllMarkedTrue(tabLabel) && !destinationReached) {
        	//Dijkstra en distance
        	if(data.getMode() == AbstractInputData.Mode.LENGTH) {
        		Label labelMin = heapLabel.findMin();
            	labelMin.setMarque(true);
            	
            	//The node has been marked, notify the observers
            	notifyNodeMarked(labelMin.getSommetCourant());
            	
            	heapLabel.remove(labelMin);
            	for (Arc arc : labelMin.getSommetCourant().getSuccessors()) {
            		if(data.isAllowed(arc)) {
            			Label labelCourant = tabLabel[arc.getDestination().getId()];
                		
                		if(labelCourant.getCost()>labelMin.getCost()+arc.getLength()) {
                			
                			//Pour effectuer le changement dans le tas, on remove, on modifie puis on insert
                			heapLabel.remove(labelCourant);
                			labelCourant.setCost(labelMin.getCost()+arc.getLength());
                			labelCourant.setPere(labelMin.getSommetCourant());
                			heapLabel.insert(labelCourant);
                			arcSuccessors[labelCourant.getSommetCourant().getId()]= arc;
                			
                			//Verifie si la destination est atteinte - si oui on sort du for et du while
                			if(data.getDestination()==labelCourant.getSommetCourant()) {
                				destinationReached = true;
                				break;
                			}
                		}
            		}
            	}
        	}
        	//Dijkstra en temps
        	else if(data.getMode() == AbstractInputData.Mode.TIME){
        		Label labelMin = heapLabel.findMin();
            	labelMin.setMarque(true);
            	
            	//The node has been marked, notify the observers
            	notifyNodeMarked(labelMin.getSommetCourant());
            	
            	heapLabel.remove(labelMin);
            	for (Arc arc : labelMin.getSommetCourant().getSuccessors()) {
            		if(data.isAllowed(arc)) {
            			Label labelCourant = tabLabel[arc.getDestination().getId()];
                		
                		if(labelCourant.getCost()>labelMin.getCost()+arc.getMinimumTravelTime()) {
                			
                			//Pour effectuer le changement dans le tas, on remove, on modifie puis on insert
                			heapLabel.remove(labelCourant);
                			labelCourant.setCost(labelMin.getCost()+arc.getMinimumTravelTime());
                			labelCourant.setPere(labelMin.getSommetCourant());
                			heapLabel.insert(labelCourant);
                			arcSuccessors[labelCourant.getSommetCourant().getId()]= arc;
                			
                			//Verifie si la destination est atteinte - si oui on sort du for et du while
                			if(data.getDestination()==labelCourant.getSommetCourant()) {
                				destinationReached = true;
                				break;
                			}
                		}
            		}
            	}
        	}
        	
        }
        ShortestPathSolution solution;
		// Destination has no predecessors, the solution is infeasible...
        if (arcSuccessors[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of successors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = arcSuccessors[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = arcSuccessors[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }
    
    public boolean isAllMarkedTrue(Label[] tabLabel) {
    	for(Label label : tabLabel) {
    		if(label.getMarque() == false) {
    			return false;
    		}
    	}
    	return true ;
    }

}
