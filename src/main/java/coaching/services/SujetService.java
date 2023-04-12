package coaching.services;

import coaching.entity.Sujet;
import coaching.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SujetService {

    private Connection connection;

    public SujetService() {
        connection = DataSource.getInstance().getCnx();
    }

    //Afficher les sujets
    public List<Sujet> readAllSujets() {
        String requete = "select * from sujet";
        List<Sujet> sujetList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                Sujet sujet = new Sujet();
                sujet.setId(resultSet.getInt("id"));
                sujet.setTitre(resultSet.getString("titre"));
                sujet.setDescription(resultSet.getString("description"));
                sujet.setDate(resultSet.getDate("date"));
                sujet.setNbreponse(resultSet.getInt("nbreponse"));
                sujet.setUser_id(resultSet.getInt("user_id"));
                sujetList.add(sujet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sujetList;
    }

    public void insertSujet(Sujet sujet) {
        String requete = "insert into sujet(titre, description, date, nbreponse) values (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, sujet.getTitre());
            preparedStatement.setString(2, sujet.getDescription());
            preparedStatement.setDate(3, sujet.getDate());
            preparedStatement.setInt(4, sujet.getNbreponse());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSujet(Sujet sujet) {
        try {
            String requete = "update sujet set titre=?, description=? , nbreponse=? where id='" + sujet.getId() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, sujet.getTitre());
            preparedStatement.setString(2, sujet.getDescription());
            preparedStatement.setInt(3, sujet.getNbreponse());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSujet(int sujetId) {
        String requete = "delete from sujet where id = " + sujetId;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateNbreReponseOnSujet(Sujet sujet) {
        try {
            String requete = "update sujet set nbreponse=? where id='" + sujet.getId() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, sujet.getNbreponse());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Sujet retournerSujet(int idSujet) throws SQLException {
        Sujet sujet = new Sujet();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM Sujet where `id` = " + idSujet);
        while (res.next()) {
            int id = res.getInt("id");
            int nbreponse = res.getInt("nbreponse");
            sujet.setNbreponse(nbreponse);
            sujet.setId(id);
        }
        return sujet;
    }

}
