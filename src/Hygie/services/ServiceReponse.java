/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.services;

import hygie.entities.Questionnaire;
import hygie.entities.Questions;
import hygie.entities.Reponse;
import hygie.utils.MyDB;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Khaled
 */
public class ServiceReponse implements QuizService<Reponse>{

     Connection con ;
    Statement ste;

    public ServiceReponse() {
                con = MyDB.createorgetInstance().getCon();

    }


            @Override
    public void Ajouter(Reponse t) {

        int type = 0 ; 
        try {
            ste = con.createStatement();
            int idquestion=t.getQuestionid().getId() ;
            System.out.println(idquestion);
            if(t.isType())
                type=1 ; 
            

            String req = "INSERT INTO `pppppp`.`Reponse` (`questionid_id`, `reponsee`, `type`) VALUES ('"+idquestion+"','"+t.getReponsee()+"','"+type+"');";


            ste.executeUpdate(req);


        }  catch (SQLException ex) {
            System.err.println("Reponse exception");
            System.out.println(ex.getMessage());
        }



    }

    @Override
    public List<Reponse> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
      public void supprimer(Reponse t) {



        //
        String sql = "delete from Reponse where id =?";
        try {
            PreparedStatement ste = con.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        //


    }
        public List<Map<String, Object>> getAll2() {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            ste =con.createStatement();

            // Exécution de la requête SQL pour récupérer les données de la jointure
            ResultSet rs = ste.executeQuery("SELECT reponse.id as reponseid , questions.question as question, questions.point as point, reponse.reponsee as reponse, reponse.type as type FROM questions JOIN reponse ON reponse.questionid_id = questions.id");
            // Parcours du résultat de la requête et stockage dans une liste de Map
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("reponseid", rs.getInt("reponseid"));
                row.put("reponse", rs.getString("reponse"));
                //row.put("questionnaire_date", rs.getObject("questionnaire_date", LocalDateTime.class));
                row.put("point", rs.getInt("point"));
                row.put("question", rs.getString("question"));
                if(rs.getInt("type")==1)
                row.put("type",  "True");
                else {
                row.put("type",  "False");

                }

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
           public List<Map<String, Object>> getbyquestion(int x) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            ste =con.createStatement();

            // Exécution de la requête SQL pour récupérer les données de la jointure
            ResultSet rs = ste.executeQuery("SELECT reponse.id as reponseid , questions.question as question, questions.point as point, reponse.reponsee as reponse, reponse.type as type FROM questions JOIN reponse ON reponse.questionid_id = questions.id  where  questions.id = "+ x);
            // Parcours du résultat de la requête et stockage dans une liste de Map
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("reponseid", rs.getInt("reponseid"));
                row.put("reponse", rs.getString("reponse"));
                //row.put("questionnaire_date", rs.getObject("questionnaire_date", LocalDateTime.class));
                row.put("point", rs.getInt("point"));
                row.put("question", rs.getString("question"));
                if(rs.getInt("type")==1)
                row.put("type",  "True");
                else {
                row.put("type",  "False");

                }

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
     
        
         public void modifier(Reponse t)
    {
        String sql = "update Reponse set reponsee=? , type=? where id=?";
        try {
            PreparedStatement ste = con.prepareStatement(sql);
            ste.setString(1, t.getReponsee());
            ste.setBoolean(2, t.isType());

            ste.setInt(3,t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }


    
}
