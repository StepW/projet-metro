package projet1;

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
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class Main3 {
	
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
	
	// constante importé
		private static JPanel container = new JPanel();
		private static JPanel container1 = new JPanel();
		  private static JComboBox combo = new JComboBox();
		  private static JComboBox combo2 = new JComboBox();
		  private static JLabel debut = new JLabel("Départ");
		  private static JLabel fin = new JLabel("Arrivée");
		 static JLabel texte = new JLabel();
		  static JButton bouton = new JButton("Recherché");
		
 public static void main(String[] args) {
		 
		 //on cherche le fichier à chercher
		 String fichiernoms = "D:/Projet_Java/nom station.txt";
		 String fichiersommets = "D:/Projet_Java/coordonnee sommet.txt";
		 String fichierarcs = "D:/Projet_Java/arc valeur temps.txt";

		 
		 File f1 = new File(fichiernoms);
		 File f2 = new File(fichiersommets);
		 File f3 = new File(fichierarcs);


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
		 
			

		 
		 //zone de test
		 //-----------------------------------------------------------------------------
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

		 //-----------------------------------------------------------------------------
		 
	 	//on créé un graphe qui va regrouper tout les éléments
		 Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
		 
		 
		 
		 //dans ce graphe on ajoute les sommets qui correspondent aux stations.
		 
		 for(Station station : l){
			 g.addVertex((Station)station);
		 }
		 
		 
		 

		 
		 
		 /*on ajoute ensuite les arcs à partir de la liste des liaisons
		 * créé une boucle qui va parcourir cette liste
		 *en lisant une liaison, on va parcourir la liste des station et dans une station...
		 *...on va parcourir les identifiants de cette station qui correspond à un sommet du graphe
		 */
		 
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
									 int d = liaison.Distance(station.getX(),station.getY(),stat.getX(),stat.getY());
									 liaison.setDistance(d);
									 
									 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
									 break;
								 }

							 }
							 
						 }
						 
					 }

						
				 

				 
			 
			 
		 }
		 
//		 for(Station station : l){
//			 System.out.println(station);
//		 }
//		 
		 

		 
//		 for(Liaison liaison : a)
//			 System.out.println(liaison);
		 
		 
		 
		 
		 
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
		      
		        

		        // Layout<V, E>, VisualizationViewer<V,E>
		        
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
			                return (e.getNom() + " : " + e.getId());
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
			      
//
				      JFrame frame = new JFrame("Simple Graph View");
				      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				      frame.setSize(800, 800);
				      frame.isResizable();

				      frame.setLocationRelativeTo(null);
				      
//			    	  
//				      frame.getContentPane().add(vv); 
////				      frame.getContentPane().add(vv2); 
//				      frame.pack();
				      frame.setVisible(true);  
			        
			        //debut de la création de new fenetre
				      container1.setBackground(Color.white);
					    container1.setLayout(new BorderLayout());
					    container.setBackground(Color.white);
					    container.setLayout(new BorderLayout());
					    combo.setPreferredSize(new Dimension(200, 20));
					    combo2.setPreferredSize(new Dimension(200, 20));
					    texte.setPreferredSize(new Dimension(200, 100));
					    
					    JPanel top = new JPanel();
					    JPanel tap = new JPanel();
					    JPanel rec = new JPanel();
					    
					    JPanel roc = new JPanel();
					    
					    top.add(debut);
					    top.add(combo);
					    top.add(fin);
					    top.add(combo2);
					    top.add(bouton);
					    
//					  bouton.addActionListener(new ActionListener());
					    
					    roc.add(vv);
					   
					    container.add(top, BorderLayout.NORTH);
					    container.add(roc, BorderLayout.CENTER);
					    container.add(texte, BorderLayout.SOUTH);
					    texte.setHorizontalAlignment(JLabel.CENTER);
					   
					    frame.setContentPane(container);
					    frame.setVisible(true); 
					    
//					    frame.setContentPane(container1);
//					    frame.setVisible(true);
					    
					    
					    for(Station station : l){
					    	combo.addItem(station.getNom());
						 }
					    for(Station station : l){
					    	combo2.addItem(station.getNom());
						 }
					   
					    //fin creation de new fenetre
				     
