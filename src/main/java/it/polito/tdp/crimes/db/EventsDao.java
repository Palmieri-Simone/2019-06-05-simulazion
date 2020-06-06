package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	

	
	public List<Integer> getAnni(){
		String sql = "	SELECT DISTINCT YEAR(reported_date) AS ANNO " + 
				     "	FROM events " + 
				     "	ORDER BY reported_date ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> result = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {

     			result.add(res.getInt("ANNO"));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	

	

	public List<Integer> getVertici(){
		String sql = "	SELECT DISTINCT district_id AS VERTICE " + 
				     "	FROM EVENTS " + 
				     "	ORDER BY district_id ASC " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> result = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
     			result.add(res.getInt("VERTICE"));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
		
		
		
		public double getLongitudineMedia(int distretto,int anno) {
			String sql = "SELECT  AVG( geo_lon ) as LON " + 
					     "FROM EVENTS " + 
					     "WHERE district_id= ? AND YEAR(reported_date)= ? ";
				
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, distretto);
			st.setInt(2,anno);
			
			Double lon=0.0;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
    	      lon=res.getDouble("LON");
				
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return lon;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;		
	}
	
		
		public double getLatitudineMedia(int distretto,int anno) {
			String sql = "SELECT  AVG( geo_lat ) as LAT " + 
					     "FROM EVENTS " + 
					     "WHERE district_id= ? AND YEAR(reported_date)= ? ";
				
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, distretto);
			st.setInt(2,anno);
			
			Double lat=0.0;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
    	      lat=res.getDouble("LAT");
				
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return lat;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;		
	}



		public int getDistrictMin(int anno) {
			String sql = "SELECT  district_id " + 
				        "FROM EVENTS " + 
				        "WHERE YEAR(reported_date)= ? "+
			            "GROUP BY district_id "+
				        "ORDER BY COUNT(*) ASC "+
			            "LIMIT 1";
			int distr=0;
			
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
	
		st.setInt(1,anno);
		
		Double lat=0.0;
		
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			try {
				distr=res.getInt("district_id");
			
				
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		conn.close();
		return distr;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return -1;		
		}



		public List<Event> elencoSimulazione(int anno, int mese, int giorno) {
			String sql = "SELECT * FROM events "
					+ "WHERE YEAR(reported_date)= ? AND MONTH(reported_date)= ? AND DAY(reported_date)= ?" ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
                st.setInt(1,anno);
                st.setInt(2,mese);
                st.setInt(3,giorno);

				List<Event> list = new ArrayList<>() ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
						list.add(new Event(res.getLong("incident_id"),
								res.getInt("offense_code"),
								res.getInt("offense_code_extension"), 
								res.getString("offense_type_id"), 
								res.getString("offense_category_id"),
								res.getTimestamp("reported_date").toLocalDateTime(),
								res.getString("incident_address"),
								res.getDouble("geo_lon"),
								res.getDouble("geo_lat"),
								res.getInt("district_id"),
								res.getInt("precinct_id"), 
								res.getString("neighborhood_id"),
								res.getInt("is_crime"),
								res.getInt("is_traffic")));
					} catch (Throwable t) {
						t.printStackTrace();
						System.out.println(res.getInt("id"));
					}
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}
		
}
