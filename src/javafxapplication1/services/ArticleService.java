/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.services;

import javafxapplication1.entities.Article;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxapplication1.utils.MyDB;

/**
 *
 * @author Yassine
 */
public class ArticleService implements IArticle<Article>{
    
    Connection con ; 
    Statement ste;
     
    
    
    
    
    
    public ArticleService() {
        
        con = MyDB.createorgetInstance().getCon();
        
    }

   @Override
public void Ajouter(Article t) {
    try {
        // create the prepared statement
        String query = "INSERT INTO article (titre, description, categorie, date_creation, image , published) VALUES (?, ?, ?, ?, ? ,?)";
        PreparedStatement ps = con.prepareStatement(query);

        LocalDateTime localDateTime = LocalDateTime.now(); // get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // create a formatter for the desired format
        String formattedDateTime = localDateTime.format(formatter); //
        // set the values of the parameters
        ps.setString(1, t.getTitre());
        ps.setString(2, t.getDescription());
        ps.setString(3, t.getCategorie());
        ps.setString(4, formattedDateTime); // set the formatted date and time as a string
        ps.setString(5, t.getImage());
        ps.setBoolean(6, true);

        // execute the insert statement
        ps.executeUpdate();
        
        // close the statement
        ps.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}



@Override
public void Modifier(Article t) {
    try {
        // create the prepared statement
        String query = "UPDATE article SET titre=?, description=?, categorie=?, image=?, published=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);

        // set the values of the parameters
        ps.setString(1, t.getTitre());
        ps.setString(2, t.getDescription());
        ps.setString(3, t.getCategorie());
        ps.setString(4, t.getImage());
        ps.setBoolean(5, t.isPublished());
        ps.setInt(6, t.getId());

        // execute the update statement
        ps.executeUpdate();

        // close the statement
        ps.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

@Override
public void Supprimer(Article t) {
    try {
        // create the prepared statement
        String query = "DELETE FROM article WHERE id=?";
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
public ArrayList<Article> Afficher() {
    ArrayList<Article> articles = new ArrayList<>();
    try {
        ste =con.createStatement();
        String req = "SELECT * FROM `article`";
        ResultSet res =ste.executeQuery(req);

        while(res.next()){
            int id = res.getInt("id");
            String titre = res.getString("titre");
            String description = res.getString("description");
            Date dateCreation = res.getDate("date_creation");
            String categorie = res.getString("categorie");
            String image = res.getString("image");
             boolean published = res.getBoolean("published");
             
            Article art = new Article(id, titre, description, dateCreation, categorie, image, published);
            articles.add(art);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return articles;
}


}