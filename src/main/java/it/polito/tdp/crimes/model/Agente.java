package it.polito.tdp.crimes.model;

public class Agente {
    private int id;
	private int distretto;
    private boolean libero;
    
    
	public Agente(int id,int distretto,boolean libero) {
	    this.id=id;
		this.distretto = distretto;
	    this.libero=libero;
	}



	public int getDistretto() {
		return distretto;
	}

	public void setDistretto(int distretto) {
		this.distretto = distretto;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}
	
	
	
}
