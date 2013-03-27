/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Domein;

import DAO.CommentDAO;
import DAO.FotoDAO;
import DAO.UserDAO;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author tim
 */
public class Event {
    
    private int id;
    private String naam;
    private String locatie;
    private Date start;
    private Date end;
    private User auteur;
    private Foto foto;
    private List<User> deelnemers;
    private Comment Comment;
    private UserDAO udao = UserDAO.getInstance();
    private FotoDAO fdao = FotoDAO.getInstance();
    private CommentDAO cdao = CommentDAO.getInstance();

    public Event(int i, String n, String l, Date s, Date ei, int a, int f, int d, int c) {
        setId(i);
        setNaam(n);
        setLocatie(l);
        setStart(s);
        setEnd(ei);
        setAuteur(udao.getUser(a));
        setFoto(fdao.getFoto(f));
        setDeelnemers(udao.getUsers(d));
        setComment(cdao.getComment(c));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public User getAuteur() {
        return auteur;
    }

    public void setAuteur(User auteur) {
        this.auteur = auteur;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public List<User> getDeelnemers() {
        return deelnemers;
    }

    public void setDeelnemers(List<User> deelnemers) {
        this.deelnemers = deelnemers;
    }

    public Comment getComment() {
        return Comment;
    }

    public void setComment(Comment Comment) {
        this.Comment = Comment;
    }    
    
}
