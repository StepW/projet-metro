package trajetMetro;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class Main2 {
 public static void main(String[] args) {
		 
		 //on cherche le fichier à chercher
		 String fichiernoms = "D:/JAVA/fichiers stations/nom station.txt";
		 String fichiersommets = "D:/JAVA/fichiers stations/coordonnee sommet.txt";
		 String fichierarcs = "D:/JAVA/fichiers stations/arc valeur temps.txt";

		 File f1 = new File(fichiernoms);
		 File f2 = new File(fichiersommets);
		 File f3 = new File(fichierarcs);
		 Scanner in = null;

		 //on créé la liste des stations pour les sommets du graphe
		ArrayList<Station> l = new ArrayList();
		
		ArrayList<String> liste = new ArrayList();
		
		//on créé la liste des liaisons pour les arcs du graphe
		
		ArrayList<Station> a1 = new ArrayList();
		ArrayList<Station> b1 = new ArrayList();
		ArrayList<Station> a2 = new ArrayList();
		ArrayList<Station> b2 = new ArrayList();
		
		 //on cherche les éléments dans le fichier texte et on les range dans une liste Array
		
		 try{
			 //on appelle les fonctions pour appeler le fichier
				InputStream ips1=new FileInputStream(fichiernoms);
				InputStream ips2=new FileInputStream(fichiersommets); 
				InputStream ips3=new FileInputStream(fichierarcs); 
				InputStreamReader ipsr1=new InputStreamReader(ips1);
				InputStreamReader ipsr2=new InputStreamReader(ips2);
				InputStreamReader ipsr3=new InputStreamReader(ips3);
				BufferedReader br1=new BufferedReader(ipsr1);
				BufferedReader br2=new BufferedReader(ipsr2);
				BufferedReader br3=new BufferedReader(ipsr3);
				String ligne;

				
	

				while ((ligne=br1.readLine())!=null){

					//le premier élément d'une ligne ne doit pas commencer par ####
					
//					if(!ligne.startsWith("####")){
//						
//						//on extrait les données d'une ligne qui sont séparées par " "

						String[] st = ligne.split(" ", 2);
						
						//verificateur

//						String[] st = ligne.split("");
//							
//							//on remplit la liste de sommets avec les objets stations

						//on créé les variables pour les tests
						String nomStation = st[1].trim().toLowerCase();
						int idStation = Integer.parseInt(st[0]);

						//on créé une station test
						Station v = null;
						
						
						//on parcours la liste en train de se remplir
						for(Station station : l){
							
						//on vérifie si dans cette liste il y a des stations en double
							if(station.getNom().equals(nomStation)){
								
						//on incrémente la station en double dans la station test
								v = station;
								
						//on sort de cette boucle
								break;
							}
						}
						
						//on vérifie maintenant si la condition précédente est remplie
						if(v == null){
							
						//on ajoute la station dans la liste
							v = new Station(new ArrayList<Integer>(), idStation,nomStation);
							l.add(v);
						}
						else{
							
						//on ajoute l'identifiant dans la liste de l'objet station
							v.getLid().add(idStation);
						}


					}

				
				br1.close();
				
				
				
				while ((ligne=br2.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					int idStation = Integer.parseInt(st[0]);
					int coordX = Integer.parseInt(st[1]);
					int coordY = Integer.parseInt(st[2]);
					
					for(Station station : l){

							station.setX(coordX);;
							station.setY(coordY);;
						}
					}
					

				
				
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
		 
		 
		 Station b = new Station(new ArrayList<Integer>(),555,"fff");
		 System.out.println(b);
		 b.addId(131);
		 System.out.println(b);
		 //2 eme element dans la liste des id
		 System.out.println(b.getLid().get(1));
		 //nom
		 System.out.println(b.getNom());
		 
		 System.out.println();
		 //on supprime les doublons dans la liste des sommets
//		 Set<Station> mySet = new HashSet<Station>(l);
//		 ArrayList<Station> l2 = new ArrayList<Station>(mySet);


		 for(int i=0;i<liste.size();i++){
			 System.out.println(liste.get(i));
		 }
		 
		 for(int i=0;i<l.size();i++){
			 System.out.println(l.get(i));
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


		      
		      
//		      Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {
//
//		            @Override
//		            public Point2D transform(Station vertex) {
//		                return new Point2D.Double((double) vertex.getX()*2-100 ,
//		                		(double) Math.abs(vertex.getY() - 600)*2-100 );
//		            }
//		        };
		      
//		      StaticLayout<Station, Liaison> layout =
//		    		  new StaticLayout<Station, Liaison>(g, locationTransformer);
//			  layout.setSize(new Dimension(600,600));
//			     
//			// The BasicVisualizationServer<V,E> is parameterized by the edge types
//			     BasicVisualizationServer<Station,Liaison> vv = 
//			              new BasicVisualizationServer<Station,Liaison>(layout);
//			      vv.setPreferredSize(new Dimension(600,600)); //Sets the viewing area size
//			      
//		      JFrame frame = new JFrame("Simple Graph View");
//		      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		      frame.setSize(400, 400);
//		      frame.isResizable();
//
//		      frame.setLocationRelativeTo(null);
//		      
//
//		      
//		      frame.getContentPane().add(vv); 
//		      frame.pack();
//		      frame.setVisible(true);       
//		      
		      
//		     
		     
		 

	 }
}
