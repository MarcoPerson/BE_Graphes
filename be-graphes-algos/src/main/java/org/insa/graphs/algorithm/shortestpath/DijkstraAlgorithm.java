package org.insa.graphs.algorithm.shortestpath;

import java.util.Arrays;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        // Initialize array of successors.
        Arc[] successorsArcs = new Arc[nbNodes];
        
        //Initialize array of label 
        Label[] tabLabel = new Label[nbNodes];
        
        for(Node node : graph.getNodes()) {
        	tabLabel[node.getId()] = new Label(node.getId(), false, 0);
        } 
        
        tabLabel[data.getOrigin().getId()] = new Label (data.getOrigin().getId(), true, data.getOrigin().getId());
        tabLabel[data.getOrigin().getId()].setCost(0);
        
        BinaryHeap heapLabel = new BinaryHeap();
        for (Label label : tabLabel) {
        	heapLabel.insert((Comparable) label);
        }
        
        
        ShortestPathSolution solution = null;
        // TODO:
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
