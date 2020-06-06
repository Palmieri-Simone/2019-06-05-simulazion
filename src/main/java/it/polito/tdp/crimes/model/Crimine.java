package it.polito.tdp.crimes.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;




public class Crimine implements Comparable<Crimine>{
	

public enum EventType {
	INIZIO_CRIMINE,
	AGENTE_ARRIVA,
	FINE_CRIMINE;
}

private LocalDateTime reportedDate;
private EventType type;
private Event e;
private int distretto;
private Agente agente;







public Crimine(LocalDateTime reportedDate, EventType type, Event e, Agente agente) {
	this.reportedDate = reportedDate;
	this.type = type;
	this.e = e;
	this.agente = agente;
}





public Event getE() {
	return e;
}





public Agente getAgente() {
	return this.agente;
}



public int getDistretto() {
	return this.distretto;
}




public LocalDateTime getReportedDate() {
	return this.reportedDate;
}








public EventType getType() {
	return this.type;
}






public int compareTo(Crimine other) {
	return this.reportedDate.compareTo(other.reportedDate);
}





public void setAgente(Agente a) {
 this.agente=a;	
}


}