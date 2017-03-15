package trajetMetro;

import java.awt.Color;

public class Liaison {
	
	
	private int A;
	private int B;
	private Station a;
	private Station b;
	private String ligne;
	private int temps;
	private int distance;
	private Color c;
	private boolean corresp;
	private boolean ouvert;
	
	public Liaison(Station a, Station b, String ligne, int t){
		this.a = a;
		this.b = b;
		this.ligne = ligne;
		temps = t;
//		distance = (int) Distance(a,b);
	}
	
	public Liaison(int A, int B, int temps,Color c, boolean corresp){
		this.A = A;
		this.B = B;
		this.temps = temps;
		this.c = c;
		this.corresp = corresp;
//		distance = (int) Distance(a,b);
	}
	
	
	
	
	public int getA() {
		return A;
	}

	public int getB() {
		return B;
	}
	
	
	

	public int getTemps() {
		return temps;
	}


	public void setTemps(int temps) {
		this.temps = temps;
	}






	public int getDistance() {
		return distance;
	}






	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public int Distance(int a, int b, int c, int d){
		
		//grande arche de la défense  : X: 77 Y : 465
		//esplanade de la défense     : X: 91 Y : 461
//		double A = a.getX();
//		double B = a.getY();
//		double C = b.getX();
//		double D = b.getY();
		double A = (double) a;
		double B = (double) b;
		double C = (double) c;
		double D = (double) d;
		double L = Math.sqrt(Math.pow(Math.abs(A-C), 2)+Math.pow(Math.abs(B-D), 2));
		L *= 25.7 ;
		// 1 mètre éauivaux à environ 25.7 mètre
		return (int) L;
		
		
		
//		return Math.sqrt(a.getX()+this.y+this.xx+this.yy);
		
		
	}

	public boolean isCorresp() {
		return corresp;
	}

	public void setCorresp(boolean corresp) {
		this.corresp = corresp;
	}

	@Override
	public String toString() {
		return "Liaison [A=" + A + ", B=" + B + ", temps=" + temps + ", distance=" + distance + ", color=" + c + ", corresp=" + corresp  + "]";
	}
	
//	@Override
//	public String toString() {
//		return "Liaison [a=" + a + ", b=" + b + ", ligne=" + ligne + ", temps=" + temps + ", distance=" + distance
//				+ "]";
//	}
//	
	
	
	
	


}