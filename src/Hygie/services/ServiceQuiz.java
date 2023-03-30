/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hygie.services;

import Hygie.entities.Questionnaire;
import Hygie.utils.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khaled
 */
public class ServiceQuiz implements QuizService<Questionnaire>{
     
    Connection con ; 
    Statement ste;
     
    
    
    
    
    
    public ServiceQuiz() {
        
        con = MyDB.createorgetInstance().getCon();
        
    }

    @Override
    public void Ajouter(Questionnaire t) {
        
           
        try {
            
            //1 creer le statement 
            ste = con.createStatement();
            
            String req = "INSERT INTO `pppppp`.`Questionnaire` (`nom`,`date`) VALUES ('"+t.getNom()+"','"+t.getDate()+"');";

            
            ste.executeUpdate(req);
            
            
        } catch (SQLException ex) {
            System.err.println("ddd");
            System.out.println(ex.getMessage());
        }
        
        
    }

    @Override
    public List<Questionnaire> getAll() {
    ArrayList<Questionnaire> pers = new ArrayList<>();
        try {
            ste =con.createStatement();
            String req = "SELECT * FROM `Questionnaire`";
            ResultSet res =ste.executeQuery(req);
            
            while(res.next()){
                int id = res.getInt("id");
                String nom = res.getString(2);
                Timestamp date = res.getTimestamp(4);

                
                
                Questionnaire p = new Questionnaire(id,date.toLocalDateTime(),nom);
                pers.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return  pers;
    }

    @Override
    public void supprimer(Questionnaire t) {

         String sql = "delete from Questionnaire where id =?";
        try {
            PreparedStatement ste = con.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }
    
    
    
            public void modifier(String nom,Questionnaire t) 
            {
                   String sql = "update Questionnaire set nom=? where id=?";
        try {
            PreparedStatement ste = con.prepareStatement(sql);
            ste.setString(1, nom);
            ste.setInt(2,t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

                
            }

}
