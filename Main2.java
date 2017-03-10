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
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class Main2 {
 public static void main(String[] args) {
		 
		 //on cherche le fichier � chercher
		 String fichiernoms = "D:/JAVA/fichiers stations/nom station.txt";
		 String fichiersommets = "D:/JAVA/fichiers stations/coordonnee sommet.txt";
		 String fichierarcs = "D:/JAVA/fichiers stations/arc valeur temps.txt";

		 File f1 = new File(fichiernoms);
		 File f2 = new File(fichiersommets);
		 File f3 = new File(fichierarcs);
		 Scanner in = null;

		 //on cr�� la liste des stations pour les sommets du graphe
		ArrayList<Station> l = new ArrayList<Station>();
		
		
		//on cr�� la liste des liaisons pour les arcs du graphe
		ArrayList<Liaison> a = new ArrayList<Liaison>();
		


		
		 //on cherche les �l�ments dans le fichier texte et on les range dans une liste Array
		
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

					//le premier �l�ment d'une ligne ne doit pas commencer par ####
					
//					if(!ligne.startsWith("####")){
//						
//						//on extrait les donn�es d'une ligne qui sont s�par�es par " "

						String[] st = ligne.split(" ", 2);
						
						//verificateur

//						String[] st = ligne.split("");
//							
//							//on remplit la liste de sommets avec les objets stations

						//on cr�� les variables pour les tests
						String nomStation = st[1].trim().toLowerCase();
						int idStation = Integer.parseInt(st[0]);

						//on cr�� une station test
						Station v = null;
						
						
						//on parcours la liste des stations en train de se remplir
						
						//on ajoute une condition pour v�rifier si dans cette liste il y a des stations en double
						//si oui, on incr�mente la station en double dans la station test
						//et on sort de cette boucle
						for(Station station : l){
							if(station.getNom().equals(nomStation)){
								v = station;	
								break;
							}
						}
						
						//on v�rifie maintenant si la condition pr�c�dente est remplie
						//si oui, on ajoute la station dans la liste
						//si non, on ajoute l'identifiant dans la liste de l'objet station
						if(v == null){
							v = new Station(new ArrayList<Integer>(), idStation,nomStation);
							l.add(v);
						}
						else{
							v.getLid().add(idStation);
						}


					}

				
				br1.close();
				
				
				
				while ((ligne=br2.readLine())!=null){
					String[] st = ligne.split(" ");
					
					//on int�gre les �l�ments de la liste dans des variables
					int idStation = Integer.parseInt(st[0]);
					int coordX = Integer.parseInt(st[1]);
					int coordY = Integer.parseInt(st[2]);
					
					
					//on parcours la liste et on v�rifie dans la liste identifiants de la station
					//lorsqu'un identifiant est reconnu, on attribut les coordonn�es � la station auquelle elle appartient
					
					
					for(Station station : l){	
						for(int z : station.getLid()){
						
							if(idStation == z){
								station.setX(coordX);
								station.setY(coordY);
								}
						}
//						System.out.println(idStation+" == "+station.getId());
						
						}

				
					}
				
				br2.close();
				

				while ((ligne=br3.readLine())!=null){
					String[] st = ligne.split(" ");
					
					int arcI = Integer.parseInt(st[0]);
					int arcF = Integer.parseInt(st[1]);
					int temps = Integer.parseInt(st[2]);
					
					Liaison e = null;
					
					for(Station station : l){
						for(int z : station.getLid()){
							if(z == arcI){
								
								
								
								for(Station stat : l){
									for(int z2 : stat.getLid()){
										if(z2 == arcF){
											a.add(new Liaison(arcI,arcF,temps));
											
											
										
										}
									}
								}
							}
						}
					}
					
					
				}
				br3.close();
				
				
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
		 
		 System.out.println();
		 
		 System.out.println();
		 
		 Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
		 
		 
		 
			
		 for(Station station : l){
			 g.addVertex((Station)station);
		 }
		 
		 
		 for(Station station : l){
			 System.out.println(station);
		 }
		 
		 for(Liaison liaison : a)
			 System.out.println(liaison);
		 
		 
		 
		 for(Liaison liaison : a){
			 for(Station station : l){
				 for(int identifiant : station.getLid()){
					 if(identifiant == liaison.getA()){
						 for(Station stat : l){
							 for(int ident : stat.getLid()){
								 if(ident == liaison.getB()){
									 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
									 break;
								 }

							 }
							 
						 }
						 
					 }

						
				 }

				 
			 }
			 
		 }
		 
		 
		 //on supprime les doublons dans la liste des sommets
//		 Set<Station> mySet = new HashSet<Station>(l);
//		 ArrayList<Station> l2 = new ArrayList<Station>(mySet);

		 
//		 for(int i=0;i<l.size();i++){
//			 System.out.println(l.get(i));
//		 }
		 
		 
		 
//		 for(int i=0;i<l2.size();i++){
//			 System.out.println(l2.get(i));
//		 }

		 //on cr�� un graphe qui va regrouper tout les �l�ments

		 
//		 
		 
		 //on cr�� toutes les stations de la ligne 1
//		 for(int i=0;i<l.size();i++){
//			 g.addVertex((Station)l.get(i));
//		 }
		 

		 
		//on cr�� toutes les stations de la ligne 2
//		 for(int i=0;i<a2.size();i++){
//			 g.addVertex((Station)a2.get(i));
//		 }
		 

		 
//	        g.addEdge(new Liaison(l2.get(1),l2.get(2),"Ligne",12,12), l2.get(1), l2.get(2));
		 
//		 g.addVertex((Station)l2.get(1));

	        

			 
		 
		   //on cr�� toutes les liaisons de la ligne 1
		 
		 
		 
		 
		 
//			 for(int i=0;i<a1.size()-1;i++){
//				 g.addEdge(new Liaison(a1.get(i),a1.get(i+1),
//						" " + a1.get(i).getNom() + " --> " + a1.get(i+1).getNom() + " " ,12)
//						 ,a1.get(i),a1.get(i+1));
//				 g.addEdge(new Liaison(b1.get(i),b1.get(i+1),
//							" " + b1.get(i).getNom() + " --> " + b1.get(i+1).getNom() + " " ,12)
//							 ,b1.get(i),b1.get(i+1));
//
//			}
//			 
			 
//			 for(int i=0;i<a2.size()-1;i++){
//				 g.addEdge(new Liaison(a2.get(i),a2.get(i+1),
//						" " + a2.get(i).getNom() + " --> " + a2.get(i+1).getNom() + " " ,12)
//						 ,a2.get(i),a2.get(i+1));
//				 g.addEdge(new Liaison(b2.get(i),b2.get(i+1),
//							" " + b2.get(i).getNom() + " --> " + b2.get(i+1).getNom() + " " ,12)
//							 ,b2.get(i),b2.get(i+1));
//
//			}
			 

			//on cr�� toutes les liaisons de la ligne 2

			 
			 
			 
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
			 
			 
//		     System.out.println("The graph g = " + g.toString());
		     
//		     for(int i=0;i<l.size();i++){
//		     System.out.println("Station = " + l.get(i));
//		     }
		     
//		     for(int i=0;i<a.size();i++){
//		     System.out.println("Liaison = " + a.get(i));
//		     }
		     		     		 
//			 for(int i=0;i<=l2.size();i++){
//				 System.out.println(l2.get(i));
//			 }

		     

		     SimpleGraphDraw sgv = new SimpleGraphDraw(); //We create our graph in here
		     // The Layout<V, E> is parameterized by the vertex and edge types
		     
//		     InteractiveGraphView1 sgv = new InteractiveGraphView1(); 
		     
		     
//		     Transformer<Station,Point2D> vertexPaint = new Transformer<Station,Point2D>(g);
		     
//		     Layout<Station, Liaison> IterativeContext, Transformer<Station,Point2D>;
//		     Transformer<Station,Point2D> vertexPaint = new Transformer<Station,Point2D>();
//		     


		      
		      
		      Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {

		            @Override
		            public Point2D transform(Station vertex) {
		                return new Point2D.Double((double) vertex.getX()*2-100 ,
		                		(double) Math.abs(vertex.getY() - 600)*2+100 );
		            }
		        };
		      
		      StaticLayout<Station, Liaison> layout =
		    		  new StaticLayout<Station, Liaison>(g, locationTransformer);
			  layout.setSize(new Dimension(1000,1000));
//		        VisualizationViewer<Station,Liaison> vv = 
//		                new VisualizationViewer<Station,Liaison>(layout);
//			     
			// The BasicVisualizationServer<V,E> is parameterized by the edge types
//			     BasicVisualizationServer<Station,Liaison> vv = 
//			              new BasicVisualizationServer<Station,Liaison>(layout);
			     
			     
			        VisualizationViewer<Station,Liaison> vv = 
			                new VisualizationViewer<Station,Liaison>(layout);
			      vv.setPreferredSize(new Dimension(600,600)); //Sets the viewing area size
			      
			      
			      
			        // Show vertex and edge labels
			      
//			        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
			        
			        
			        vv.getRenderContext().setVertexLabelTransformer(new Transformer<Station, String>() {
			            public String transform(Station e) {
			                return (e.getNom());
			            }
			        });
			        
			        
			        
//			        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
			      
			      
			        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
			        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
			        vv.setGraphMouse(gm);
			      
		      JFrame frame = new JFrame("Simple Graph View");
		      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      frame.setSize(800, 800);
		      frame.isResizable();

		      frame.setLocationRelativeTo(null);
		      

		      
		      frame.getContentPane().add(vv); 
		      frame.pack();
//		      frame.setVisible(true);       
		      
		      DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath(g);
//		      ArrayList<Liaison> L = alg.getPath(l.get(1), l.get(2));
		      Number dist = alg.getDistance(l.get(1), l.get(2));
		      
//		      System.out.println(L);
		      System.out.println(dist);
		      
		      Dijkstra dijska = new Dijkstra(g);
		      
		      System.out.println(dijska);
		      
		      
//		     
		     
		 

	 }

}