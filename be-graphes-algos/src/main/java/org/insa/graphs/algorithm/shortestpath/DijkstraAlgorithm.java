package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.Collections;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	protected static Label[] tabLabel = null;
	protected static Graph graph = null;

	public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        
        
        graph = data.getGraph();
        
        //Origin = Destination
        if(data.getDestination().getId() == data.getOrigin().getId()) {
        	ArrayList<Arc> arcs = new ArrayList<>();
        	ShortestPathSolution solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        	return solution;
        }
        
        final int nbNodes = graph.size();
        
        //System.out.println("MODE :"+data.getMode());
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        // Initialize array.
        Arc[] arcSuccessors = new Arc[nbNodes];
        
        //Initialize array of label 
        tabLabel = new Label[nbNodes];
        
        initTabLabel(data);
                
        //Creation du tas
        BinaryHeap<Label> heapLabel = new BinaryHeap<Label>();
//        for (Label label : tabLabel) {
//        	heapLabel.insert(label);
//        }
        
        //Mise du depart dans le tas
        heapLabel.insert(tabLabel[data.getOrigin().getId()]);
        
        
        
        boolean destinationReached = false;
        //System.out.println(data.getDestination().getId());
        
        while(!isAllMarkedTrue(tabLabel) && !destinationReached && !heapLabel.isEmpty()) {
        	//Dijkstra en distance
        	if(data.getMode() == AbstractInputData.Mode.LENGTH) {
        		
        		//labelMin
        		Label labelMin = heapLabel.findMin();
            	labelMin.setMarque(true);
            	heapLabel.remove(labelMin);
            	
            	//The node has been marked, notify the observers
            	notifyNodeMarked(labelMin.getSommetCourant());
            	
            	//Insert the successor in the heap if not already in
            	for(Arc arc : labelMin.getSommetCourant().getSuccessors()) {
            		Node nodeSuc = arc.getDestination();
            		if(tabLabel[nodeSuc.getId()] == null) {
            			addLabel(nodeSuc.getId(), data);
            		}
            		if(!tabLabel[nodeSuc.getId()].getInserted() && !tabLabel[nodeSuc.getId()].getMarque()) {
            			heapLabel.insert(tabLabel[nodeSuc.getId()]);
            			tabLabel[nodeSuc.getId()].setInserted(true);
            		}
            	}
            	
            	
            	for (Arc arc : labelMin.getSommetCourant().getSuccessors()) {
            		if(data.isAllowed(arc) && !tabLabel[arc.getDestination().getId()].getMarque()) {
            			Label labelCourant = tabLabel[arc.getDestination().getId()];
            			
            			if(!labelCourant.getInserted() || labelCourant.getMarque()) {
            				System.exit(-1);
            			}
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
        		
        		//labelMin
        		Label labelMin = heapLabel.findMin();
            	labelMin.setMarque(true);
            	heapLabel.remove(labelMin);
            	//System.out.println(labelMin.getSommetCourant().getId());
            	
            	
            	//The node has been marked, notify the observers
            	notifyNodeMarked(labelMin.getSommetCourant());
            	
            	for(Arc arc : labelMin.getSommetCourant().getSuccessors()) {
            		Node nodeSuc = arc.getDestination();
            		if(tabLabel[nodeSuc.getId()] == null) {
            			addLabel(nodeSuc.getId(), data);
            		}
            		if(!tabLabel[nodeSuc.getId()].getInserted() && !tabLabel[nodeSuc.getId()].getMarque()) {
            			heapLabel.insert(tabLabel[nodeSuc.getId()]);
            			tabLabel[nodeSuc.getId()].setInserted(true);
            		}
            	}
            	
            	
            	for (Arc arc : labelMin.getSommetCourant().getSuccessors()) {
            		if(data.isAllowed(arc) && !tabLabel[arc.getDestination().getId()].getMarque()) {
            			Label labelCourant = tabLabel[arc.getDestination().getId()];
            			
            			if(!labelCourant.getInserted() || labelCourant.getMarque()) {
            				System.exit(-1);
            			}
            			
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
    		if(label != null && label.getMarque() == false) {
    			return false;
    		}
    	}
    	return true ;
    }
    
    public void initTabLabel(ShortestPathData data){
    	tabLabel[data.getOrigin().getId()] = new Label(graph.getNodes().get(data.getOrigin().getId()), false, null);
    	tabLabel[data.getOrigin().getId()].setCost(0);
   }
    
    public void addLabel(int id, ShortestPathData data) {
    	tabLabel[id] = new Label(graph.getNodes().get(id), false, null);
    }

}
