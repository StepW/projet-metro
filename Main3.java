package trajetMetro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Point2D;

import java.io.BufferedReader;
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

 		//on créé la liste des stations pour les sommets du graphe
		ArrayList<Station> l = new ArrayList<Station>();
		
		//on créé la liste des liaisons pour les arcs du graphe
		ArrayList<Liaison> a = new ArrayList<Liaison>();
		


		//-----------------------------------------------------------------------------
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
				//-----------------------------------------------------------------------------

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



						
						l.add(new Station(idStation,nomStation,0,0,Color.red,false));
						
					}
				
				br1.close();

				//inportation du fichier 2
				//-----------------------------------------------------------------------------		
				
				while ((ligne=br2.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					//on intègre les éléments de la liste dans des variables pour le test suivant
					int idStation = Integer.parseInt(st[0]);
					int coordX = Integer.parseInt(st[1]);
					int coordY = Integer.parseInt(st[2]);
					
					
					//on parcours la liste et on vérifie dans la liste identifiants de la station
					//lorsqu'un identifiant est reconnu, on attribut les coordonnées à la station auquelle elle appartient
					

						for(Station station : l){	
							
							
								if(idStation == station.getId()){
									station.setX(coordX);
									station.setY(coordY);
									}
							
						}

				
					}
				
				br2.close();
				
				//inportation du fichier 3
				//-----------------------------------------------------------------------------
				
				while ((ligne=br3.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					//on intègre les éléments de la liste dans des variables pour le test suivant
					int sI = Integer.parseInt(st[0]);
					int sF = Integer.parseInt(st[1]);
					double tempsDouble = Double.parseDouble(st[2]);
					int temps = (int) tempsDouble;
					
					//on commence par vérifier le sommet initiale de l'arc
					//pour cela on cherche l'identifiant dans la liste des stations
					for(Station station : l){
						if(station.getId() == sI){
							
							//quand la condition précédente est remplie, on fait la même opération
							//avec le sommet final
							
								for(Station station2 : l){
										if(station2.getId() == sF){
											
											/*quand les deux stations sont trouvées, on cherche ensuite des arcs
											 *qui sont des correspondances on sait les reconaitre lorsque le temps
											 *est égal à la valeur double 120.0 
											 */
											if(tempsDouble == 120.0){
												a.add(new Liaison(sI,sF,temps,Color.black,true));
											}
											else {
												a.add(new Liaison(sI,sF,temps,Color.black,false));
												
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
		 
			
	 	//on créé un graphe qui va regrouper tout les éléments
		 Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
		 
		 
		 
		 //dans ce graphe on ajoute les sommets qui correspondent aux stations.
		 for(Station station : l){
			 g.addVertex((Station)station);
		 }
		 
		 
		 

		 
		 
		 /*on ajoute ensuite les arcs à partir de la liste des liaisons
		 * pour cela créé une boucle qui va parcourir cette liste en lisant l'identifiant du sommet entrant
		 * 
		 * en même temps on parcourt la liste des station et on vérifie d'identifiant qu'on associe
		 * au sommet initiale de la liaison
		 * 
		 * on va parcourir les identifiants de cette station qui correspond à un sommet du graphe
		 */

		 
		 for(Liaison liaison : a){
			 for(Station station : l){

				 /* en même temps on parcourt la liste des station et on vérifie d'identifiant qu'on associe
				 *au sommet initiale de la liaison
				 */
					 if(station.getId() == liaison.getA()){
						 
						//quand l'identifiant est trouvé, on fait la même opération pour le sommet final
						 for(Station stat : l){

								 if(stat.getId() == liaison.getB()){
									 
						//une fois le sommet initial et final, on va pouvoir ajouter un arc dans le graphe
						//on recommence jusqu'à ce que toute la liste des liaisons soit parcourue
									 int d = liaison.Distance(station.getX(),station.getY(),stat.getX(),stat.getY());
									 liaison.setDistance(d);
									 
									 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
									 break;
								 }

							 }
							 
						 }
						 
					 }

						
				 

				 
			 
			 
		 }
		 
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
			  
			  
			     //puis nous insérons un module de visualisation du graphe
			        VisualizationViewer<Station,Liaison> vv = 
			                new VisualizationViewer<Station,Liaison>(layout);
			      vv.setPreferredSize(new Dimension(600,600)); 
			      
			      
			      

			        //nous voulons afficher le nom des stations ansi que leur identifiant dans l'interface graphique
			        vv.getRenderContext().setVertexLabelTransformer(new Transformer<Station, String>() {
			            public String transform(Station e) {
			                return (e.getNom() + " : " + e.getId());
			            }
			        });
	 
			        //graphique
			        
				     //-------------------------------------------------------------------------------
			        
			        
			        //lorsqu'un itinéraire est trouvé, on veut le colorier
			        //on change alors la couleur des sommets et des arcs qui correspondent
			        //à cet itinéraire
			        
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
			        
				     //------------------------------------------------------------

			      //on utilise une fonction pour pouvoir zoomer sur le graphe	  
			        DefaultModalGraphMouse<Station, Liaison> gm = new DefaultModalGraphMouse<Station, Liaison>();
			        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
			        vv.setGraphMouse(gm);
			      

			        //on veut afficher le graphe dans une fenêtre
			        JFrame frame = new JFrame("Itinéraire Métro Paris");
				      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				      frame.setSize(800, 800);
				      frame.isResizable();
				      frame.setLocationRelativeTo(null);
				      frame.getContentPane().add(vv); 
				      frame.pack();
				      frame.setVisible(true);      
				      
				      Transformer<Liaison, Integer> wtTransformer = new Transformer<Liaison,Integer>() {
				          public Integer transform(Liaison link) {
				        		  return link.getTemps();
				          }
				      };
		      
		      //on utilise un objet Djikstra pour calculer l'itinéraire le plus court entre deux stations 
		      DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
		      
		      //le scanner est utilisé pour écrire une chaine de caractères dans la console
		      
		      //pour une chaine de caractère
		      Scanner sc = new Scanner(System.in);
		      
		      //pour un entier
		      Scanner jk = new Scanner(System.in);
		      
		      
		      //on créé deux listes qui correspondent respectivenet à la liste des stations
		      //et à la liste des liaisons de l'itinéraire
		      List<Station> L = new ArrayList<Station>();
		      List<Liaison> A = new ArrayList<Liaison>();
		      
		      
		      //pour obtenir un itinéraire, on doit taper la station de début et la station de fin
		      
		      while(A.isEmpty() && L.isEmpty()){
		    
		    //nous pouvons taper la station de début et la station de fin
		      System.out.println("Départ : ");
		      String m = sc.nextLine().trim();
		      System.out.println("Arrivée : ");
		      String n = sc.nextLine().trim();     
		      
		      //nous devons aussi faire une liste des stations qui sont fermées,
		      //on tape l'identifiant de la station
		      //les stations fermées influent sur l'itinéraire à tracer
		      //le métro ne s'arrete pas au niveau des stations fermées
		      //on ne peut pas effectuer de correspondance à partir de ces dernières
		      
		      System.out.println("Station fermées : ");
		      String f = null;
		      
		      //on créé une liste de stations fermées qui servira plus tard
		      List<Station> F = new ArrayList<Station>();
		      
		      

		      
		      while(true){
		    	  
		    	  //on écrit "stop" pour demander d'arreter de remplir la liste
		    	  f = jk.nextLine().trim();
		    	  if(f.equals("stop")){
		    		  break;
		    		  
		    	  }
		    	  
		    	  //la variable ne doit pas être vide on doit obligatoirement rentrer soit un numéro d'une station
		    	  //soit le mot "stop" 
		    	  if(f.equals("")){
		    		  f = jk.nextLine().trim();
		    	  }
		    	  
		    	  //on remplis la liste des stations fermées et on change la valeur du booléen "ferme"
		    	  for(Station station : l){
		    		  if(Integer.parseInt(f) == station.getId()){
		    			  F.add(station);
		    			  station.setFerme(true);
		    		  }
		    	  }  
		    	  
//		    	  System.out.println(f);
		    	  
		      }
		      
		      System.out.println("------------------------------------");

		      
		      
		      //on modifie les données de la station lorsqu'elle est fermée
		      //si les liaisons autour sont des correspondances, on ne doit pas les emprunter
		      //on leur donne une priorité minimale
		    	  for(Station station : l){
		    		  if(station.isFerme() == true){
			    		  for(Liaison liaison : a){
			    			 
			    			  if(station.getId() == liaison.getB()
			    					  && liaison.isCorresp() == true){
			    				  liaison.setTemps(100000);
			    			  }
			    			  
			    			  if(station.getId() == liaison.getA()	    					  
			    					  && liaison.isCorresp() == true){
			    				  liaison.setTemps(100000);
			    			  }
			    		  }
		    		  }
		    		  
		      }	    	  
		    	  

		      int k = 0;
		      int c = 0;
		      
		      for(Station station : l){
			      if(m.equals(station.getNom())){
			    	  k = l.indexOf(station);

			    	  }
			      if(n.equals(station.getNom())){
			    	  c = l.indexOf(station);

			    	  }
		      }
		      
		      A = alg.getPath(l.get(k), l.get(c));
		      
		      //lorsque l'itinéraire est calculé, la station de départ est définit en fonction du nom rentré
		      //et non de l'identifiant, un nom de station a un identifiant fixe
		      //si la première liaison est une correspondance, on l'enlève de la liste

		      if(A.get(0).isCorresp() == true){
		    	  A.remove(0);  
		      }
		      
		      //on enlève aussi la dernière liaison si c'est une correspondance
		      if(A.get(A.size()-1).isCorresp() == true){
		    	  A.remove(A.size()-1);  
		      }

		      for(Liaison liaison : A){
		    	  liaison.setC(Color.red);
		      }


		      Station v = null;

		      //à l'aide de l'objet de transformation plus haut, on colorie les stations
		      //qui font partie de l'itinéraire
		      
		    //on colorie les liaisons appartenant à la liaison en rouge, les stations de l'itinéraire
		    //en bleu et les stations fermées en gris
		      
		      //on colorie d'abort la première station de l'itinéraire...
		      for(Station station : l){
		    	  if(station.getId() == A.get(0).getA()){
		    		  L.add(station);
		    		  L.get(0).setC(Color.blue);
		    	  }
		      }


		      //...puis toutes les autres
		      for(Liaison ligne : A){
			      for(Station station : l){
			    		  if(ligne.getB() == station.getId()){
			    			  v = station;
			    			  break;

			    		  }

			    	  }
			      
			      
			    	  if(!L.contains(v) && v != null){
			    		  v.setC(Color.blue);
			    		  L.add(v);
			    		  
			    	  }
			    	  
			    	  for(Station station : F){
			    		  station.setC(Color.gray);
			    	  }
			    	  
			    	  
			  }

		      
		      System.out.println();
		      
		      //on affiche la liste des liaisons
	    	  System.out.println("Liaisons de l'itinéraire : ");
	    	  
	    	  
		      for(Liaison ligne : A)
		      System.out.println(ligne);
		      
		      
		      System.out.println();
		      
		      //on affiche la liste des itinéraires
	    	  System.out.println("Stations de l'itinéraire : ");  
		      for(Station station : L)
			      System.out.println(station);
			      
		      //à l'aide de la transformation du poids des arc plus haut, on calcule le temps
		      //passé dans les liaisons
		      Number dist = alg.getDistance(L.get(0), L.get(L.size()-1));
		      
		      // le temps d'arrêt  à une station= 20 sec
		      //on ne compte pas la station de départ et d'arrivée, ni les stations fermées
		      double arret = 0;
		      for(Station station : L){
		    	  if(station.isFerme() == false)
		    	  arret += 20;
		      }
		      if(arret <= 0)
		      arret = 0;

		      System.out.println();
		      System.out.println("-----------------------");
		      System.out.println();
		      
		      //on fait la conversion du temps en minutes
		      //on prend en compte le temps de trajet dans une liaison + le temps d'arret à une stations
		      double tempsM = (double) dist + arret;
		      tempsM *= 0.0166667;
		      
		      
		      System.out.println("## Station de départ :");
		      System.out.println(L.get(0).getNom());
		      System.out.println("## Station d'arrivée :");
		      System.out.println(L.get(L.size()-1).getNom());
		      System.out.println("## temps de trajet estimé :");
		      System.out.println(Math.round(tempsM) + " minutes");
		      System.out.println("## distance à parcourir estimée :");
		      
		      //les distances sont calculées à partir des coordonnées des stations
		      //elles sont approximatives
		      int d = 0;
		      for(Liaison liaison : A){
		    	 d += liaison.getDistance();
		      }	      
		      
		     if(d > 1000){
		     d /= 1000;
		     System.out.println(d + " km");
		     }
		     else
		     System.out.println(d + " m");
		     
		     //on modifie la couleur des sommets et des arcs
		      vv.repaint();      
		      
		      //on verifie le nombre de correspondance avec un compteur
		      //et on affiche la liste
		      System.out.println("## nombre de correspondances :");
		      int corr = 0;
		      List<Station> Lcorr = new ArrayList<Station>();
		      for(Liaison liaison : A){
		    	  if(liaison.isCorresp() == true){
		    		  corr += 1;
		    		  for(Station station : L){
		    			  if(liaison.getA() == station.getId()){
		    				  Lcorr.add(station);
		    			  }
		    		  }
		    	  }
		      }
		      
		      System.out.print(corr);
		      if(corr != 0){
		    	  System.out.println(" à :");
		      }
		      	      
		      for(Station station : Lcorr){
		    	  System.out.println(" - " + station.getNom());
		      }
		      System.out.println();
		      //on affiche ensuite la liste des stations fermées
		      System.out.println("## stations fermées :");
		      for(Station station : l){
		    	  if(station.isFerme() == true)
		    	  System.out.println(" # " + station.getNom());
		      }
		      System.out.println();
		      System.out.println("-----------------------");
		      System.out.println();
		      
		      
		      //si l'utilisateur veut, il peut recalculer un nouvel itinéraire
		      //si oui l'ancien sera alors effacé et les variables qui ont permis de calculer l'itinéraire
		      //seront réinitialisées
		      
		      //les couleurs seront de nouveau par défaut et la liste des stations et celle des liaisons
		      //sera éffacée
		      System.out.println("Reset ?, appuyez sur 1");
		      int re = jk.nextInt();
		      if(re == 1){
		    	  
		    	  for(Liaison liaison : A){
			    	  liaison.setC(Color.black);
			      }

		    	  for(Station station : L){
			    	  station.setC(Color.red);
			      }
		    	  
		    	  for(Station station : l){
			    	  if(station.isFerme() == true)
			    	  station.setFerme(false);
			    	  station.setC(Color.red);
			      }
		    	  
		    	  d = 0;
		    	  corr = 0;
		    	  
		    	  A.clear();
		    	  L.clear();
		    	  Lcorr.clear();
		    	  
		    	  m = null;
		    	  n = null;
		    	  f = null;

		    	 
		    	  
		    	  
		    	  
		      	}
		      
		      }
		      
		      sc.close();
		      jk.close();
		      //-----------------------------------------------------------------------
 			}
	 	}
