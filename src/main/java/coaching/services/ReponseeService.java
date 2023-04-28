package coaching.services;

import coaching.entity.Reponsee;
import coaching.entity.Sujet;
import coaching.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReponseeService {

    private Connection connection;

    public ReponseeService() {
        connection = DataSource.getInstance().getCnx();
    }

    public List<Reponsee> readAssociatedReponses(int sujetId) {
        String requete = "select * from reponsee where sujet_id='" + sujetId + "'";
        List<Reponsee> reponseeList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                Reponsee reponsee = new Reponsee();
                reponsee.setId(resultSet.getInt("id"));
                reponsee.setContenu(resultSet.getString("contenu"));
                reponsee.setDate(resultSet.getDate("date"));
                reponsee.setNblike(resultSet.getInt("nblike"));
                reponsee.setNbdislike(resultSet.getInt("nbdislike"));

                reponseeList.add(reponsee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReponseeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reponseeList;
    }

    public void insertReponsee(Reponsee reponsee) {
        String requete = "insert into reponsee(sujet_id, contenu, date) values (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, reponsee.getSujet_id());
            preparedStatement.setString(2, reponsee.getContenu());
            preparedStatement.setDate(3, reponsee.getDate());
            preparedStatement.executeUpdate();
            SujetService sujetService = new SujetService();
            Sujet sujet = sujetService.retournerSujet(reponsee.getSujet_id());
            sujet.setId(reponsee.getSujet_id());
            sujet.setNbreponse(sujet.getNbreponse() + 1);
            sujetService.updateNbreReponseOnSujet(sujet);
        } catch (SQLException ex) {
            Logger.getLogger(ReponseeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteReponse(int reponseId) {
        String requete = "delete from reponsee where id = " + reponseId;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReponseeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Reponsee returnReponsee(int reponseeId) throws SQLException {
        Reponsee reponsee = new Reponsee();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM reponsee where `id` = " + reponseeId);
        while (res.next()) {
            int id = res.getInt("id");
            int sujetId = res.getInt("sujet_id");
            reponsee.setId(id);
            reponsee.setSujet_id(sujetId);
        }
        return reponsee;
    }
    public void setlike(int idreponse,int nb) {
        try {
            String req= "update reponsee set nblike=?  where id= ?";
            PreparedStatement ps=connection.prepareStatement(req);
            ps.setInt(1,nb);
            ps.setInt(2,idreponse);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReponseeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getnblike(int id) {
        int s=0;
        try {

            String req="select * from reponsee where id="+id;
            Statement st=connection.createStatement();
            ResultSet rs= st.executeQuery(req);
            while(rs.next())
            {
                s = rs.getInt(6);
            }


        } catch (SQLException ex) {
            Logger.getLogger(ReponseeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    public void setdislike(int idreponse,int nb) {
        try {
            String req= "update reponsee set nbdislike=?  where id= ?";
            PreparedStatement ps=connection.prepareStatement(req);
            ps.setInt(1,nb);
            ps.setInt(2,idreponse);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReponseeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getnbdislike(int id) {
        int s=0;
        try {

            String req="select * from reponsee where id="+id;
            Statement st=connection.createStatement();
            ResultSet rs= st.executeQuery(req);
            while(rs.next())
            {
                s = rs.getInt(7);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReponseeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}
