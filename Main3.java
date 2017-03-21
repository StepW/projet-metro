package trajetMetro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
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
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class Main3 {
	
 public static void main(String[] args) {
		 
		 //on cherche le fichier � chercher
		 String fichiernoms = "D:/JAVA/fichiers stations/nom station.txt";
		 String fichiersommets = "D:/JAVA/fichiers stations/coordonnee sommet.txt";
		 String fichierarcs = "D:/JAVA/fichiers stations/arc valeur temps.txt";

		 
		 File f1 = new File(fichiernoms);
		 File f2 = new File(fichiersommets);
		 File f3 = new File(fichierarcs);
		 



		 //on cr�� la liste des stations pour les sommets du graphe
		ArrayList<Station> l = new ArrayList<Station>();
		
		//on cr�� la liste des liaisons pour les arcs du graphe
		ArrayList<Liaison> a = new ArrayList<Liaison>();
		


		//-----------------------------------------------------------------------------
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
				//-----------------------------------------------------------------------------

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



						
						l.add(new Station(idStation,nomStation,0,0,Color.red,false));
						
					}
				
				br1.close();

				//inportation du fichier 2
				//-----------------------------------------------------------------------------		
				
				while ((ligne=br2.readLine())!=null){
					String[] st = ligne.split(" ");
					
					
					//on int�gre les �l�ments de la liste dans des variables pour le test suivant
					int idStation = Integer.parseInt(st[0]);
					int coordX = Integer.parseInt(st[1]);
					int coordY = Integer.parseInt(st[2]);
					
					
					//on parcours la liste et on v�rifie dans la liste identifiants de la station
					//lorsqu'un identifiant est reconnu, on attribut les coordonn�es � la station auquelle elle appartient
					

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
					
					
					//on int�gre les �l�ments de la liste dans des variables pour le test suivant
					int sI = Integer.parseInt(st[0]);
					int sF = Integer.parseInt(st[1]);
					double tempsDouble = Double.parseDouble(st[2]);
					int temps = (int) tempsDouble;
					
					//on commence par v�rifier le sommet initiale de l'arc
					//pour cela on cherche l'identifiant dans la liste des stations
					for(Station station : l){
						if(station.getId() == sI){
							
							//quand la condition pr�c�dente est remplie, on fait la m�me op�ration
							//avec le sommet final
							
								for(Station station2 : l){
										if(station2.getId() == sF){
											
											/*quand les deux stations sont trouv�es, on cherche ensuite des arcs
											 *qui sont des correspondances on sait les reconaitre lorsque le temps
											 *est �gal � la valeur double 120.0 
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
		 
			
	 	//on cr�� un graphe qui va regrouper tout les �l�ments
		 Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
		 
		 
		 
		 //dans ce graphe on ajoute les sommets qui correspondent aux stations.
		 for(Station station : l){
			 g.addVertex((Station)station);
		 }
		 
		 
		 

		 
		 
		 /*on ajoute ensuite les arcs � partir de la liste des liaisons
		 * pour cela cr�� une boucle qui va parcourir cette liste en lisant l'identifiant du sommet entrant
		 * 
		 * en m�me temps on parcourt la liste des station et on v�rifie d'identifiant qu'on associe
		 * au sommet initiale de la liaison
		 * 
		 * on va parcourir les identifiants de cette station qui correspond � un sommet du graphe
		 */

		 
		 for(Liaison liaison : a){
			 for(Station station : l){

				 /* en m�me temps on parcourt la liste des station et on v�rifie d'identifiant qu'on associe
				 *au sommet initiale de la liaison
				 */
					 if(station.getId() == liaison.getA()){
						 
						//quand l'identifiant est trouv�, on fait la m�me op�ration pour le sommet final
						 for(Station stat : l){

								 if(stat.getId() == liaison.getB()){
									 
						//une fois le sommet initial et final, on va pouvoir ajouter un arc dans le graphe
						//on recommence jusqu'� ce que toute la liste des liaisons soit parcourue
									 int d = liaison.Distance(station.getX(),station.getY(),stat.getX(),stat.getY());
									 liaison.setDistance(d);
									 
									 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
									 break;
								 }

							 }
							 
						 }
						 
					 }

						
				 

				 
			 
			 
		 }
		 
		    // nous allons maintenant g�n�rer une interface pour visualiser le graphe
		 	// ce graphe correspond � un plan g�om�trique des liaisons et des stations de m�tro
		 
		 //� l'aide de la fonction Transformer, nous voulons placer les sommets
		 //en fonction des coordonn�es X et Y de de chaque station � partir d'objets Point 2D
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
			  
			  
			     //puis nous ins�rons un module de visualisation du graphe
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
			        
			        
			        //lorsqu'un itin�raire est trouv�, on veut le colorier
			        //on change alors la couleur des sommets et des arcs qui correspondent
			        //� cet itin�raire
			        
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
			        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
			        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
			        vv.setGraphMouse(gm);
			      

			        //on veut afficher le graphe dans une fen�tre
			        JFrame frame = new JFrame("Itin�raire M�tro Paris");
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
		      
		      //on utilise un objet Djikstra pour calculer l'itin�raire le plus court entre deux stations 
		      DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
		      
		      //le scanner est utilis� pour �crire une chaine de caract�res dans la console
		      
		      //pour une chaine de caract�re
		      Scanner sc = new Scanner(System.in);
		      
		      //pour un entier
		      Scanner jk = new Scanner(System.in);
		      
		      
		      //on cr�� deux listes qui correspondent respectivenet � la liste des stations
		      //et � la liste des liaisons de l'itin�raire
		      List<Station> L = new ArrayList<Station>();
		      List<Liaison> A = new ArrayList<Liaison>();
		      
		      
		      //pour obtenir un itin�raire, on doit taper la station de d�but et la station de fin
		      
		      while(A.isEmpty() && L.isEmpty()){
		    
		    //nous pouvons taper la station de d�but et la station de fin
		      System.out.println("D�part : ");
		      String m = sc.nextLine().trim();
		      System.out.println("Arriv�e : ");
		      String n = sc.nextLine().trim();     
		      
		      //nous devons aussi faire une liste des stations qui sont ferm�es,
		      //on tape l'identifiant de la station
		      //les stations ferm�es influent sur l'itin�raire � tracer
		      //le m�tro ne s'arrete pas au niveau des stations ferm�es
		      //on ne peut pas effectuer de correspondance � partir de ces derni�res
		      
		      System.out.println("Station ferm�es : ");
		      String f = null;
		      
		      //on cr�� une liste de stations ferm�es qui servira plus tard
		      List<Station> F = new ArrayList<Station>();
		      
		      

		      
		      while(true){
		    	  
		    	  //on �crit "stop" pour demander d'arreter de remplir la liste
		    	  f = jk.nextLine().trim();
		    	  if(f.equals("stop")){
		    		  break;
		    		  
		    	  }
		    	  
		    	  //la variable ne doit pas �tre vide on doit obligatoirement rentrer soit un num�ro d'une station
		    	  //soit le mot "stop" 
		    	  if(f.equals("")){
		    		  f = jk.nextLine().trim();
		    	  }
		    	  
		    	  //on remplis la liste des stations ferm�es et on change la valeur du bool�en "ferme"
		    	  for(Station station : l){
		    		  if(Integer.parseInt(f) == station.getId()){
		    			  F.add(station);
		    			  station.setFerme(true);
		    		  }
		    	  }  
		    	  
//		    	  System.out.println(f);
		    	  
		      }
		      
		      System.out.println("------------------------------------");

		      
		      
		      //on modifie les donn�es de la station lorsqu'elle est ferm�e
		      //si les liaisons autour sont des correspondances, on ne doit pas les emprunter
		      //on leur donne une priorit� minimale
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
		      
		      //lorsque l'itin�raire est calcul�, la station de d�part est d�finit en fonction du nom rentr�
		      //et non de l'identifiant, un nom de station a un identifiant fixe
		      //si la premi�re liaison est une correspondance, on l'enl�ve de la liste

		      if(A.get(0).isCorresp() == true){
		    	  A.remove(0);  
		      }
		      
		      //on enl�ve aussi la derni�re liaison si c'est une correspondance
		      if(A.get(A.size()-1).isCorresp() == true){
		    	  A.remove(A.size()-1);  
		      }

		      for(Liaison liaison : A){
		    	  liaison.setC(Color.red);
		      }


		      Station v = null;

		      //� l'aide de l'objet de transformation plus haut, on colorie les stations
		      //qui font partie de l'itin�raire
		      
		    //on colorie les liaisons appartenant � la liaison en rouge, les stations de l'itin�raire
		    //en bleu et les stations ferm�es en gris
		      
		      //on colorie d'abort la premi�re station de l'itin�raire...
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
	    	  System.out.println("Liaisons de l'itin�raire : ");
	    	  
	    	  
		      for(Liaison ligne : A)
		      System.out.println(ligne);
		      
		      
		      System.out.println();
		      
		      //on affiche la liste des itin�raires
	    	  System.out.println("Stations de l'itin�raire : ");  
		      for(Station station : L)
			      System.out.println(station);
			      
		      //� l'aide de la transformation du poids des arc plus haut, on calcule le temps
		      //pass� dans les liaisons
		      Number dist = alg.getDistance(L.get(0), L.get(L.size()-1));
		      
		      // le temps d'arr�t  � une station= 20 sec
		      //on ne compte pas la station de d�part et d'arriv�e, ni les stations ferm�es
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
		      //on prend en compte le temps de trajet dans une liaison + le temps d'arret � une stations
		      double tempsM = (double) dist + arret;
		      tempsM *= 0.0166667;
		      
		      
		      System.out.println("## Station de d�part :");
		      System.out.println(L.get(0).getNom());
		      System.out.println("## Station d'arriv�e :");
		      System.out.println(L.get(L.size()-1).getNom());
		      System.out.println("## temps de trajet estim� :");
		      System.out.println(Math.round(tempsM) + " minutes");
		      System.out.println("## distance � parcourir estim�e :");
		      
		      //les distances sont calcul�es � partir des coordonn�es des stations
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
		    	  System.out.println(" � :");
		      }
		      	      
		      for(Station station : Lcorr){
		    	  System.out.println(" - " + station.getNom());
		      }
		      System.out.println();
		      //on affiche ensuite la liste des stations ferm�es
		      System.out.println("## stations ferm�es :");
		      for(Station station : l){
		    	  if(station.isFerme() == true)
		    	  System.out.println(" # " + station.getNom());
		      }
		      System.out.println();
		      System.out.println("-----------------------");
		      System.out.println();
		      
		      
		      //si l'utilisateur veut, il peut recalculer un nouvel itin�raire
		      //si oui l'ancien sera alors effac� et les variables qui ont permis de calculer l'itin�raire
		      //seront r�initialis�es
		      
		      //les couleurs seront de nouveau par d�faut et la liste des stations et celle des liaisons
		      //sera �ffac�e
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
		      //-----------------------------------------------------------------------
 			}
	 	}
