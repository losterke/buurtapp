/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buurtapp;

import java.util.List;

/**
 *
 * @author tim
 */
public class Buurtapp {

    
    
    public static void main(String[] args) {
        
        MeldingDAO Mdao = MeldingDAO.getInstance();
        
        Melding m1 = new Melding (1, "vandalisme", "Graffiti op de muur gespoten.");
        Melding m2 = new Melding (2, "onderhoud", "Een put in de weg.");
        
        Mdao.addMeldingen(m1,m2);
        
        List<Melding> allMeldingen = Mdao.getAllMeldingen();
        for (Melding m : allMeldingen) {
            System.out.printf("Melding gevonden met nummer: %d, type: %s en beschrijving: %s%n", m.getId(), m.getType(), m.getBeschrijving());
        }
        
        m2.setBeschrijving("Een put in het wegdek.");
        
        Mdao.updateMelding(m2);
        
        allMeldingen = Mdao.getAllMeldingen();
        for (Melding m : allMeldingen) {
            System.out.printf("Melding gevonden met nummer: %d, type: %s en beschrijving: %s%n", m.getId(), m.getType(), m.getBeschrijving());
        }
        
        Mdao.deleteMeldingen(m1,m2);       
        
    }
}
