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
    
    private static DijkstraAlgorithm dijkstraAlgo;
    private static AStarAlgorithm aStarAlgo;
    
    private static ShortestPathSolution solutionDijkstra;
    private static ShortestPathSolution solutionAStar;
    
    private static Graph graphINSA = null;
    private static Graph graphMadagascar = null;
    private static Graph graphBelgium = null;
    
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
        final String mapINSA = "/home/agbeti-m/Documents/BE Graphes/Maps/insa.mapgr";
        final String mapBelgium = "/home/agbeti-m/Documents/BE Graphes/Maps/belgium.mapgr";
        final String mapMadagascar = "/home/agbeti-m/Documents/BE Graphes/Maps/madagascar.mapgr";
        //final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader readerINSA = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapINSA))));
        final GraphReader readerBelgium = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapBelgium))));
        final GraphReader readerMadagascar = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapMadagascar))));
		//
        // TODO: Read the graph.
        graphINSA = readerINSA.read();
        graphBelgium = readerBelgium.read();
        graphMadagascar = readerMadagascar.read();
        
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
    public void Initializer(int Origin_param, int Destination_param, int Road, Graph graph) {
    	Origin = new Node(Origin_param, null);
        Destination = new Node(Destination_param, null);
        
        System.out.println(Origin.hasSuccessors());
        
        listInspector = new ArcInspectorFactory().getAllFilters();
        arcInspector = listInspector.get(Road);
        System.out.println(arcInspector.toString());
        
        data = new ShortestPathData(graph, Origin, Destination, arcInspector);
        
        dijkstraAlgo = new DijkstraAlgorithm(data);
        aStarAlgo = new AStarAlgorithm(data);
        
        solutionDijkstra = dijkstraAlgo.run();
        solutionAStar = aStarAlgo.run();
        
    }
    @Test
    public void TestOriginEqualsDestination() throws IOException {
		Initializer(0,0,0, graphINSA);
    	assertEquals(0 , solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,69,0, graphINSA);
    	assertEquals(0, solutionDijkstra.getPath().getLength(), 0);
    }
    
	@Test
    public void TestShortestAllRoads() throws IOException {
		Initializer(0,5,0, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,344,0, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(644,518,0, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(73,1276,0, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(187,656,0, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
	
	@Test
    public void TestShortestCarsOnly() throws IOException {
		Initializer(253,5,1, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(69,344,1, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(644,518,1, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(74,214,1, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(501,1024,1, graphINSA);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
	@Test
	public void TestRoadCarsNotFound() throws IOException {
		Initializer(0,5,0, graphINSA);
		if(solutionDijkstra.getPath() != null) {
			assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
		}
    	Initializer(73,1276,0, graphINSA);
    	if(solutionDijkstra.getPath() != null) {
			assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
		}
    	Initializer(187,656,0, graphINSA);
    	if(solutionDijkstra.getPath() != null) {
			assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
		}
    }
	
	@Test
    public void TestShortestLongDistance() throws IOException {
		Initializer(4574,29774,0, graphMadagascar);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(76574,36675,0, graphMadagascar);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
	}
	
	@Test
    public void TestShortestShortDistance() throws IOException {
		Initializer(63512,621254,0, graphBelgium);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    	Initializer(581892,581898,0, graphBelgium);
    	assertEquals(solutionAStar.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0);
    }
	
}
