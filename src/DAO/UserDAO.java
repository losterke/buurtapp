/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domein.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tim
 */
public class UserDAO {
    private static final String JDBC_URL_MYSQL = "jdbc:mysql://localhost:3306/BuurtAppDB?user=jdbc&password=jdbc";
    private static final UserDAO instance = new UserDAO();
    
    private UserDAO(){      
    }
    
    public static UserDAO getInstance(){
        return instance;
    }
        
    public boolean addUser(User u){
        try (
                            
            Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?)");
            stat.setInt(1, u.getId());
            stat.setString(2, u.getNaam());
            stat.setString(3, u.getVoornaam());
            stat.setString(4, u.getAdres());
            stat.setBytes(5, u.getFoto());
            stat.setInt(6, u.getPunten());
            stat.executeUpdate();
            return true;
        }catch (SQLException ex){
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        } 
        
        
    }
    
    public boolean addUsers(User... users) {
        boolean success = true;
        for (User u : users) {
            success = success && addUser(u);
        }
        return success;
    }
    
    public User getUser(int id){
        User u = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT FROM User WHERE ID = ?");
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int i = rs.getInt("ID");
                    String n = rs.getString("Naam");
                    String vn = rs.getString("Voornaam");
                    String a = rs.getString("Adres");
                    byte[] f = rs.getBytes("Foto");
                    int ptn = rs.getInt("Punten");
            
                                        
                    u =  new User(i,n,vn,a,f,ptn);
                }
            }
            
        }catch (SQLException ex){
           for (Throwable t : ex) {
                t.printStackTrace();
            } 
        }
        return u;
    }
    
    public List<User> getUsers(int... ids) {
        List<User> users = new ArrayList<>();
        
        for (int id : ids) {
            User u = getUser(id);
            if (u != null) {
                users.add(u);
            }
        }
        
        return users;
    }
    
     public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM User");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int i = rs.getInt("ID");
                    String n = rs.getString("Naam");
                    String vn = rs.getString("Voornaam");
                    String a = rs.getString("Adres");
                    byte[] f = rs.getBytes("Foto");
                    int ptn = rs.getInt("Punten");
                    
                    
                    users.add(new User(i,n,vn,a,f,ptn));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return users;
     }
     
     public boolean updateUser(User u) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("UPDATE User SET Naam = ?, Voornaam = ?, Adres = ?, Foto = ?, Punten = ? WHERE ID = ?");
            stat.setInt(1, u.getId());
            stat.setString(2, u.getNaam());
            stat.setString(3, u.getVoornaam());
            stat.setString(4, u.getAdres());
            stat.setBytes(5, u.getFoto());
            stat.setInt(6, u.getPunten());
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
     }
     
    public boolean updateUsers(User... users) {
        boolean success = true;
        for (User u : users) {
            success = success && updateUser(u);
        }
        return success;
    }
    
    public boolean deleteUser(User u){
        return deleteUser(u.getId());
    }
    
    public boolean deleteUser(int id){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM User WHERE ID = ?");
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
    
    public boolean deleteUsers(User... users) {
        boolean success = true;
        for (User u : users) {
            success = success && deleteUser(u);
        }
        return success;
    }
    
    public boolean deleteUsers(int... ids) {
        boolean success = true;
        for (int id : ids) {
            success = success && deleteUser(id);
        }
        return success;
    }
}
