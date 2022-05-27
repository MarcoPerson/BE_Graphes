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
    
    public void initTabLabel(ShortestPathData data){
    	double distanceDestination = 0;
    	Node destination = graph.getNodes().get(data.getDestination().getId());
    	Node origin = graph.getNodes().get(data.getOrigin().getId());
    	distanceDestination = origin.getPoint().distanceTo(destination.getPoint());
		tabLabel[origin.getId()] = new LabelStar(origin, false, null, distanceDestination);
    	tabLabel[data.getOrigin().getId()].setCost(0);
    }
    
    public void addLabel(int id, ShortestPathData data) {
    	double distanceDestination = 0;
    	Node destination = graph.getNodes().get(data.getDestination().getId());
    	distanceDestination = graph.getNodes().get(id).getPoint().distanceTo(destination.getPoint());
    	tabLabel[id] = new LabelStar(graph.getNodes().get(id), false, null, distanceDestination);
    }

}
