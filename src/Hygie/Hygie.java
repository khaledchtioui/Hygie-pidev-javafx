/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie;

import hygie.entities.Reclamation;
import hygie.services.ServiceReclamation;
import java.time.LocalDateTime;

/**
 *
 * @author abedj
 */
public class Hygie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
           Reclamation r5 = new Reclamation(LocalDateTime.now(), "xxxxx","xxxx","SDFxxxDSF","SDxxhxFDSF");
        Reclamation r6 = new Reclamation(2,LocalDateTime.now(), "bbb", "bbbb","aaabbbaa","bbbbb");
        ServiceReclamation sr = new ServiceReclamation();
        
      
        sr.modifier(r6);
       
  //System.out.println(sr.getAll());
  
         
  
  //System.out.println(sr.getOneById(2));
        
    }
    
}
