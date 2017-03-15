package trajetMetro;

import java.awt.Color;
import java.util.ArrayList;

public class Station {
	
	
	private int id;
	private String nom;
	private int x;
	private int y;
	private ArrayList<Integer> lid;
	private Color c;
	private boolean ferme;
	
	public Station(ArrayList<Integer> lid, int id,String n,int x,int y){
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
	
	public Station(int id,String n,int x,int y, Color c, boolean ferme){
		this.id = id;
		nom = n;
		this.x = x;
		this.y = y;
		this.c = c;
		this.ferme = ferme;
		
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
        if ( cat.getId() != getId() ) return false;
        return true;
	}
	@Override
    public int hashCode() {
        int result;
        result = getId();
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
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
		return "Station [id=" + id + ", nom=" + nom + ", x=" + x + ", y=" + y + ", c=" + c + ", ferme=" + ferme +"]";
	}








}