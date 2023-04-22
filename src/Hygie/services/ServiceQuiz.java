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
import java.util.List;


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

}
