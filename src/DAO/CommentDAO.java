/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domein.Melding;
import java.sql.Connection;
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
public class CommentDAO {
    private static final String JDBC_URL_MYSQL = "jdbc:mysql://localhost:3306/BuurtAppDB?user=jdbc&password=jdbc";
    private static final CommentDAO instance = new CommentDAO();
    
    private CommentDAO(){      
    }
    
    public static CommentDAO getInstance(){
        return instance;
    }
        
    public boolean addMelding(Melding m){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("INSERT INTO Melding VALUES (?,?,?)");
            stat.setInt(1, m.getId());
            stat.setString(2, m.getType());
            stat.setString(3, m.getBeschrijving());
            stat.executeUpdate();
            return true;
        }catch (SQLException ex){
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
        
    }
    
    public boolean addMeldingen(Melding... meldingen) {
        boolean success = true;
        for (Melding m : meldingen) {
            success = success && addMelding(m);
        }
        return success;
    }
    
    public Melding getMelding(int id){
        Melding m = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT FROM Melding WHERE ID = ?");
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int i = rs.getInt("ID");
                    String t = rs.getString("Type");
                    String b = rs.getString("Beschrijving");
                                        
                    m =  new Melding(i,t,b);
                }
            }
            
        }catch (SQLException ex){
           for (Throwable t : ex) {
                t.printStackTrace();
            } 
        }
        return m;
    }
    
    public List<Melding> getMeldingen(int... ids) {
        List<Melding> meldingen = new ArrayList<>();
        
        for (int id : ids) {
            Melding m = getMelding(id);
            if (m != null) {
                meldingen.add(m);
            }
        }
        
        return meldingen;
    }
    
     public List<Melding> getAllMeldingen() {
        List<Melding> meldingen = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM Melding");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int nummer = rs.getInt("ID");
                    String t = rs.getString("Type");
                    String b = rs.getString("Beschrijving");
                    
                    
                    meldingen.add(new Melding(nummer, t, b));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return meldingen;
     }
     
     public boolean updateMelding(Melding m) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("UPDATE Melding SET Type = ?, Beschrijving = ? WHERE ID = ?");
            stat.setString(1, m.getType());
            stat.setString(2, m.getBeschrijving());
            stat.setInt(3, m.getId());
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
     }
     
    public boolean updateMeldingen(Melding... meldingen) {
        boolean success = true;
        for (Melding m : meldingen) {
            success = success && updateMelding(m);
        }
        return success;
    }
    
    public boolean deleteMelding(Melding m){
        return deleteMelding(m.getId());
    }
    
    public boolean deleteMelding(int id){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM Melding WHERE ID = ?");
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
    
    public boolean deleteMeldingen(Melding... meldingen) {
        boolean success = true;
        for (Melding m : meldingen) {
            success = success && deleteMelding(m);
        }
        return success;
    }
    
    public boolean deleteMeldingen(int... ids) {
        boolean success = true;
        for (int id : ids) {
            success = success && deleteMelding(id);
        }
        return success;
    }
}