//		      
				      Transformer<Liaison, Integer> wtTransformer = new Transformer<Liaison,Integer>() {
				          public Integer transform(Liaison link) {
				        		  return link.getTemps();
				          }
				      };
		      
		      //on utilise un objet Djikstra pour calculer l'itinéraire le plus court entre deux stations 
				      bouton.addActionListener(new ActionListener() {
					   		
				        	public void actionPerformed(ActionEvent e) {
				 			
				        		
				        		
				        		DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
				        	    
				        	    //le scanner est utilisé pour écrire une chaine de caractères dans la console
				        	    
				        	    //pour une chaine de caractère
				        	//   Scanner sc = new Scanner(System.in);
				        	    
				        	    //pour un entier
			//	        	    Scanner jk = new Scanner(System.in);
				        	    
//				        	    System.out.println("Départ : ");
//				        	    int i = sc.nextInt();
//				        	    System.out.println("Arrivée : ");
//				        	    int j = sc.nextInt();
				        	    
				        	    
				        	    List<Station> L = new ArrayList<Station>();
				        	    List<Liaison> A = new ArrayList<Liaison>();
				        	    
				        	    
				        	  
				        	  	  
				        	   // System.out.println("Départ : ");
				        	    String m = combo.getSelectedItem().toString();
				        	  //  System.out.println( m );
				        	    String n = combo2.getSelectedItem().toString();
				        	 //  System.out.prinln( n );

				        	    String f = null;
				        	 //   System.out.println(f);
				        	   List<Station> F = new ArrayList<Station>();
				        	    
//				        	    
//				        	    boolean ferme = true;
////				        	    
////				        	    while(ferme == true){
////				        	  	  
//				        	  	  f = jk.nextLine().trim();
//				        	  	  
////				        	  	  
//				        	  	  
//				        	  	  if(f.equals("stop")){
//				        	  		  ferme = false;
//				        	  		 //
//				        	  		  
//				        	  	  }
//				        	  	  
//				        	  	  
//				        	  	  if(f.equals("")){
//				        	  		  f = jk.nextLine().trim();
//				        	  	  }
//				        	  	  
//				        	  	  for(Station station : l){
//				        	  		  if(Integer.parseInt(f) == station.getId()){
//				        	  			  F.add(station);
//				        	  			  station.setFerme(true);
//				        	  		  }
//				        	  	  }  
////				        	  	  
//				        	  	  System.out.println(f);
//				        	  	  
//				        	    }
//				        	    
//				        	    System.out.println("-----------------------");

//				        	    
//
//				        	  	  for(Station station : l){
//				        	  		  if(station.isFerme() == true){
//				        		    		  for(Liaison liaison : a){
//				        		    			  System.out.println(station.getId());
//				        		    			  System.out.println(liaison.getA());
//				        		    			  
//				        		    			  if(station.getId() == liaison.getB()
//				        		    					  && liaison.isCorresp() == true){
//				        		    				  liaison.setTemps(100000);
//				        		    			  }
//				        		    			  
//				        		    			  if(station.getId() == liaison.getA()	    					  
//				        		    					  && liaison.isCorresp() == true){
//				        		    				  liaison.setTemps(100000);
//				        		    			  }
//				        		    			  
//				        		    			  
//				        		    			  
//				        		    		  }
//				        	  		  }
//
//				        	    }
//				        	  	  System.out.println("---------------------------------------");
//				        	  	  
//				        	  	  System.out.println("Stations fermées");
//				        	  	  
//				        	  	  for(Station station : l){
//				        	  		  if(station.isFerme() == true){
//				        	  			  System.out.println(station.getId());
//				        	  		  }
//				        	  	  }
//				        	  	  
				        	    
				        	    
				        	    
				        	    
				        	    
				        	    
//				        	    System.out.println("Départ : ");
//				        	    int M = sc.nextInt();
//				        	    System.out.println("Arrivée : ");
//				        	    int N = sc.nextInt();
				        	    
//				        	    String m = "châtelet";
//				        	    String n = "gare du nord";
				        	    
				        	    
				        	    int k = 0;
				        	    int c = 0;
//				        	    
//
//				        	    for(Station station : l){
//				        	  	  for(int id : station.getLid()){
//				        	  		  if(id == i){
//				        	  			  k = l.indexOf(station);
//				        	  		  }
//				        	  		  if(id == j){
//				        	  			  c = l.indexOf(station);
//				        	  		  }
//				        	  	  }
//				        	    }
				        	    
				        	    		      
				        	    //String
				        	    for(Station station : l){
				        		      if(m.equals(station.getNom())){
				        		    	  k = l.indexOf(station);
				        		    	//  System.out.println(station);
				        		    	  }
				        		      if(n.equals(station.getNom())){
				        		    	  c = l.indexOf(station);
				        		    	 // System.out.println(station);
				        		    	  }
				        	    }
				        	    
				        	    //station fermee
				        	    
				        	    
				        	    
				        	    
				        	    
//				        	    for(Station station : l){
//				        		      if(M == station.getId()){
//				        	  	  k = l.indexOf(station);
				        	//
//				        	  	  }
//				        	    if(N == station.getId()){
//				        	  	  c = l.indexOf(station);
				        	//
//				        	  	  }
//				        	    }
				        	    
				        	    
				        	    
//				        	    List<Liaison> L = alg.getPath(l.get(k), l.get(c));

				        	    
				        	  //  System.out.println(l.get(k).getNom()+" : "+l.get(k).getId());
				        	   // System.out.println(l.get(c).getNom()+" : "+l.get(c).getId());
				        	    
//				        	    
//				        	    int gg = 0;
//				        	//    
//				        	    while(gg <= 10){
//				        		      A = null;
				        	    
				        	    A = alg.getPath(l.get(k), l.get(c));
				        	    
				        	    
				        	    
				        	//    

				        	    //si la première liaison est une correspondance, on l'enlève
				        	    if(A.get(0).isCorresp() == true){
				        	  	  A.remove(0);  
				        	    }
				        	    
				        	    
//				        	    System.out.println(A.get(A.size()-1));
//				        	    System.out.println("dernier élément : " + A.get(A.size()));
				        	    

				        	    
				        	    if(A.get(A.size()-1).isCorresp() == true){
				        	  	  A.remove(A.size()-1);  
				        	    }

				        	    
				        	    for(Liaison ligne : A){
				        		  //  System.out.println(ligne);
				        		      
				        		      }
				        	    
				        	    System.out.println();
				        	    

				        	    for(Liaison liaison : A){
				        	  	  liaison.setC(Color.red);
				        	    }


				        	   

				        	    
				        	    for(Station station : l){
				        	  if(station.getId() == A.get(0).getA()){
				        	  		//System.out.println(l);
				        	  		//station.getNom();
				        	  		L.add(station);
				        	  		L.get(0).setC(Color.blue);
				        	  		//texte.getText() + station.getNom() + "\n");
				        	  		//System.out.println(L);
				        	  	  }
				        	    }
				        	    
				        	    Station v = null;

				        		for(Liaison ligne : A){
				        			//System.out.println(station.getLid());
				        		    for(Station station : l){
				        		    	System.out.println(station);
				        		  	  for(int id : station.getLid()){
//				        		  		  if(ligne.getA() == id || ligne.getB() == id){
//				        		  			  v = station;
//				        		  			  break;
//				        		  		  }
//				        		  	  }
				        		  	  if(!L.contains(v) && v != null){
				        		  		  v.setC(Color.blue);
				        		  		  L.add(v);
				        		  		System.out.println(v);
				        		  	  }
				        		    	}

				        		    }
//				        	    
				        		for(Station station : L){
//				        			
				        			String [] tab = {station.getNom()};
				        			//System.out.println(L);
				        			//texte.setText(texte.getText() + "==> " + station.getNom() + "");
				        		  // texte():
				        			for (int j = 0; j < tab.length; ) {
				        				texte.setText(texte.getText() + "==> " + station.getNom() + "\n");
				        				j++;
				        			}

				        		    }
				        		


//				        	    for(Liaison ligne : A){
//				        		      for(Station station : l){
//
//
//				        		    	  
//				        		    		  if(ligne.getB() == station.getId()){
//				        		    			  v = station;
//				        		    			  System.out.println(v);
//				        		    			  break;
//
//				        		    		  }
//				        		    		  
//
//				        		    		 
//				        		    	  }
//				        		      
//				        		    	  if(!L.contains(v) && v != null){
//				        		    		  v.setC(Color.blue);
//				        		    		  L.add(v);
//				        		    		  
//				        		    	  }
//				        		    	  
//				        		    	  for(Station station : F){
//				        		    		  station.setC(Color.gray);
//				        		    	  }
//				        		    	  
//				        		    	  
//				        		  }
//
//				        	    
//				        	    System.out.println();
//				        	    
//				        		  System.out.println("Liaisons de l'itinéraire : ");
//				        		  
//				        		  
//				        	    for(Liaison ligne : A){
//
//				        	    System.out.println(ligne);
//				        	    }
//				        	    
//				        	    System.out.println();
//				        		  System.out.println("Stations de l'itinéraire : ");  
//				        	    for(Station station : L){
//
//				        		      System.out.println(station);
//				        		      }
//				        	    
//				        	    
//				        	    Number dist = alg.getDistance(L.get(0), L.get(L.size()-1));
//				        	    
//				        	    // le temps d'arrêt  à une station= 20 sec
//				        	    //on ne compte pas la station de départ et d'arrivée
//
//				        	    double arret = 0;
//				        	    for(Station station : L){
//				        	  	  arret += 20;
//				        	    }
//				        	    if(arret <= 0)
//				        	    arret = 0;
//				        	    
//				        	    
//				        	    System.out.println(dist);
//				        	    System.out.println();
//				        	    System.out.println("-----------------------");
//				        	    System.out.println();
//				        	    
//				        	    
//				        	    double tempsM = (double) dist + arret;
//				        	    tempsM *= 0.0166667;
//				        	    System.out.println("temps de trajet estimé :");
//				        	    System.out.println(Math.round(tempsM) + " minutes");
//				        	    
//				        	   
//				        	   System.out.println("distance à parcourir estimée :");
//				        	   
//				        	   int d = 0;
//				        	   
//				        	   for(Liaison liaison : A){
//				        	  	 d += liaison.getDistance();
//				        	   }
//				        	   if(d > 1000){
//				        	   d /= 1000;
//				        	   System.out.println(d + " km");
//				        	   }
//				        	   else
//				        	   System.out.println(d + " m");
//
//				        	    vv.repaint();      
//				        	    
//				        	    
//				        	    System.out.println("nombre de correspondances :");
//				        	    
//				        	    int corr = 0;
//				        	    
//				        	    
//				        	    //on verifie le nombre de ois où la liaison est une correspondance
//				        	    
//				        	    List<Station> Lcorr = new ArrayList<Station>();
//				        	    
//				        	    for(Liaison liaison : A){
//				        	  	  if(liaison.isCorresp() == true){
//				        	  		  corr += 1;
//				        	  		  for(Station station : L){
//				        	  			  if(liaison.getA() == station.getId()){
//				        	  				  Lcorr.add(station);
//				        	  			  }
//				        	  		  }
//				        	  		  
//				        	  	  }
//			        	    }
				        	    
				        	    
//				        	    //on affiche les correspondances
//				        	    System.out.print(corr);
//				        	    if(corr != 0){
//				        	  	  System.out.println(" à :");
//				        	    }
//				        	    
//				        	    for(Station station : Lcorr){
//				        	  	  System.out.println(" - " + station.getNom());
//				        	    }
//				        	    
//				        	    System.out.println();
//				        	    
//				        	    System.out.println("nombre de stations fermées :");
////				        	    for(Station station : F){
////				        	  	  System.out.println(" # " + station.getNom());
////				        	    }
//				        	    for(Station station : l){
//				        	  	  if(station.isFerme() == true)
//				        	  	  System.out.println(" # " + station.getNom());
//				        	    }
//				        	    
//				        	    System.out.println();
//				        	    System.out.println("-----------------------");
//				        	    System.out.println();
//				        	    
//				        	    System.out.println("Reset ?, appuyez sur 1");
//				        	    
//				        	    
//				        	    int re = jk.nextInt();
//
//				        	    if(re == 1){
//				        	  	  
//				        	  	  for(Liaison liaison : A){
//				        		    	  liaison.setC(Color.black);
//				        		      }
//
//				        	  	  for(Station station : L){
//				        		    	  station.setC(Color.red);
//				        		      }
//				        	  	  
//				        	  	  for(Station station : l){
//				        		    	  if(station.isFerme() == true)
//				        		    	  station.setFerme(false);
//				        		    	  station.setC(Color.red);
//				        		      }
//				        	  	  
//				        	  	  d = 0;
//				        	  	  corr = 0;
//				        	  	  
//				        	  	  A.clear();
//				        	  	  L.clear();
//				        	  	  Lcorr.clear();
//				        	  	  
//				        	  	  m = null;
//				        	  	  n = null;
//				        	  	  f = null;
//				        	  	  ferme = true;
//				        	  	  
//
//				        	    }
//				        	    
//				        	    
//				        	    
				        	    
				        	    
				        	} 	 
					 
				      });
				     
		      // #######################################################################

             
		     
     }             

	 }
