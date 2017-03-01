package trajetMetro;

public class Station {
	
	private String nom;
	
	public Station(String n){
		nom = n;
		
	}
	
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

	@Override
	public String toString() {
		return "Station [nom=" + nom + "]";
	}
	
	

}
