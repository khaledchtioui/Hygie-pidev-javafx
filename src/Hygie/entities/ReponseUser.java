/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.entities;

import java.util.logging.Logger;

/**
 *
 * @author Khaled
 */
public class ReponseUser {
    private Reponse rep_id;

    public ReponseUser(Reponse rep_id) {
        this.rep_id = rep_id;
    }
   // private static final Logger LOG = Logger.getLogger(ReponseUser.class.getName());

    public Reponse getRep_id() {
        return rep_id;
    }

    public void setRep_id(Reponse rep_id) {
        this.rep_id = rep_id;
    }

    
    
    
}
