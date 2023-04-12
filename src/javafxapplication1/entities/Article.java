/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.entities;

import java.util.Date;

/**
 *
 * @author Yassine
 */
public class Article {
    
    private int id;
    
    private String titre;
    private String description;
    private Date dateCreation;
    private String categorie;
    private String image ;  
    private boolean published;
    
    public Article() {
        
        
        
    }
    


    public Article(int id,String titre, String description, String categorie) {
        this.id = id;
         this.titre = titre;
        this.description = description;
       
        this.categorie = categorie;
    }
  
        public Article(String titre, String description, String categorie) {
     
        this.titre = titre;
        this.description = description;
       
        this.categorie = categorie;
      
    }

    public Article(int id, String titre, String description, Date dateCreation, String categorie, String image) {
             this.id = id;
              this.titre = titre;
              this.description = description;
          this.dateCreation = dateCreation;
                 this.categorie = categorie;
                      this.image = image;
    }
    
        public Article(String titre, String description, String categorie,  String image) {
        
              this.titre = titre;
              this.description = description;
       
                 this.categorie = categorie;
                      this.image = image;
    }

    public Article(int id, String titre, String description, Date dateCreation, String categorie, String image, boolean published) {
           this.id = id;
              this.titre = titre;
              this.description = description;
          this.dateCreation = dateCreation;
                 this.categorie = categorie;
                      this.image = image;
                      this.published=published;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

      // getter for published field
    public boolean isPublished() {
        return published;
    }
    
    // setter for published field
    public void setPublished(boolean published) {
        this.published = published;
    }
   
    @Override
    public String toString() {
        return "article{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", dateCreation=" + dateCreation + ", categorie=" + categorie + ", image=" + image +  '}';
    }
    
    
    
}
