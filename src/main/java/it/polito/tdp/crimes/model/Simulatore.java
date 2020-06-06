package it.polito.tdp.crimes.model;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.Crimine.EventType;



public class Simulatore {
	// PARAMETRI DI SIMULAZIONE
	int agenti=5;
	final int vel=60;
	List<Agente> centrale;
	Duration tempoArrivo;
	Duration durata=Duration.of(30,ChronoUnit.MINUTES);
	Duration attesa;
	int distr;
	
	EventsDao dao=new EventsDao();
	
	Model model;
	
    
	 public Simulatore(Model model) {
		 this.model=model;
	 }
	
	
	// OUTPUT DA CALCOLARE
          int malgestiti=0;

	// STATO DEL SISTEMA
         
    private Graph<Integer,DefaultWeightedEdge> grafo;      
          
	// CODA DEGLI EVENTI
	private PriorityQueue<Crimine> queue;

	

	
	// INIZIALIZZAZIONE
	public void init(int agenti,int anno,int giorno,int mese,Graph<Integer,DefaultWeightedEdge> grafo ) {
		this.agenti=agenti;
		centrale=new ArrayList<>();
		this.grafo=grafo;
		EventsDao dao=new EventsDao();
		distr=dao.getDistrictMin(anno);
		System.out.println(distr);
		for(int i=0;i<agenti;i++) {
			Agente a= new Agente(i,distr,true);
			centrale.add(a);
		}
		
		System.out.println(centrale.toString());
	
		
		this.queue = new PriorityQueue<>();
	
		double random=Math.random();
		
		
		for(Event e: dao.elencoSimulazione(anno, mese, giorno)) {		
           this.queue.add(new Crimine(e.getReported_date(),EventType.INIZIO_CRIMINE,e,null));
		}
	}
		
	
	

	
	// ESECUZIONE
	public void run() {
		while (!this.queue.isEmpty()) {
			Crimine e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Crimine c) {
	
			Event evento=c.getE();

			
		switch(c.getType()) {
		
		case INIZIO_CRIMINE:
			   double distanza=0.0;
		        tempoArrivo=Duration.of(0, ChronoUnit.MINUTES);
		    	
		        
		        
		        Agente a=contieneLibero(centrale);
		
			  
			    if(a!=null) {
			  	  System.out.println(a.getDistretto());
				if(a.getDistretto()!=evento.getDistrict_id())	{
				distanza=this.grafo.getEdgeWeight(this.grafo.getEdge(c.getE().getDistrict_id(), a.getDistretto()));	
				tempoArrivo=Duration.of((long) ((distanza*1000)/(vel*3.65)),ChronoUnit.SECONDS);
				}
				a.setDistretto(evento.getDistrict_id());
			  	System.out.println(a.getDistretto());

				a.setLibero(false);

				Crimine crimine=new Crimine(evento.getReported_date().plus(tempoArrivo),EventType.AGENTE_ARRIVA,evento,a);
				crimine.setAgente(a);
				this.queue.add(crimine);
				System.out.println(crimine.getAgente());
				
				System.out.println("NUOVO CRIMINE : distretto"+c.getE().getDistrict_id()+" ora crimine: "+c.getReportedDate() +". AGENTE IN ARRIVO TRA :"+tempoArrivo);
				}else {
					
				System.out.println("NUOVO CRIMINE : distretto"+c.getE().getDistrict_id()+". NON CI SONO AGENTI LIBERI");
				malgestiti++;
				
				}
				
		
		case AGENTE_ARRIVA:
		
			
			if(evento.getOffense_category_id().equals("all_other_crimes")) {
				if(Math.random()<0.5)
			       durata=Duration.of(120, ChronoUnit.MINUTES);
			 else
			       durata=Duration.of(60, ChronoUnit.MINUTES);
		}else {
		       durata=Duration.of(120, ChronoUnit.MINUTES);
		}
			  
		//	System.out.println(c.getAgente().getDistretto());
				
    	 Crimine crimine=new Crimine(evento.getReported_date().plus(durata),EventType.FINE_CRIMINE,evento,c.getAgente());
    	 System.out.println("***"+crimine.getAgente());
    	 
		 this.queue.add(crimine);
		
	


    	 if(crimine.getReportedDate().isAfter(evento.getReported_date().plusMinutes(15))) {
    		 System.out.println("AGENTE ARRIVATO  : distretto"+c.getE().getDistrict_id()+".GESTITO MALE:   ora massima consentita: "+evento.getReported_date().plusMinutes(15));
    			 malgestiti++;
    	 }else {
    		 System.out.println("AGENTE ARRIVATO  : distretto"+c.getE().getDistrict_id()+".GESTITO CORRETTEMANTE:  ora massima consentita: "+evento.getReported_date().plusMinutes(15));
    	 }
    	 
		
	 break;
		
		
		case FINE_CRIMINE:
			System.out.println("CRIMINE TERMINATO");
			//System.out.println(c.getAgente().toString());
			
			c.getAgente().setLibero(true);
		
			break;
		
		}
}


public Agente contieneLibero(List<Agente> centrale) {
	for(Agente a:centrale) {
		if(a.isLibero())
			return a;
	}
	return null;
	
}

}
