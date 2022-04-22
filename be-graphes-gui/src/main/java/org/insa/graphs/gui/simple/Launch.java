package org.insa.graphs.gui.simple;

import org.insa.graphs.model.io.BinaryPathReader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;

public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
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
    
    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        final String mapName = "/home/agbeti-m/Documents/BE Graphes/Maps/insa.mapgr";
        final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		//
        // TODO: Read the graph.
        final Graph graph = reader.read();
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
        
        //Test avec Bellman-Ford
        int Origin_int =  0;
        int Destination_int =  0;
        int numInspector = 0;
        
        Node Origin = new Node(Origin_int, null);
        Node Destination = new Node(Destination_int, null);
        
        List <ArcInspector> listInspector = new ArcInspectorFactory().getAllFilters();
        ArcInspector arcInspector = listInspector.get(numInspector);
        
        ShortestPathData data = new ShortestPathData(graph, Origin, Destination, arcInspector);
        //0 continuer ici: on vient de créer un data pour mettre en paramètre de bellman et dijkstra
        
    }

}
