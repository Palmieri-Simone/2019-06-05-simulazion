package it.polito.tdp.crimes.model;

public class DistrettoDistanza implements Comparable<DistrettoDistanza>{
private int distretto;
private Double distanza;



public DistrettoDistanza(int distretto, double distanza)  {
	this.distretto = distretto;
	this.distanza = distanza;
}
public int getDistretto() {
	return distretto;
}
public void setDistretto(int distretto) {
	this.distretto = distretto;
}
public double getDistanza() {
	return distanza;
}
public void setDistanza(double distanza) {
	this.distanza = distanza;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + distretto;
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	DistrettoDistanza other = (DistrettoDistanza) obj;
	if (distretto != other.distretto)
		return false;
	return true;
}
@Override
public String toString() {
	return  distretto + "  :  " + distanza+"\n";
}
@Override
public int compareTo(DistrettoDistanza o) {
	return this.distanza.compareTo(o.distanza);
}






}
