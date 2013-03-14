/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buurtapp;

/**
 *
 * @author tim
 */
public class Melding {
    
    private int id;
    private String type;
    private String beschrijving;
    
    public Melding(int id,String type, String beschrijving){
        setId(id);
        setType(type);
        setBeschrijving(beschrijving);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Id moet > 0");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type != null && !type.trim().equals("")) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Het type van deze melding kan niet leeg zijn.");
        }
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        if (beschrijving != null && !beschrijving.trim().equals("")) {
            this.beschrijving = beschrijving;
        } else {
            throw new IllegalArgumentException("De beschrijving van u melding mag niet leeg zijn.");
        }
    }
    
}
