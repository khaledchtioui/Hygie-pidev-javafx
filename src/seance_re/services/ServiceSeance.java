package seance_re.services;

import seance_re.Seance_re;
import seance_re.entities.Seance;
import seance_re.utilities.MyDB;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;

import java.util.Base64;

public class ServiceSeance implements IService<Seance>{
   Connection con;
    Statement ste;
    public File selectedFile;

    public ServiceSeance() {
        con = MyDB.createorgetInstance().getCon();
    }

    @Override
    public void Ajouter(Seance t) {
        try {
            if (selectedFile == null) {
                System.err.println("No file selected!");
                return;
            }
            ste = con.createStatement();
            String imageData = getImageData(selectedFile);
            String query = "INSERT INTO `hygie_app`.`seance`(titre, image, description, prix, date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            // assignation des valeurs aux paramètres de la requête
            pst.setString(1, t.getTitre());
            pst.setString(2, imageData);
            pst.setString(3, t.getDescription());
            pst.setFloat(4, t.getPrix());
            pst.setDate(5, new java.sql.Date(t.getDate().getTime()));

            // exécution de la requête
            pst.executeUpdate();
            System.out.println("Seance ajoutée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public String getImageData(File file) {
        String imageData = null;
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            imageData = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return imageData;
    }
    public void setSelectedFile(String filePath) {
        this.selectedFile = new File(filePath);
    }
    public ArrayList<Seance> Afficher(){
        ArrayList<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance`";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Seance s = new Seance();
                s.setTitre(rs.getString("titre"));
                s.setImage(rs.getString("image"));
                s.setDescription(rs.getString("description"));
                s.setPrix(rs.getFloat("prix"));
                s.setDate(rs.getDate("date"));
                seances.add(s);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return seances;
    }
    public void supprimerSeance(Seance seance) throws SQLException {
        try {
            String query = "DELETE FROM `hygie_app`.`seance` WHERE id=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, seance.getId());
            statement.executeUpdate();
            System.out.println("Seance deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting seance: " + e.getMessage());
        }
    }
    public void modifierSeance(Seance seance) throws SQLException {
        try {
            String query = "UPDATE `hygie_app`.`seance` SET titre=?, image=?, description=?, prix=?, date=? WHERE id=?";
            PreparedStatement statement = con.prepareStatement(query);

            // assignation des valeurs aux paramètres de la requête
            statement.setString(1, seance.getTitre());
            if (selectedFile != null) {
                statement.setString(2, getImageData(selectedFile));
            } else {
                statement.setNull(2, Types.VARCHAR);
            }
            statement.setString(3, seance.getDescription());
            statement.setFloat(4, seance.getPrix());
            statement.setDate(5, new java.sql.Date(seance.getDate().getTime()));
            statement.setInt(6, seance.getId());

            // exécution de la requête
            statement.executeUpdate();
            System.out.println("Seance updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating seance: " + e.getMessage());
        }
    }    }