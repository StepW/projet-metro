package trajetMetro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class Main3 {
 public static void main(String[] args) {
		 
		 //on cherche le fichier à chercher
		 String fichiernoms = "D:/JAVA/fichiers stations/nom station.txt";
		 String fichiersommets = "D:/JAVA/fichiers stations/coordonnee sommet.txt";
		 String fichierarcs = "D:/JAVA/fichiers stations/arc valeur temps.txt";

		 
		 File f1 = new File(fichiernoms);
		 File f2 = new File(fichiersommets);
		 File f3 = new File(fichierarcs);


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

				//inportation du fichier 1
				// #############################################################################	

				while ((ligne=br1.readLine())!=null){

					//le premier élément d'une ligne ne doit pas commencer par ####
					
//					if(!ligne.startsWith("####")){
//						
//						//on extrait les données d'une ligne en séparant les identifiants et le nom des stations

						String[] st = ligne.split(" ", 2);
						
						//verificateur

//							
//						//on remplit la liste de sommets avec les objets stations
						//on créé les variables pour les tests
						
						String nomStation = st[1].trim().toLowerCase();
						int idStation = Integer.parseInt(st[0]);


						//on créé une station test
						
						Station v = null;
						
						l.add(new Station(idStation,nomStation,0,0,Color.red));
						
//						
						
						//on parcours la liste des stations en train de se remplir
						
						//on ajoute une condition pour vérifier si dans cette liste il y a des stations en double
						//si oui, on incrémente la station en double dans la station test
						//et on sort de cette boucle
						
						
//						for(Station station : l){
//							if(station.getNom().equals(nomStation)){
//								v = station;	
//								break;
//							}
//						}
						
						//on vérifie maintenant si la condition précédente est remplie
						//si oui, on ajoute la station dans la liste
						//si non, on ajoute l'identifiant dans la liste de l'objet station
//						if(v == null){
//							v = new Station(new ArrayList<Integer>(), idStation,nomStation,Color.red);
//							l.add(v);
//						}
//						else{
//							v.getLid().add(idStation);
//						}


					}

				
				br1.close();

				//inportation du fichier 2
				// #############################################################################				
				
				while ((ligne=br2.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					//on intègre les éléments de la liste dans des variables pour le test suivant
					int idStation = Integer.parseInt(st[0]);
					int coordX = Integer.parseInt(st[1]);
					int coordY = Integer.parseInt(st[2]);
					
					
					//on parcours la liste et on vérifie dans la liste identifiants de la station
					//lorsqu'un identifiant est reconnu, on attribut les coordonnées à la station auquelle elle appartient
					
					
//					for(Station station : l){	
//						for(int z : station.getLid()){
//						
//							if(idStation == z){
//								station.setX(coordX);
//								station.setY(coordY);
//								}
//						}
						
						for(Station station : l){	
							
							
								if(idStation == station.getId()){
									station.setX(coordX);
									station.setY(coordY);
									}
							
						
						
//						System.out.println(idStation+" == "+station.getId());
						
						}

				
					}
				
				br2.close();
				
				//inportation du fichier 1
				// #############################################################################
				
				while ((ligne=br3.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					//on intègre les éléments de la liste dans des variables pour le test suivant
					int sI = Integer.parseInt(st[0]);
					int sF = Integer.parseInt(st[1]);
					double tempsD = Double.parseDouble(st[2]);
					int temps = (int) tempsD;
					

					
					//dans la première boucle for on parcourt la liste l qui contient toutes les stations
					//dans la deuxième boucle for on parcourt la liste des identifiants d'une station
					
					//à ce moment on vérifie le numéro contenu dans la station correspondant au sommet initiale
//					for(Station station : l){
//						for(int id : station.getLid()){
//							if(id == sI){
//								
//								
//								//quand la condition précédente est remplie, on fait la même opération
//								//avec le sommet final
//								
//								for(Station station2 : l){
//									for(int id2 : station2.getLid()){
//										if(id2 == sF){
//											a.add(new Liaison(sI,sF,temps,Color.black,false));
//											
//											
//										
//										}
//									}
//								}
//							}
//						}
//					}
					
					for(Station station : l){

							if(station.getId() == sI){
								
								
								//quand la condition précédente est remplie, on fait la même opération
								//avec le sommet final
								
								for(Station station2 : l){

										if(station2.getId() == sF){
//											System.out.println("coucou");
											a.add(new Liaison(sI,sF,temps,Color.black,false));
											
											
										
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
//		 Station b = new Station(new ArrayList<Integer>(),555,"fff",Color.RED);
//		 System.out.println(b);
//		 b.addId(131);
//		 System.out.println(b);
//		 //2 eme element dans la liste des id
//		 System.out.println(b.getLid().get(1));
//		 //nom
//		 System.out.println(b.getNom());
//		 
//		 System.out.println();
//		 
//		 System.out.println();
//		 
//		 System.out.println();

		 // #############################################################################
		 
	 	//on créé un graphe qui va regrouper tout les éléments
		 Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
		 
		 
		 
		 //dans ce graphe on ajoute les sommets en parcourant la liste des stations
		 for(Station station : l){
			 g.addVertex((Station)station);
		 }
		 
		 
		 for(Station station : l){
			 System.out.println(station);
		 }
		 
//		 for(Liaison liaison : a)
//			 System.out.println(liaison);
		 
		 
		 //on ajoute ensuite les arcs à partir de la liste des liaisons
		 //on créé une boucle qui va parcourir cette liste
		 //en lisant une liaison, on va parcourir la liste des station et dans une station...
		 //...on va parcourir les identifiants de cette station qui correspond à un sommet du graphe
		 
		 
//		 for(Liaison liaison : a){
//			 for(Station station : l){
//				 for(int identifiant : station.getLid()){
//					 
//					 //on cherche l'identifiant qui correspond au sommet initial de la liaison
//					 if(identifiant == liaison.getA()){
//						 
//						//quand l'identifiant est trouvé, on fait la même opération pour le sommet final
//						 for(Station stat : l){
//							 for(int ident : stat.getLid()){
//								 if(ident == liaison.getB()){
//									 
//						//une fois le sommet initial et final, on va pouvoir ajouter un arc dans le graphe
//						//on recommence jusqu'à ce que toute la liste des liaisons soit parcourue
//									 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
//									 break;
//								 }
//
//							 }
//							 
//						 }
//						 
//					 }
//
//						
//				 }
//
//				 
//			 }
//			 
//		 }
		 
		 for(Liaison liaison : a){
			 for(Station station : l){

					 
					 //on cherche l'identifiant qui correspond au sommet initial de la liaison
					 if(station.getId() == liaison.getA()){
						 
						//quand l'identifiant est trouvé, on fait la même opération pour le sommet final
						 for(Station stat : l){

								 if(stat.getId() == liaison.getB()){
									 
						//une fois le sommet initial et final, on va pouvoir ajouter un arc dans le graphe
						//on recommence jusqu'à ce que toute la liste des liaisons soit parcourue
									 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
									 break;
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


		    // nous allons maintenant générer une interface pour visualiser le graphe
		 	// ce graphe correspond à un plan géométrique des liaisons et des stations de métro
		 
		 //à l'aide de la fonction Transformer, nous voulons placer les sommets
		 //en fonction des coordonnées X et Y de de chaque station à partir d'objets Point 2D
		 	Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {

		            @Override
		            public Point2D transform(Station vertex) {
		                return new Point2D.Double((double) vertex.getX()*2-100 ,
		                		(double) Math.abs(vertex.getY() - 600)*2+100 );
		            }
		        };
		      
		        
		        //nous voulons faire une mise en forme du graphe
		      StaticLayout<Station, Liaison> layout =
		    		  new StaticLayout<Station, Liaison>(g, locationTransformer);
			  layout.setSize(new Dimension(1000,1000));
			  
			  
			     
			        VisualizationViewer<Station,Liaison> vv = 
			                new VisualizationViewer<Station,Liaison>(layout);
			      vv.setPreferredSize(new Dimension(600,600)); //Sets the viewing area size
			      
			      
			      
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
			      

				      JFrame frame = new JFrame("Simple Graph View");
				      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				      frame.setSize(800, 800);
				      frame.isResizable();

				      frame.setLocationRelativeTo(null);
				      
			    	  
				      frame.getContentPane().add(vv); 
//				      frame.getContentPane().add(vv2); 
				      frame.pack();
				      frame.setVisible(true);      
		      
				      Transformer<Liaison, Integer> wtTransformer = new Transformer<Liaison,Integer>() {
				          public Integer transform(Liaison link) {
				        	  if(link.isCorresp() == true)
				        		  return /*link.getTemps() + 240*/ 1000000;
				        	  else
				        		  return link.getTemps();
				          }
				      };
		      
		      //on utilise un objet Djikstra pour calculer l'itinéraire le plus court entre deux stations 
		      DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
		      
		      //le scanner est utilisé pour écrire une chaine de caractères dans la console
		      Scanner sc = new Scanner(System.in);
		      
//		      System.out.println("Départ : ");
//		      int i = sc.nextInt();
//		      System.out.println("Arrivée : ");
//		      int j = sc.nextInt();
		      
		      
		      List<Station> L = new ArrayList<Station>();
		      List<Liaison> A = new ArrayList<Liaison>();
		      
		      
		      while(A.isEmpty() && L.isEmpty()){
		    	  
//		      System.out.println("Départ : ");
//		      String m = sc.nextLine().trim();
//		      System.out.println("Arrivée : ");
//		      String n = sc.nextLine().trim();
		      
		      System.out.println("Départ : ");
		      int M = sc.nextInt();
		      System.out.println("Arrivée : ");
		      int N = sc.nextInt();
		      
		      
		      
		      
		      int k = 0;
		      int c = 0;
		      

//		      for(Station station : l){
//		    	  for(int id : station.getLid()){
//		    		  if(id == i){
//		    			  k = l.indexOf(station);
//		    		  }
//		    		  if(id == j){
//		    			  c = l.indexOf(station);
//		    		  }
//		    	  }
//		      }
		      
		      		      
		      //String
//		      for(Station station : l){
//			      if(m.equals(station.getNom())){
//			    	  k = l.indexOf(station);
//
//			    	  }
//			      if(n.equals(station.getNom())){
//			    	  c = l.indexOf(station);
//
//			    	  }
//		      }
		      
		      
		      
		      for(Station station : l){
			      if(M == station.getId()){
		    	  k = l.indexOf(station);

		    	  }
		      if(N == station.getId()){
		    	  c = l.indexOf(station);

		    	  }
		      }
		      
		      
		      
//		      List<Liaison> L = alg.getPath(l.get(k), l.get(c));
		      
		      System.out.println(l.get(k).getNom()+" : "+l.get(k).getId());
		      System.out.println(l.get(c).getNom()+" : "+l.get(c).getId());
		      
		      
//		      int gg = 0;
//		      
//		      while(gg <= 10){
			      A = null;
		      
		      A = alg.getPath(l.get(k), l.get(c));

		      
		      for(Liaison ligne : A){
			      System.out.println(ligne);
			      }
		      
		      System.out.println();
		      
		      
//		      System.out.println("AVANT INSERSION TRUE");
//		      for(Liaison ligne : A){
//			      System.out.println(ligne);
//			      }
//		      System.out.println();
//		      
////
//		      for(int i = 0; i < A.size()-1;i++){
//		    	  if(A.get(i).getB() != A.get(i+1).getA()){
//		    		  A.get(i+1).setCorresp(true);;
//		    	  }
//		      }


//		      

			      
//			       wtTransformer = new Transformer<Liaison,Integer>() {
//			          public Integer transform(Liaison link) {
//			        	  if(link.isCorresp() == true)
//			        		  return link.getTemps() + 240 /*1000000*/;
//			        	  else
//			        		  return link.getTemps();
//			          }
//			      };
//			      
//			      alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
	
			      
			     
			      A = alg.getPath(l.get(k), l.get(c));
			      
			      
			      
			      System.out.println("APRES:");
			      for(Liaison ligne : A){
	
				      System.out.println(ligne);
				      }
			      
//			      gg += 1;
//		      }
		      
		      
		      
//		      
		      for(Liaison liaison : A){
		    	  liaison.setC(Color.red);
		      }
		      
		      
		      Number dist = alg.getDistance(l.get(k), l.get(c));
		      System.out.println(dist);
		      
		      
		      
		      

		      
		      Station v = null;
		      
		      ///
		      for(Liaison ligne : A){
			      for(Station station : l){
			    	  
//			    	  System.out.println(ligne.getA());
//			    	  System.out.println(station.getId());
			    	  
			    		  if(ligne.getB() == station.getId()){
			    			  v = station;
			    			  System.out.println(v);
			    			  break;

			    		  }
			    		  
//			    		  if(ligne.getB() == station.getId()){
//			    			  v = station;
//			    			  System.out.println(v);
//			    			  break;
//
//			    		  }
			    		 
			    	  }
			      
			    	  if(!L.contains(v) && v != null){
			    		  v.setC(Color.blue);
			    		  L.add(v);
			    		  
			    	  }
			    	  
			    	  
			      	}
		      
		      

    		  L.get(0).setC(Color.blue);

    		  L.add(l.get(0));



			      
		      
		      
		      ///
		      
		      System.out.println();
		      
	    	  System.out.println("Liaisons de l'itinéraire : ");
	    	  
		      for(Liaison ligne : A){

		      System.out.println(ligne);
		      }
		      
		      System.out.println();
	    	  System.out.println("Stations de l'itinéraire : ");  
		      for(Station station : L){

			      System.out.println(station);
			      }
		      
		      
		     System.out.println(dist);
		      

		      vv.repaint();

		      
		      System.out.println("Reset ?");
		      String re = sc.nextLine().trim();
		      
		      
		      if(re.equals("reset")){
		    	  
		    	  
		    	  for(Liaison liaison : A){
			    	  liaison.setC(Color.black);
			      }

		    	  for(Station station : L){
			    	  station.setC(Color.red);
			      }
		    	  
		    	  
		    	  A.clear();
		    	  L.clear();
		      }
		      }
		      // #######################################################################
		      
 }

	 }



