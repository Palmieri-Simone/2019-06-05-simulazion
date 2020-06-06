package it.polito.tdp.crimes.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	EventsDao dao;
	Simulatore sim;
	 private Graph<Integer,DefaultWeightedEdge>graph;

	
	
	
	public Model(){
		 dao= new EventsDao();
		
	}
	
	
	public List<Integer> getAnni() {
		return this.dao.getAnni();
		
	}
	
	public void creaGrafo(int anno) {
		 
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(this.graph, dao.getVertici());
	
      for(Integer v1: this.graph.vertexSet()) {
    	  for(Integer v2: this.graph.vertexSet()) {
    		 if(!v1.equals(v2)) {
    		   if(this.graph.getEdge(v1, v2)==null) {	 
    		 
    		  double latMedia1=dao.getLatitudineMedia(v1, anno);
    		  double longMedia1=dao.getLongitudineMedia(v1,anno);
    	   
	
    		  double latMedia2=dao.getLatitudineMedia(v2,anno);
    		  double longMedia2=dao.getLongitudineMedia(v2, anno);
    		
    		  
    		  double distanzaMedia=LatLngTool.distance(new LatLng(latMedia1,longMedia1), new LatLng(latMedia2,longMedia2), LengthUnit.KILOMETER);
    		
    		 Graphs.addEdge(this.graph, v1, v2, distanzaMedia);
    	  }
       }
    	  
      }
	   
	}  
      
	}


	public Set<Integer> getVertici(){
		return this.graph.vertexSet();
	}
	
	
	public int getNumVertici() {
		return this.graph.vertexSet().size();
		
	}
	
	public int getNumArchi() {
		return this.graph.edgeSet().size();
		}


	public List<DistrettoDistanza> getElenco(Integer v) {
		DistrettoDistanza d;
		List<DistrettoDistanza> elenco=new ArrayList<>();
   
		

		    for(Integer vicino: Graphs.neighborListOf(this.graph, v)) {
             d=new DistrettoDistanza(vicino,this.graph.getEdgeWeight(this.graph.getEdge(v, vicino)));
             elenco.add(d);
			   }
			   Collections.sort(elenco);
			   return elenco;
	}
	
	public void simulazione() {
		 sim=new Simulatore(this);
		 sim.init(10,2015, 10, 4,this.graph);
         sim.run();
		
	}
	
}
