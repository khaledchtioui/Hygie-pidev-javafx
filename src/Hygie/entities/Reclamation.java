/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.entities;


import java.time.LocalDateTime;

/**
 *
 * @author abedj
 */
public class Reclamation {
    
    private int id ;
    private String titre ;
    private String type ;
    private String descreption ;
    private LocalDateTime date  ;
    private String status ;
    
    
     public Reclamation(int id ,LocalDateTime date, String titre, String type, String descreption,String status) {
        this.id = id;
        this.date=date ;
        this.titre = titre;
        this.type = type;
        this.descreption = descreption;
        this.status = status;
        
    }
      public Reclamation(int id , String titre, String type, String descreption,String status) {
        this.id = id;
   
        this.titre = titre;
        this.type = type;
        this.descreption = descreption;
        this.status = status;
        
    }
     
      public Reclamation(LocalDateTime date, String titre, String type, String descreption,String status) {
        this.date=date ;
        this.titre = titre;
        this.type = type;
        this.descreption = descreption;
        this.status = status;
        
    }

    public Reclamation(String titre, String type, String descreption, String status) {
        this.titre = titre;
        this.type = type;
        this.descreption = descreption;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }
    
     public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getDescreption() {
        return descreption;
    }
    
    
    public void setDescription(String descreption) {
        this.descreption = descreption;
    }
    

    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    

  

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", titre=" + titre + ", type=" + type + ", descreption=" + descreption + ", date=" + date + ", status=" + status + '}';
    }
      
    
    
    
      
      

    
    
    
   
    
}
