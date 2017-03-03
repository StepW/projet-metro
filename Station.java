package trajetMetro;

public class Station {
	
	private String nom;
	private double x;
	private double y;
	
	public Station(String n,double x,double y){
		nom = n;
		this.x = x;
		this.y = y;
		
	}
	


	//nous ne voulons pas de doublons dans la liste
	@Override
	public boolean equals(Object s){
		if (this == s) return true;
        if ( !(s instanceof Station) ) return false;
        final Station cat = (Station) s;
        if ( !cat.getNom().equals( getNom() ) ) return false;
        return true;
	}
	@Override
    public int hashCode() {
        int result;
        result = getNom().hashCode();
        return result;
    }

	public String getNom() {
		return nom;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Station [nom=" + nom + ", x=" + x + ", y=" + y + "]";
	}




}
