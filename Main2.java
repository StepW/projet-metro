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
		 
		 //on cherche le fichier à chercher
		 String fichiernoms = "D:/JAVA/fichiers stations/nom station.txt";
		 String fichiersommets = "D:/JAVA/fichiers stations/coordonnee sommet.txt";
		 String fichierarcs = "D:/JAVA/fichiers stations/arc valeur temps.txt";

		 File f1 = new File(fichiernoms);
		 File f2 = new File(fichiersommets);
		 File f3 = new File(fichierarcs);
		 Scanner in = null;

		 //on créé la liste des stations pour les sommets du graphe
		ArrayList<Station> l = new ArrayList<Station>();
		
		
		//on créé la liste des liaisons pour les arcs du graphe
		ArrayList<Liaison> a = new ArrayList<Liaison>();
		


		
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
						
						
						//on parcours la liste des stations en train de se remplir
						
						//on ajoute une condition pour vérifier si dans cette liste il y a des stations en double
						//si oui, on incrémente la station en double dans la station test
						//et on sort de cette boucle
						for(Station station : l){
							if(station.getNom().equals(nomStation)){
								v = station;	
								break;
							}
						}
						
						//on vérifie maintenant si la condition précédente est remplie
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
					
					//on intègre les éléments de la liste dans des variables
					int idStation = Integer.parseInt(st[0]);
					int coordX = Integer.parseInt(st[1]);
					int coordY = Integer.parseInt(st[2]);
					
					
					//on parcours la liste et on vérifie dans la liste identifiants de la station
					//lorsqu'un identifiant est reconnu, on attribut les coordonnées à la station auquelle elle appartient
					
					
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
		 
	 	//on créé un graphe qui va regrouper tout les éléments
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

		 

		 //on créé un graphe qui va regrouper tout les éléments

		 
//		 
		 

		     

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
	 
			        //graphique
			        
			        Transformer<Liaison,Paint> strokePaint = new Transformer<Liaison,Paint>() {
			        	 public Paint transform(Liaison i) {
			        	 return Color.RED;
			        	 }
			        	 }; 
				      vv.getRenderContext().setEdgeDrawPaintTransformer(strokePaint);
				      vv.getRenderContext().setArrowFillPaintTransformer(strokePaint);
				      vv.getRenderContext().setArrowDrawPaintTransformer(strokePaint);
				      
				      Transformer<Station,Paint> vertexPaint = new Transformer<Station,Paint>() {
				    	  public Paint transform(Station e) {
				    	  return Color.BLUE;
				    	  }
				    	  }; 
				    	  
				    	  vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);			        
			        
			        
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
		      
		     	DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
		      
		      
		      Scanner sc = new Scanner(System.in);
		      
		      System.out.println("Départ : ");
		      int i = sc.nextInt();
		      System.out.println("Arrivée : ");
		      int j = sc.nextInt();
		      
		      
		      int b = 0;
		      int c = 0;
		      
		      for(Station station : l){
		    	  for(int id : station.getLid()){
		    		  if(id == i){
		    			  b = l.indexOf(station);
		    		  }
		    		  if(id == j){
		    			  c = l.indexOf(station);
		    		  }
		    	  }
		      }
		      
		      List<Liaison> L = alg.getPath(l.get(b), l.get(c));
		      
		      Number dist = alg.getDistance(l.get(b), l.get(c));
		      
		      System.out.println(L);
		      System.out.println(dist);
		      

	 }

}
