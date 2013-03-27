/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domein.Event;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tim
 */
public class EventDAO {
    private static final String JDBC_URL_MYSQL = "jdbc:mysql://localhost:3306/BuurtAppDB?user=jdbc&password=jdbc";
    private static final EventDAO instance = new EventDAO();
    
    private EventDAO(){      
    }
    
    public static EventDAO getInstance(){
        return instance;
    }
        
    public boolean addEvent(Event e){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("INSERT INTO Event VALUES (?,?,?,?,?,?,?,?,?)");
            stat.setInt(1, e.getId());
            stat.setString(2, e.getNaam());
            stat.setString(3, e.getLocatie());
            stat.setDate(4, e.getStart());
            stat.setDate(5, e.getEnd());
            stat.setInt(6, e.getAuteur().getId());
            stat.setInt(7, e.getFoto().getId());
            stat.setInt(8, e.getDeelnemers().size());
            stat.setInt(9, e.getComment().getId());
            stat.executeUpdate();
            return true;
        }catch (SQLException ex){
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
        
    }
    
    public boolean addEvents(Event... events) {
        boolean success = true;
        for (Event e : events) {
            success = success && addEvent(e);
        }
        return success;
    }
    
    public Event getEvent(int id){
        Event e = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT FROM Event WHERE ID = ?");
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int i = rs.getInt("ID");
                    String n = rs.getString("Naam");
                    String l = rs.getString("Locatie");
                    Date s = rs.getDate("Start");
                    Date ei = rs.getDate("Einde");
                    int a = rs.getInt("Auteur");
                    int f = rs.getInt("Foto");
                    int d = rs.getInt("Deelnemers");
                    int c = rs.getInt("Comment");
                    
                    e =  new Event(i,n,l,s,ei,a,f,d,c);
                }
            }
            
        }catch (SQLException ex){
           for (Throwable t : ex) {
                t.printStackTrace();
            } 
        }
        return e;
    }
    
    public List<Event> getEvents(int... ids) {
        List<Event> events = new ArrayList<>();
        
        for (int id : ids) {
            Event e = getEvent(id);
            if (e != null) {
                events.add(e);
            }
        }
        
        return events;
    }
    
     public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM Event");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int i = rs.getInt("ID");
                    String n = rs.getString("Naam");
                    String l = rs.getString("Locatie");
                    Date s = rs.getDate("Start");
                    Date ei = rs.getDate("Einde");
                    int a = rs.getInt("Auteur");
                    int f = rs.getInt("Foto");
                    int d = rs.getInt("Deelnemers");
                    int c = rs.getInt("Comment");
                    
                    
                    events.add(new Event(i,n,l,s,ei,a,f,d,c));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return events;
     }
     
     public boolean updateEvent(Event e) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("UPDATE Event SET Naam = ?, Locatie = ?, Start = ?, Einde = ?, Auteur = ?, Foto = ?, Deelnemers = ?, Comment = ? WHERE ID = ?");
            stat.setInt(1, e.getId());
            stat.setString(2, e.getNaam());
            stat.setString(3, e.getLocatie());
            stat.setDate(4, e.getStart());
            stat.setDate(5, e.getEnd());
            stat.setInt(6, e.getAuteur().getId());
            stat.setInt(7, e.getFoto().getId());
            stat.setInt(8, e.getDeelnemers().size());
            stat.setInt(9, e.getComment().getId());
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
     }
     
    public boolean updateEvents(Event... events) {
        boolean success = true;
        for (Event e : events) {
            success = success && updateEvent(e);
        }
        return success;
    }
    
    public boolean deleteEvent(Event e){
        return deleteEvent(e.getId());
    }
    
    public boolean deleteEvent(int id){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM Event WHERE ID = ?");
            stat.setInt(1, id);
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }
    
    public boolean deleteEvents(Event... events) {
        boolean success = true;
        for (Event e : events) {
            success = success && deleteEvent(e);
        }
        return success;
    }
    
    public boolean deleteEvents(int... ids) {
        boolean success = true;
        for (int id : ids) {
            success = success && deleteEvent(id);
        }
        return success;
    }
}
