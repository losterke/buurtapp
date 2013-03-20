/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domein.Foto;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author tim
 */
public class FotoDAO {
    private static final String JDBC_URL_MYSQL = "jdbc:mysql://localhost:3306/BuurtAppDB?user=jdbc&password=jdbc";
    private static final FotoDAO instance = new FotoDAO();
    
    private FotoDAO(){      
    }
    
    public static FotoDAO getInstance(){
        return instance;
    }
        
    public boolean addFoto(Foto f){
        try (
            Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            
            FileInputStream fis = new FileInputStream(f.getFoto());
            
            PreparedStatement stat = conn.prepareStatement("INSERT INTO Foto VALUES (?,?,?)");
            stat.setInt(1, f.getId());
            stat.setBinaryStream(2, fis);
            stat.setInt(3, f.getAuteur().getId());
            stat.executeUpdate();
            return true;
        }catch (SQLException ex){
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }catch (FileNotFoundException e){
            e.printStackTrace(); 
            return false;
        }
        
    }
    
    public boolean addFotos(Foto... fotos) {
        boolean success = true;
        for (Foto f : fotos) {
            success = success && addFoto(f);
        }
        return success;
    }
    
    public Foto getFoto(int id){
        Foto m = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT FROM Foto WHERE ID = ?");
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int i = rs.getInt("ID");
                    byte[] f = rs.getBytes("Foto");
                    int a = rs.getInt("Auteur");

                    m =  new Foto(i,ConvertToFile(f, i),a);
                    
                }
            }
            
        }catch (SQLException ex){
           for (Throwable t : ex) {
                t.printStackTrace();
            } 
        }
        return m;
    }
    
    public List<Foto> getFotos(int... ids) {
        List<Foto> fotos = new ArrayList<>();
        
        for (int id : ids) {
            Foto f = getFoto(id);
            if (f != null) {
                fotos.add(f);
            }
        }
        
        return fotos;
    }
    
     public List<Foto> getAllFotos() {
        List<Foto> fotos = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM Foto");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int i = rs.getInt("ID");
                    byte[] f = rs.getBytes("Foto");
                    int a = rs.getInt("Auteur");
                    
                    fotos.add(new Foto(i,ConvertToFile(f, i),a));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return fotos;
     }
     
     public boolean updateFoto(Foto f) {
        try (
            
            FileInputStream fis = new FileInputStream(f.getFoto());   
                
            Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("UPDATE Foto SET Type = ?, Beschrijving = ? WHERE ID = ?");
            stat.setInt(1, f.getId());
            stat.setBinaryStream(2, fis);
            stat.setInt(3, f.getAuteur().getId());
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        } catch (FileNotFoundException e) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
     }
     
    public boolean updateFotos(Foto... fotos) {
        boolean success = true;
        for (Foto f : fotos) {
            success = success && updateFoto(f);
        }
        return success;
    }
    
    public boolean deleteFoto(Foto m){
        return deleteFoto(m.getId());
    }
    
    public boolean deleteFoto(int id){
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM Foto WHERE ID = ?");
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
    
    public boolean deleteFotos(Foto... fotos) {
        boolean success = true;
        for (Foto f : fotos) {
            success = success && deleteFoto(f);
        }
        return success;
    }
    
    public boolean deleteFotos(int... ids) {
        boolean success = true;
        for (int id : ids) {
            success = success && deleteFoto(id);
        }
        return success;
    }
    
    public byte[] scale(byte[] fileData)  {
        byte[] bytes = fileData;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(fileData);
            BufferedImage img = ImageIO.read(in);                
            Image scaledImage = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            bytes = buffer.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytes;
        
    }
    
    public File ConvertToFile(byte[] data, int i){
        try {
            File file = new File("Foto"+i);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();             
            return file;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
