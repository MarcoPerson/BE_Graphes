package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
	
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public void initTabLabel(){
    	final ShortestPathData data = getInputData();
    	
    	double distanceDestination = 0;
    	Node destination = null;

    	destination = graph.getNodes().get(data.getDestination().getId());
		for(Node node : this.graph.getNodes()) {
			distanceDestination = node.getPoint().distanceTo(destination.getPoint());
			tabLabel[node.getId()] = new LabelStar(node, false, null, distanceDestination);
		} 
    }

}
