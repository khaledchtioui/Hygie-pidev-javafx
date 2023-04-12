/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafxapplication1.entities.Commentaire;
import javafxapplication1.utils.MyDB;

/**
 *
 * @author Yassine
 */
public class ComService  implements ICommentaire<Commentaire>{
    
      Connection con ; 
    Statement ste;
     
    
    
    
    
    
    public ComService() {
        
        con = MyDB.createorgetInstance().getCon();
        
    }

    @Override
    public void Ajouter(Commentaire t) {
        try {
        // create the prepared statement
        String query = "INSERT INTO commentaire (article_id, contenue , date_creation_com) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);

        LocalDateTime localDateTime = LocalDateTime.now(); // get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // create a formatter for the desired format
        String formattedDateTime = localDateTime.format(formatter); //
        
        // set the values of the parameters
        ps.setInt(1, t.getArticle_id());
        ps.setString(2, t.getContenue());
    
        ps.setString(3, formattedDateTime); // set the formatted date and time as a string
     
       

        // execute the insert statement
        ps.executeUpdate();
        
        // close the statement
        ps.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    

    @Override
    public void Supprimer(Commentaire t) {
                try {
                // create the prepared statement
                String query = "DELETE FROM commentaire WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);

                // set the value of the parameter
                ps.setInt(1, t.getId());

                // execute the delete statement
                ps.executeUpdate();

                // close the statement
                ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    
    @Override
    public ArrayList<Commentaire> Afficher(int id) {
        ArrayList<Commentaire> commentaires = new ArrayList<>();
        try {
            // create the prepared statement
            String query = "SELECT * FROM commentaire WHERE article_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            // set the value of the parameter
            ps.setInt(1, id);

            // execute the query
            ResultSet rs = ps.executeQuery();

            // iterate through the result set and create Commentaire objects
            while (rs.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(rs.getInt("id"));
                commentaire.setArticle_id(rs.getInt("article_id"));
                commentaire.setContenue(rs.getString("contenue"));
                commentaire.setDateCreation(rs.getTimestamp("date_creation_com"));
                commentaires.add(commentaire);
            }

            // close the result set and statement
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return commentaires;
    }

  
    
    
}
