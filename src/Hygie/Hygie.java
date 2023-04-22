/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie;

import hygie.entities.Questionnaire;
import hygie.entities.Questions;
import hygie.entities.Reponse;
import hygie.services.ServiceReponse;

/**
 *
 * @author Khaled
 */
public class Hygie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
      
        ServiceReponse sr = new ServiceReponse()  ;
        //sr.Ajouter(r1);
        Reponse r = new Reponse("ss",true,new Questions(40))  ;
        sr.Ajouter(r);
         System.out.println(sr.getAll2()); 
    }
    
}
