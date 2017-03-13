package trajetMetro;

import java.awt.Color;
import java.util.ArrayList;

public class Station {
	
	
	private int id;
	private String nom;
	private double x;
	private double y;
	private ArrayList<Integer> lid;
	private Color c;
	
	public Station(ArrayList<Integer> lid, int id,String n,double x,double y){
		this.lid = lid;
		lid.add(id);
		nom = n;
		this.x = x;
		this.y = y;

		
	}
	//test
	
	public Station(ArrayList<Integer> lid, int id,String n, Color c){
		this.lid = lid;
		lid.add(id);
		this.id = id;
		nom = n;
		this.c = c;
		
	}
	
	public void addId(int id){
		this.getLid().add(id);
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
	
	


	public ArrayList<Integer> getLid() {
		return lid;
	}

	public void setLid(ArrayList<Integer> lid) {
		this.lid = lid;
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

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	
	

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return "Station [lid=" + lid + ", nom=" + nom + ", x=" + x + ", y=" + y + ", color=" + c +"]";
	}








}
