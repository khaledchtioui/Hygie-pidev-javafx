package hygie.services;


import hygie.entities.Questions;
import hygie.utils.MyDB;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ServiceQuestion implements QuizService<Questions>{



    Connection con ;
    Statement ste;






    public ServiceQuestion() {

        con = MyDB.createorgetInstance().getCon();

    }



    @Override
    public void Ajouter(Questions t) {

        try {




            //1 creer le statement
            ste = con.createStatement();
            int idQuiz=t.getQuizid().getId() ;
            System.out.println(idQuiz);

            String req = "INSERT INTO `pppppp`.`Questions` (`question`,`quizid_id`,`type`,`point`) VALUES ('"+t.getQuestion()+"','"+idQuiz+"','"+t.getType()+"','"+t.getPoint()+"');";


            ste.executeUpdate(req);


        }  catch (SQLException ex) {
            System.err.println("Questions exception");
            System.out.println(ex.getMessage());
        }



    }


    @Override
    public void supprimer(Questions t) {



        //
        String sql = "delete from Questions where id =?";
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
            ResultSet rs = ste.executeQuery("SELECT questionnaire.id AS questionnaire_id, questionnaire.nom AS questionnaire_nom, questionnaire.date AS questionnaire_date, questions.id AS question_id, questions.question AS question_question, questions.type AS question_type, questions.point AS question_point FROM questionnaire JOIN questions ON questionnaire.id = questions.quizid_id");
            // Parcours du résultat de la requête et stockage dans une liste de Map
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("questionnaire_id", rs.getInt("questionnaire_id"));
                row.put("questionnaire_nom", rs.getString("questionnaire_nom"));
                //row.put("questionnaire_date", rs.getObject("questionnaire_date", LocalDateTime.class));
                row.put("question_id", rs.getInt("question_id"));
                row.put("question_question", rs.getString("question_question"));
                if( rs.getInt("question_type")==1)
                {
                    row.put("question_type", "Choix Unique");

                }
                else
                {
                    row.put("question_type", "Choix Multuple");

                }
                row.put("question_point", rs.getInt("question_point"));
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

    public List<Map<String, Object>> getAllbyquiz(int id) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            ste =con.createStatement();

            // Exécution de la requête SQL pour récupérer les données de la jointure
            ResultSet rs = ste.executeQuery("SELECT questionnaire.id AS questionnaire_id, questionnaire.nom AS questionnaire_nom, questionnaire.date AS questionnaire_date, questions.id AS question_id, questions.question AS question_question, questions.type AS question_type, questions.point AS question_point FROM questionnaire JOIN questions ON questionnaire.id = questions.quizid_id  where  questionnaire.id= " +id);
            // Parcours du résultat de la requête et stockage dans une liste de Map
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("questionnaire_id", rs.getInt("questionnaire_id"));
                row.put("questionnaire_nom", rs.getString("questionnaire_nom"));
                //row.put("questionnaire_date", rs.getObject("questionnaire_date", LocalDateTime.class));
                row.put("question_id", rs.getInt("question_id"));
                row.put("question_question", rs.getString("question_question"));
                if( rs.getInt("question_type")==1)
                {
                    row.put("question_type", "Choix Unique");

                }
                else
                {
                    row.put("question_type", "Choix Multuple");

                }
                row.put("question_point", rs.getInt("question_point"));
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



    public void modifier(String question,int type,int point ,Questions t)
    {
        String sql = "update Questions set question=? ,type=?,point=? where id=?";

        try {
            PreparedStatement ste = con.prepareStatement(sql);
            ste.setString(1, question);
            ste.setInt(2,type);
            ste.setInt(3,point);
            ste.setInt(4,t.getId())  ;
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }



    @Override
    public List<Questions> getAll() {
        System.err.println("err");   return null;
    }

}
