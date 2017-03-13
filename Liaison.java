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
	
	public Liaison(Station a, Station b, String ligne, int t){
		this.a = a;
		this.b = b;
		this.ligne = ligne;
		temps = t;
		distance = (int) Distance(a,b);
	}
	
	public Liaison(int A, int B, int temps,Color c, boolean corresp){
		this.A = A;
		this.B = B;
		this.temps = temps;
		this.c = c;
		this.corresp = corresp;
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






//	public double getDistance() {
//		return distance;
//	}






//	public void setDistance(double distance) {
//		this.distance = distance;
//	}
	
	
	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public double Distance(Station a,Station b){
		double A = a.getX();
		double B = a.getY();
		double C = b.getX();
		double D = b.getY();
		double L = Math.sqrt(Math.pow(Math.abs(A-C), 2)+Math.pow(Math.abs(B-D), 2));
		return L / 0.014136 ;
		
		
		
//		return Math.sqrt(a.getX()+this.y+this.xx+this.yy);
		
		
	}

	@Override
	public String toString() {
		return "Liaison [A=" + A + ", B=" + B + ", temps=" + temps + ", color=" + c + "]";
	}
	
//	@Override
//	public String toString() {
//		return "Liaison [a=" + a + ", b=" + b + ", ligne=" + ligne + ", temps=" + temps + ", distance=" + distance
//				+ "]";
//	}
//	
	
	
	
	


}
