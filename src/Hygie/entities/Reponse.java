/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.entities;

/**
 *
 * @author Khaled
 */
public class Reponse {
   
   
    private int id ; 
    private String reponsee ; 
    private boolean   type ; 
    private int src1 ; 
    private int src2 ; 
    private Questions questionid ;

    public Reponse(int id, String reponsee, boolean type, int src1, int src2, Questions questionid) {
        this.id = id;
        this.reponsee = reponsee;
        this.type = type;
        this.src1 = src1;
        this.src2 = src2;
        this.questionid = questionid;
    }

    public Reponse(int id, String reponsee, boolean type, Questions questionid) {
        this.id = id;
        this.reponsee = reponsee;
        this.type = type;
        this.questionid = questionid;
    }

    public Reponse(String reponsee, boolean type, Questions questionid) {
        this.reponsee = reponsee;
        this.type = type;
        this.questionid = questionid;
    }



    @Override
    public String toString() {
        return "Reponse{" + "reponsee=" + reponsee + ", type=" + type + ", questionid=" + questionid + '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReponsee(String reponsee) {
        this.reponsee = reponsee;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setSrc1(int src1) {
        this.src1 = src1;
    }

    public void setSrc2(int src2) {
        this.src2 = src2;
    }

    public void setQuestionid(Questions questionid) {
        this.questionid = questionid;
    }

    public Reponse(int id, String reponsee, boolean type) {
        this.id = id;
        this.reponsee = reponsee;
        this.type = type;
    }

    public Reponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getReponsee() {
        return reponsee;
    }

    public boolean isType() {
        return type;
    }

    public int getSrc1() {
        return src1;
    }

    public int getSrc2() {
        return src2;
    }

    public Questions getQuestionid() {
        return questionid;
    }
    
    
    
    
    
}
