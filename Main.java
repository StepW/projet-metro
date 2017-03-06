package trajetMetro;

import java.awt.Dimension;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class Main {
	
	 public static void main(String[] args) {
		 
		 //on cherche le fichier à chercher
		 String filename = "D:/JAVA/metro.txt";

		 File f = new File(filename);
		 Scanner in = null;

		 //on créé la liste des stations pour les sommets du graphe
		ArrayList<Station> l = new ArrayList();
		
		//on créé la liste des liaisons pour les arcs du graphe
		
		ArrayList<Station> a1 = new ArrayList();
		ArrayList<Station> b1 = new ArrayList();
		
		 //on cherche les éléments dans le fichier texte et on les range dans une liste Array
		 try{
				InputStream ips=new FileInputStream(filename); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;

				while ((ligne=br.readLine())!=null){

						String[] st = ligne.split(":");
						
						//on remplit la liste de sommets avec les objets stations
					l.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
					
					if(st[0].equals("1") && st[1].equals("a")){
						a1.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
					}
					
					if(st[0].equals("1") && st[1].equals("b")){
						b1.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
					}
		
				}
				br.close(); 
				
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
		 
		 
		 
		 //on supprime les doublons dans la liste des sommets
		 Set<Station> mySet = new HashSet<Station>(l);
		 ArrayList<Station> l2 = new ArrayList<Station>(mySet);

		 
//		 for(int i=0;i<l2.size();i++){
//			 System.out.println(l2.get(i));
//		 }

		 Graph<Station, Liaison> g = new SparseMultigraph<Station, Liaison>();
		 
//		 
		 for(int i=0;i<l2.size();i++){
			 g.addVertex((Station)l2.get(i));
		 }
		 
		 
//	        g.addEdge(new Liaison(l2.get(1),l2.get(2),"Ligne",12,12), l2.get(1), l2.get(2));
		 
//		 g.addVertex((Station)l2.get(1));

	        
//			 for(int i=0;i<a1.size();i++){
//			 System.out.println(a1.get(i));
//		 }
			 
		 
		 //on créé les liaisons de la ligne 1 sens a
			 for(int i=0;i<a1.size()-1;i++){
				 g.addEdge(new Liaison(a1.get(i),a1.get(i+1),
						" " + a1.get(i).getNom() + " --> " + a1.get(i+1).getNom() + " " ,12)
						 ,a1.get(i),a1.get(i+1),EdgeType.DIRECTED);

			}
//			 
//			 for(int i=0;i<b1.size()-1;i++){
//				 g.addEdge("b1-"+i,b1.get(i),b1.get(i+1),EdgeType.DIRECTED);
//
//			}
	        

	        
	        
		     System.out.println("The graph g = " + g.toString());
		     
		    
		 
//			 for(int i=0;i<=l2.size();i++){
//				 System.out.println(l2.get(i));
//			 }


		     SimpleGraphDraw sgv = new SimpleGraphDraw(); //We create our graph in here
		     // The Layout<V, E> is parameterized by the vertex and edge types
		     CircleLayout<Station, Liaison> layout = new CircleLayout<Station, Liaison>(g);
		     layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		      // The BasicVisualizationServer<V,E> is parameterized by the edge types
		      BasicVisualizationServer<Station, Liaison> vv = 
		               new BasicVisualizationServer<Station,Liaison>(layout);
		      vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		      JFrame frame = new JFrame("Simple Graph View");
		      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      frame.getContentPane().add(vv); 
		      frame.pack();
		      frame.setVisible(true);       
		     
		     
		 

	 }




}
