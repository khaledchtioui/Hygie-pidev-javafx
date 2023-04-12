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
public class Commentaire {
    
    private int id;
    private int article_id;
    private String contenue ;
    private Date dateCreation;

    public Commentaire(int id, int article_id, String contenue, Date dateCreation) {
        this.id = id;
        this.article_id = article_id;
        this.contenue = contenue;
        this.dateCreation = dateCreation;
    }
    
      public Commentaire() {

    }

    public Commentaire(int article_id, String contenue, Date dateCreation) {
        this.article_id = article_id;
        this.contenue = contenue;
        this.dateCreation = dateCreation;
    }
    
    public Commentaire(String contenue,int article_id) {
        this.article_id = article_id;
        this.contenue = contenue;
     
    }

      
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", article_id=" + article_id + ", contenue=" + contenue + ", dateCreation=" + dateCreation + '}';
    }
    
    
}
