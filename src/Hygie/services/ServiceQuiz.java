package hygie.services;


import hygie.entities.Questionnaire;
import hygie.utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


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

       
    public List<Questionnaire> getAllsearch(String recherche_nom) {
        ArrayList<Questionnaire> pers = new ArrayList<>();
        try {
            ste =con.createStatement();
            String req = "SELECT * FROM questionnaire \n" +
"WHERE nom LIKE '%"+recherche_nom+"%' ";
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

    
    
        public List<Map<String, Object>> getAllbyquiz(int id) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            ste =con.createStatement();

            // Exécution de la requête SQL pour récupérer les données de la jointure
            ResultSet rs = ste.executeQuery("SELECT q.id AS questionnaire_id, q.nom AS questionnaire_name, q.date AS questionnaire_date,\n" +
"       qs.id AS question_id, qs.question AS question_text, qs.type AS question_type, qs.point AS question_point,\n" +
"       r.id AS response_id, r.reponsee AS response_text, r.type AS response_type\n" +
"FROM questionnaire q\n" +
"JOIN questions qs ON qs.quizid_id = q.id\n" +
"LEFT JOIN reponse r ON r.questionid_id = qs.id\n" +
"WHERE q.id=60\n" +
"ORDER BY q.id, qs.id, r.id;\n" +
"");
            // Parcours du résultat de la requête et stockage dans une liste de Map
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("questionnaire_id", rs.getInt("questionnaire_id"));
                row.put("questionnaire_name", rs.getString("questionnaire_name"));
                //row.put("questionnaire_date", rs.getObject("questionnaire_date", LocalDateTime.class));
                row.put("question_id", rs.getInt("question_id"));
                row.put("question_text", rs.getString("question_text"));             
                row.put("question_type",rs.getInt("question_type"));
                row.put("question_point", rs.getInt("question_point"));
                row.put("reponse_text", rs.getString("response_text"));
                row.put("response_type", rs.getBoolean("response_type"));
             
                
                resultList.add(row);
            }

            // Fermeture du ResultSet
            rs.close();
        } catch (SQLException ex) {
            // Gestion des exceptions
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Fermeture de la connexion à la base de données
            if (ste != null) {
                try {
                    ste.close();
                } catch (SQLException ex) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }

        return resultList;
    }
        public List<Map<String, Object>> getAllbyquiz2(int id) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            ste =con.createStatement();

            // Exécution de la requête SQL pour récupérer les données de la jointure
            ResultSet rs = ste.executeQuery("SELECT q.id AS questionnaire_id, q.nom AS questionnaire_name, q.date AS questionnaire_date,\n" +
"       qs.id AS question_id, qs.question AS question_text, qs.type AS question_type, qs.point AS question_point,\n" +
"       r.id AS response_id, r.reponsee AS response_text, r.type AS response_type\n" +
"FROM questionnaire q\n" +
"JOIN questions qs ON qs.quizid_id = q.id\n" +
"LEFT JOIN reponse r ON r.questionid_id = qs.id\n" +
"WHERE q.id="+id+"\n" +
"ORDER BY q.id, qs.id, r.id;\n" +
"");
            // Parcours du résultat de la requête et stockage dans une liste de Map
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("questionnaire_id", rs.getInt("questionnaire_id"));
                row.put("questionnaire_name", rs.getString("questionnaire_name"));
                //row.put("questionnaire_date", rs.getObject("questionnaire_date", LocalDateTime.class));
                row.put("question_id", rs.getInt("question_id"));
                row.put("question_text", rs.getString("question_text"));             
                row.put("question_type",rs.getInt("question_type"));
                row.put("question_point", rs.getInt("question_point"));
                row.put("reponse_text", rs.getString("response_text"));
                row.put("response_type", rs.getBoolean("response_type"));
             
                
                resultList.add(row);
            }

            // Fermeture du ResultSet
            rs.close();
        } catch (SQLException ex) {
            // Gestion des exceptions
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Fermeture de la connexion à la base de données
            if (ste != null) {
                try {
                    ste.close();
                } catch (SQLException ex) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }

        return resultList;
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
        String sql = "update Questionnaire set nom=? , date=? where id=?";
        try {
            PreparedStatement ste = con.prepareStatement(sql);
            ste.setString(1, nom);
            ste.setString(2, t.getDate().toString());

            ste.setInt(3,t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }
    
    
    
    public void showNotification(String message,int x) {
            Rectangle rectangle = new Rectangle(400, 50);

           rectangle.setFill(Color.GREEN);

    Label label = new Label(message);
    if(x==0)
    rectangle.setFill(Color.RED);

        
   

    StackPane stackPane = new StackPane(rectangle, label);
    stackPane.setAlignment(Pos.CENTER);

    Notifications notifications = Notifications.create()
            .hideAfter(Duration.seconds(6))
            .position(Pos.BOTTOM_RIGHT)
            .darkStyle()
            .graphic(stackPane)
            .title("Notification");

    notifications.show();
}

    public void showNotification(String pdf_file_created_and_saved_successfully) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
