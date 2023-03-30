/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hygie.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Khaled
 */
public class Questionnaire {
    
    
    private int id ; 
    private LocalDateTime date  ;
    private String nom ;
    private String src1 ; 
    private String src2;

    public Questionnaire(int id, String nom, String src1, String src2) {
        this.id = id;
        this.nom = nom;
        this.src1 = src1;
        this.src2 = src2;
        this.date=date ;
    }

    public Questionnaire(String nom) {
        this.nom = nom;
        this.date=LocalDateTime.now()  ;

    }

    public Questionnaire(int id, LocalDateTime date, String nom) {
        this.id = id;
        this.date = date;
        this.nom = nom;
    }

    public Questionnaire(int id, LocalDateTime date, String nom, String src1, String src2) {
        this.id = id;
        this.date = date;
        this.nom = nom;
        this.src1 = src1;
        this.src2 = src2;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Questionnaire{" + "id=" + id + ", date=" + date + ", nom=" + nom + '}';
    }

   
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSrc1() {
        return src1;
    }

    public void setSrc1(String src1) {
        this.src1 = src1;
    }

    public String getSrc2() {
        return src2;
    }

    public void setSrc2(String src2) {
        this.src2 = src2;
    }
    
    
    
}
