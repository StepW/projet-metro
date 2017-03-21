package trajetMetro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class Main1 extends JFrame implements ActionListener{
	
	
	// constante import�
	
	static Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {
			
			            @Override
			            public Point2D transform(Station vertex) {
			                return new Point2D.Double((double) vertex.getX()*2-100 ,
			                		(double) Math.abs(vertex.getY() - 600)*2+100 );
			            }
			        };
	
		static Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();	        
		
		static StaticLayout<Station, Liaison> layout =
	    		  new StaticLayout<Station, Liaison>(g, locationTransformer);
        
        static VisualizationViewer<Station,Liaison> vv = 
                new VisualizationViewer<Station,Liaison>(layout);
        
        static Transformer<Liaison, Integer> wtTransformer = new Transformer<Liaison,Integer>() {
	          public Integer transform(Liaison link) {
	              return link.getTemps();
	          }
	      };
	      
        static DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
       	        
	static ArrayList<Station> l = new ArrayList<Station>();
	
	// constante import�
	
	
 public static void main(String[] args) {
		 
		 //on cherche le fichier � chercher
		 String fichiernoms = "D:/Projet_Java/nom station.txt";
		 String fichiersommets = "D:/Projet_Java/coordonnee sommet.txt";
		 String fichierarcs = "D:/Projet_Java/arc valeur temps.txt";
		
		 
		 File f1 = new File(fichiernoms);
		 File f2 = new File(fichiersommets);
		 File f3 = new File(fichierarcs);

		 //on cr�� la liste des stations pour les sommets du graphe
//		ArrayList<Station> l = new ArrayList<Station>();
		
		
		//on cr�� la liste des liaisons pour les arcs du graphe
		ArrayList<Liaison> a = new ArrayList<Liaison>();
		
//		Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {
//
//            @Override
//            public Point2D transform(Station vertex) {
//                return new Point2D.Double((double) vertex.getX()*2-100 ,
//                		(double) Math.abs(vertex.getY() - 600)*2+100 );
//            }
//        };
//        
//        Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();	        
//		
//		StaticLayout<Station, Liaison> layout =
//	    		  new StaticLayout<Station, Liaison>(g, locationTransformer);
//        
//        VisualizationViewer<Station,Liaison> vv = 
//                new VisualizationViewer<Station,Liaison>(layout);
//        
      
		
			
			        
		
		
		
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

				//inportation du fichier 1
				// #############################################################################	

				while ((ligne=br1.readLine())!=null){

					//le premier �l�ment d'une ligne ne doit pas commencer par ####
					
//					if(!ligne.startsWith("####")){
//						
//						//on extrait les donn�es d'une ligne en s�parant les identifiants et le nom des stations

						String[] st = ligne.split(" ", 2);
						
						//verificateur

//							
//						//on remplit la liste de sommets avec les objets stations
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
							v = new Station(new ArrayList<Integer>(), idStation,nomStation,Color.red);
							l.add(v);
						}
						else{
							v.getLid().add(idStation);
						}


					}

				
				br1.close();
				
				//inportation du fichier 2
				// #############################################################################				
				
				while ((ligne=br2.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					//on int�gre les �l�ments de la liste dans des variables pour le test suivant
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
				
				//inportation du fichier 1
				// #############################################################################
				
				while ((ligne=br3.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					//on int�gre les �l�ments de la liste dans des variables pour le test suivant
					int sI = Integer.parseInt(st[0]);
					int sF = Integer.parseInt(st[1]);
					int temps = Integer.parseInt(st[2]);
					
					//dans la premi�re boucle for on parcourt la liste l qui contient toutes les stations
					//dans la deuxi�me boucle for on parcourt la liste des identifiants d'une station
					
					//� ce moment on v�rifie le num�ro contenu dans la station correspondant au sommet initiale
					for(Station station : l){
						for(int id : station.getLid()){
							if(id == sI){
								
								
								//quand la condition pr�c�dente est remplie, on fait la m�me op�ration
								//avec le sommet final
								
								for(Station station2 : l){
									for(int id2 : station2.getLid()){
										if(id2 == sF){
											a.add(new Liaison(sI,sF,temps,Color.black,false));
											
											
										
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
		 
		 //zone de test
		 // #############################################################################
		 Station b = new Station(new ArrayList<Integer>(),555,"fff",Color.RED);
		// System.out.println(b);
		 b.addId(131);
		 //System.out.println(b);
		 //2 eme element dans la liste des id
		// System.out.println(b.getLid().get(1));
		 //nom
		// System.out.println(b.getNom());
		 
		// System.out.println();
		 
		// System.out.println();
		 
		// System.out.println();

		 // #############################################################################
		 
	 	//on cr�� un graphe qui va regrouper tout les �l�ments
//		 Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
		 
		 
		 
		 //dans ce graphe on ajoute les sommets en parcourant la liste des stations
		 for(Station station : l){
			 g.addVertex((Station)station);
		 }
		 
		 
		 for(Station station : l){
		//	 System.out.println(station);
		 }
		 
//		 for(Liaison liaison : a)
//			 System.out.println(liaison);
//		 
		 
		 //on ajoute ensuite les arcs � partir de la liste des liaisons
		 //on cr�� une boucle qui va parcourir cette liste
		 //en lisant une liaison, on va parcourir la liste des station et dans une station...
		 //...on va parcourir les identifiants de cette station qui correspond � un sommet du graphe
		 for(Liaison liaison : a){
			 for(Station station : l){
				 for(int identifiant : station.getLid()){
					 
					 //on cherche l'identifiant qui correspond au sommet initial de la liaison
					 if(identifiant == liaison.getA()){
						 
						//quand l'identifiant est trouv�, on fait la m�me op�ration pour le sommet final
						 for(Station stat : l){
							 for(int ident : stat.getLid()){
								 if(ident == liaison.getB()){
									 
						//une fois le sommet initial et final, on va pouvoir ajouter un arc dans le graphe
						//on recommence jusqu'� ce que toute la liste des liaisons soit parcourue
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

		 

		 //on cr�� un graphe qui va regrouper tout les �l�ments

		 
//		 


		    // nous allons maintenant g�n�rer une interface pour visualiser le graphe
		 	// ce graphe correspond � un plan g�om�trique des liaisons et des stations de m�tro
		 
		 //� l'aide de la fonction Transformer, nous voulons placer les sommets
		 //en fonction des coordonn�es X et Y de de chaque station � partir d'objets Point 2D
//		 	Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {
//
//		            @Override
//		            public Point2D transform(Station vertex) {
//		                return new Point2D.Double((double) vertex.getX()*2-100 ,
//		                		(double) Math.abs(vertex.getY() - 600)*2+100 );
//		            }
//		        };
//		      
		        
		        //nous voulons faire une mise en forme du graphe
//		      StaticLayout<Station, Liaison> layout =
//		    		  new StaticLayout<Station, Liaison>(g, locationTransformer);
			  layout.setSize(new Dimension(1000,1000));
			  
			  
//			     
//			        VisualizationViewer<Station,Liaison> vv = 
//			                new VisualizationViewer<Station,Liaison>(layout);
			      vv.setPreferredSize(new Dimension(800,800)); //Sets the viewing area size
			      
			      
			      
			        // Show vertex and edge labels
			      
//			        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
			        
			      
			        //nous voulons afficher le nom des stations dans l'interface graphique
			        vv.getRenderContext().setVertexLabelTransformer(new Transformer<Station, String>() {
			            public String transform(Station e) {
			                return (e.getNom());
			            }
			        });
	 
			        //graphique
				     // #####################################################################
			        
			        //
			        Transformer<Liaison,Paint> strokePaint = new Transformer<Liaison,Paint>() {
			        	 public Paint transform(Liaison i) {
			        	 return i.getC();
			        	 }
			        	 };
			        	 
				      vv.getRenderContext().setEdgeDrawPaintTransformer(strokePaint);
				      vv.getRenderContext().setArrowFillPaintTransformer(strokePaint);
				      vv.getRenderContext().setArrowDrawPaintTransformer(strokePaint);
				     
				      
				      Transformer<Station,Paint> vertexPaint = new Transformer<Station,Paint>() {
				    	  public Paint transform(Station e) {
				    	  return e.getC();
				    	  }
				    	  }; 
				    	  
				    	  
				    	  
				    	  vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);			        
			        
				     // #####################################################################
			        
//			        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
			        
			      
			      //on utilise une fonction pour pouvoir zoomer sur le graphe
			        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
			        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
			        vv.setGraphMouse(gm);
			      

				     // JFrame frame = new JFrame("Simple Graph View");
				     // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				     // frame.setSize(1000, 1000);
				     // frame.isResizable();

//				      frame.setLocationRelativeTo(null);
				      
			    	//  
				   //   frame.getContentPane().add(vv); 
				     // frame.getContentPane().add(container); 
//				      frame.getContentPane().add(vv2); 
				     // frame.pack();
				    //  frame.setVisible(true); 
				      
				     
					   
					    //fin creation de new fenetre
				     
		      
				      Transformer<Liaison, Integer> wtTransformer = new Transformer<Liaison,Integer>() {
				          public Integer transform(Liaison link) {
				              return link.getTemps();
				          }
				      };
				      
		      //on utilise un objet Djikstra pour calculer l'itin�raire le plus court entre deux stations 
		      //DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);

		     
		      // #######################################################################
		      Fin P = new Fin();

		      
		      P.setVisible(true);    
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


       
}
