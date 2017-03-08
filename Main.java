package trajetMetro;

import java.awt.Dimension;
import java.awt.List;
import java.awt.geom.Point2D;
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

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
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
		ArrayList<Station> a2 = new ArrayList();
		ArrayList<Station> b2 = new ArrayList();
		
		 //on cherche les éléments dans le fichier texte et on les range dans une liste Array
		
		 try{
			 //on appelle les fonctions pour appeler le fichier
				InputStream ips=new FileInputStream(filename); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;

				while ((ligne=br.readLine())!=null){

					//le premier élément d'une ligne ne doit pas commencer par ####
					
					if(!ligne.startsWith("####")){
						
						//on extrait les données d'une ligne qui sont séparées par ":"
						
						String[] st = ligne.split(":");
							
							//on remplit la liste de sommets avec les objets stations
						l.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
						
						
						if(st[0].equals("1") && st[1].equals("a")){
							a1.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
						}
						
						if(st[0].equals("1") && st[1].equals("b")){
							b1.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
						}
						
						
						if(st[0].equals("2") && st[1].equals("a")){
							a2.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
						}
						
						if(st[0].equals("2") && st[1].equals("b")){
							b2.add(new Station(st[2],Double.parseDouble(st[3]),Double.parseDouble(st[4])));
						}
						
						
						
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

		 
		 for(int i=0;i<l2.size();i++){
			 System.out.println(l2.get(i));
		 }
		 
		 
		 
//		 for(int i=0;i<l2.size();i++){
//			 System.out.println(l2.get(i));
//		 }

		 //on créé un graphe qui va regrouper tout les éléments
		 Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
		 
//		 
		 
		 //on créé toutes les stations de la ligne 1
		 for(int i=0;i<a1.size();i++){
			 g.addVertex((Station)a1.get(i));
		 }
		 
		 
		//on créé toutes les stations de la ligne 2
		 for(int i=0;i<a2.size();i++){
			 g.addVertex((Station)a2.get(i));
		 }
		 

		 
//	        g.addEdge(new Liaison(l2.get(1),l2.get(2),"Ligne",12,12), l2.get(1), l2.get(2));
		 
//		 g.addVertex((Station)l2.get(1));

	        

			 
		 
		   //on créé toutes les liaisons de la ligne 1
			 for(int i=0;i<a1.size()-1;i++){
				 g.addEdge(new Liaison(a1.get(i),a1.get(i+1),
						" " + a1.get(i).getNom() + " --> " + a1.get(i+1).getNom() + " " ,12)
						 ,a1.get(i),a1.get(i+1));
				 g.addEdge(new Liaison(b1.get(i),b1.get(i+1),
							" " + b1.get(i).getNom() + " --> " + b1.get(i+1).getNom() + " " ,12)
							 ,b1.get(i),b1.get(i+1));

			}
			 
			 
			 for(int i=0;i<a2.size()-1;i++){
				 g.addEdge(new Liaison(a2.get(i),a2.get(i+1),
						" " + a2.get(i).getNom() + " --> " + a2.get(i+1).getNom() + " " ,12)
						 ,a2.get(i),a2.get(i+1));
				 g.addEdge(new Liaison(b2.get(i),b2.get(i+1),
							" " + b2.get(i).getNom() + " --> " + b2.get(i+1).getNom() + " " ,12)
							 ,b2.get(i),b2.get(i+1));

			}
			 

			//on créé toutes les liaisons de la ligne 2

			 
			 
			 
//			 
//			 for(int i=0;i<b1.size()-1;i++){
//				 g.addEdge("b1-"+i,b1.get(i),b1.get(i+1),EdgeType.DIRECTED);
//
//			}
	        
			 
//
//			 for(int i=0;i<a1.size();i++){
//				 System.out.println(a1.get(i));
//			 }
//			 System.out.println();
//			 for(int i=0;i<a2.size();i++){
//				 System.out.println(a2.get(i));
//			 }
//	        
			 
			 
		     System.out.println("The graph g = " + g.toString());
		     
		     for(int i=0;i<l.size();i++){
		     System.out.println("Station = " + l.get(i));
		     }
		 
//			 for(int i=0;i<=l2.size();i++){
//				 System.out.println(l2.get(i));
//			 }

		     

		     SimpleGraphDraw sgv = new SimpleGraphDraw(); //We create our graph in here
		     // The Layout<V, E> is parameterized by the vertex and edge types

		     
		     
//		     Transformer<Station,Point2D> vertexPaint = new Transformer<Station,Point2D>(g);
		     
//		     Layout<Station, Liaison> IterativeContext, Transformer<Station,Point2D>;
//		     Transformer<Station,Point2D> vertexPaint = new Transformer<Station,Point2D>();
//		     


		      
		      
		      Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {

		            @Override
		            public Point2D transform(Station vertex) {
		                return new Point2D.Double((double) vertex.getX()*2-100 ,
		                		(double) Math.abs(vertex.getY() - 600)*2-100 );
		            }
		        };
		      
		      StaticLayout<Station, Liaison> layout =
		    		  new StaticLayout<Station, Liaison>(g, locationTransformer);
			  layout.setSize(new Dimension(600,600));
			     
			// The BasicVisualizationServer<V,E> is parameterized by the edge types
			     BasicVisualizationServer<Station,Liaison> vv = 
			              new BasicVisualizationServer<Station,Liaison>(layout);
			      vv.setPreferredSize(new Dimension(600,600)); //Sets the viewing area size
			      
		      JFrame frame = new JFrame("Simple Graph View");
		      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      frame.setSize(400, 400);
		      frame.isResizable();

		      frame.setLocationRelativeTo(null);
		      

		      
		      frame.getContentPane().add(vv); 
		      frame.pack();
		      frame.setVisible(true);       
		      
		      
//		     
		     
		 

	 }

}