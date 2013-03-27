/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domein.Comment;
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
        
    public boolean addComment(Comment c){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("INSERT INTO Comment VALUES (?,?,?)");
            stat.setInt(1, c.getId());
            stat.setString(2, c.getInhoud());
            stat.setInt(3, c.getAuteur().getId());
            stat.executeUpdate();
            return true;
        }catch (SQLException ex){
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
        
    }
    
    public boolean addComments(Comment... comments) {
        boolean success = true;
        for (Comment c : comments) {
            success = success && addComment(c);
        }
        return success;
    }
    
    public Comment getComment(int id){
        Comment c = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT FROM Comment WHERE ID = ?");
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int i = rs.getInt("ID");
                    String in = rs.getString("Inhoud");
                    int a = rs.getInt("Auteur");
                                        
                    c =  new Comment(i,in,a);
                }
            }
            
        }catch (SQLException ex){
           for (Throwable t : ex) {
                t.printStackTrace();
            } 
        }
        return c;
    }
    
    public List<Comment> getComments(int... ids) {
        List<Comment> comments = new ArrayList<>();
        
        for (int id : ids) {
            Comment c = getComment(id);
            if (c != null) {
                comments.add(c);
            }
        }
        
        return comments;
    }
    
     public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM Comment");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int i = rs.getInt("ID");
                    String in = rs.getString("Inhoud");
                    int a = rs.getInt("Auteur");
                    
                    
                    comments.add(new Comment(i, in, a));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return comments;
     }
     
     public boolean updateComment(Comment c) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("UPDATE Comment SET Inhoud = ?, Auteur = ? WHERE ID = ?");
            stat.setString(1, c.getInhoud());
            stat.setInt(2, c.getAuteur().getId());
            stat.setInt(3, c.getId());
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
     }
     
    public boolean updateComments(Comment... comments) {
        boolean success = true;
        for (Comment m : comments) {
            success = success && updateComment(m);
        }
        return success;
    }
    
    public boolean deleteComment(Comment c){
        return deleteComment(c.getId());
    }
    
    public boolean deleteComment(int id){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM Comment WHERE ID = ?");
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
    
    public boolean deleteComments(Comment... comments) {
        boolean success = true;
        for (Comment m : comments) {
            success = success && deleteComment(m);
        }
        return success;
    }
    
    public boolean deleteComments(int... ids) {
        boolean success = true;
        for (int id : ids) {
            success = success && deleteComment(id);
        }
        return success;
    }
}
