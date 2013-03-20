/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Domein;

import java.sql.Date;

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
    private User[] deelnemers;
    private Comment Comment;

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

    public User[] getDeelnemers() {
        return deelnemers;
    }

    public void setDeelnemers(User[] deelnemers) {
        this.deelnemers = deelnemers;
    }

    public Comment getComment() {
        return Comment;
    }

    public void setComment(Comment Comment) {
        this.Comment = Comment;
    }    
    
}
