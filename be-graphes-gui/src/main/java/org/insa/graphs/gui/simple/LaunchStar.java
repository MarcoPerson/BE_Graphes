package org.insa.graphs.gui.simple;

import org.insa.graphs.model.io.BinaryPathReader;

import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class LaunchStar {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
	
	private static int Origin_int;
	private static int Destination_int;
    private static int numInspector;
    
    private static Node Origin;
    private static Node Destination;
    
    private static List <ArcInspector> listInspector;
    private static ArcInspector arcInspector;
    
    private static ShortestPathData data;
    
    private static BellmanFordAlgorithm bellManAlgo;
    private static DijkstraAlgorithm dijkstraAlgo;
    private static AStarAlgorithm aStarAlgo;
    
    private static ShortestPathSolution solutionBellMan;
    private static ShortestPathSolution solutionDijkstra;
    private static ShortestPathSolution solutionAStar;
    
    private static Graph graph = null;
    
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }
    
    @BeforeClass
    public static void initAll() throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        final String mapName = "/home/agbeti-m/Documents/BE Graphes/Maps/insa.mapgr";
        final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		//
        // TODO: Read the graph.
        graph = reader.read();
		//
		//        // Create the drawing:
		//        final Drawing drawing = createDrawing();
		//
		//        // TODO: Draw the graph on the drawing.
		//        drawing.drawGraph(graph);
		//
		//        // TODO: Create a PathReader.
		//        final PathReader pathReader = new BinaryPathReader(
		//                new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
		//
		//        // TODO: Read the path.
		//        final Path path = pathReader.readPath(graph);
		//
		//        // TODO: Draw the path.
		//        drawing.drawPath(path);
        
    }
    public void Initializer(int Origin_param, int Destination_param, int Road) {
    	Origin = new Node(Origin_param, null);
        Destination = new Node(Destination_param, null);
        
        System.out.println(Origin.hasSuccessors());
        
        listInspector = new ArcInspectorFactory().getAllFilters();
        arcInspector = listInspector.get(Road);
        System.out.println(arcInspector.toString());
        
        data = new ShortestPathData(graph, Origin, Destination, arcInspector);
        
        dijkstraAlgo = new DijkstraAlgorithm(data);
        bellManAlgo = new BellmanFordAlgorithm(data);
        aStarAlgo = new AStarAlgorithm(data);
        
        solutionDijkstra = dijkstraAlgo.run();
        solutionBellMan = bellManAlgo.run();
        solutionAStar = aStarAlgo.run();
        
    }
	@Test
    public void TestShortestAllRoads() throws IOException {
		Initializer(0,5,0);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,344,0);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(644,518,0);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(73,1276,0);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(187,656,0);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
	
	@Test
    public void TestShortestCarsOnly() throws IOException {
		Initializer(253,5,1);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,344,1);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(644,518,1);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(74,214,1);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(501,1024,1);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
	@Test
    public void TestFastestAllRoads() throws IOException {
		Initializer(0,5,2);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,344,2);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(644,518,2);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(73,1276,2);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(187,656,2);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
	
	@Test
    public void TestFastestCarsOnly() throws IOException {
		Initializer(253,5,3);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,344,3);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(644,518,3);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(74,214,3);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(501,1024,3);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
	
	@Test
    public void TestFastestPedestrians() throws IOException {
		Initializer(253,5,4);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,344,4);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(644,518,4);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(74,214,4);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(501,1024,4);
    	assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
	@Test
	public void TestRoadCarsNotFound() throws IOException {
		Initializer(0,5,0);
		if(solutionDijkstra.getPath() != null) {
			assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
		}
    	Initializer(73,1276,0);
    	if(solutionDijkstra.getPath() != null) {
			assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
		}
    	Initializer(187,656,0);
    	if(solutionDijkstra.getPath() != null) {
			assertEquals(solutionBellMan.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
		}
    }
}
