package trajetMetro;

public class Liaison {
	
	private Station a;
	private Station b;
	private String ligne;
	private double temps;
	private double distance;
	
	public Liaison(Station a, Station b, String ligne, double t){
		this.a = a;
		this.b = b;
		this.ligne = ligne;
		temps = t;
		distance = Distance(a,b);
	}
	
	
	
	
	
	
	public double getTemps() {
		return temps;
	}






	public void setTemps(double temps) {
		this.temps = temps;
	}






	public double getDistance() {
		return distance;
	}






	public void setDistance(double distance) {
		this.distance = distance;
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
		return "Liaison [a=" + a + ", b=" + b + ", ligne=" + ligne + ", temps=" + temps + ", distance=" + distance
				+ "]";
	}
	
	
	

}
