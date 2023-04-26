package seance_re.services;

import javafx.scene.image.Image;
import seance_re.Seance_re;
import seance_re.entities.Seance;
import seance_re.utilities.MyDB;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

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
        if (file != null) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                imageData = Base64.getEncoder().encodeToString(bytes);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return imageData;
    }

    public void setSelectedFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.err.println("Invalid file path: " + filePath);
            return;
        }
        this.selectedFile = file;
    }

    private String encodeImage(String imageData) {
        return imageData;
    }


    public ArrayList<Seance> Afficher(){
        ArrayList<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance`";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Seance s = new Seance();
                s.setId(rs.getInt("id"));
                s.setTitre(rs.getString("titre"));
                String imageData = rs.getString("image");
                s.setImage(encodeImage(imageData));
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
    public void supprimerSeance(Seance s) throws SQLException {
        try {
            String query = "DELETE FROM `hygie_app`.`seance` WHERE id=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, s.getId());
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

            // check if imageData is null or empty
            String imageData = seance.getImage();
            if (imageData == null || imageData.isEmpty()) {
                // retrieve existing image data from database
                String query2 = "SELECT image FROM `hygie_app`.`seance` WHERE id=?";
                PreparedStatement statement2 = con.prepareStatement(query2);
                statement2.setInt(1, seance.getId());
                ResultSet rs = statement2.executeQuery();
                if (rs.next()) {
                    imageData = rs.getString("image");
                }
            } else {
                // update image column with new image data
                imageData = getImageData(selectedFile);
            }

            statement.setString(2, imageData);
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
    }

    public boolean seanceDejaReservee(Seance seance) throws SQLException {
        String query = "SELECT COUNT(*) FROM `hygie_app`.`reservation` WHERE `seance_id` = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, seance.getId());
        ResultSet rs = statement.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
    }


    public void reserveSeance(Seance seance) throws SQLException {
        if (seanceDejaReservee(seance)) {
            System.out.println("La séance a déjà été réservée !");
            return;
        }

        try {
            String query = "INSERT INTO `hygie_app`.`reservation` (seance_id) VALUES (?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, seance.getId());
            statement.executeUpdate();
            System.out.println("Séance réservée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la réservation de la séance : " + e.getMessage());
        }
    }


    public List<Seance> afficherSeancesReservees() throws SQLException {
        List<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT s.* FROM `hygie_app`.`seance` s INNER JOIN `hygie_app`.`reservation` r ON r.seance_id = s.id";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setId(rs.getInt("id"));
                seance.setTitre(rs.getString("titre"));
                seance.setImage(rs.getString("image"));
                seance.setDescription(rs.getString("description"));
                seance.setPrix(rs.getFloat("prix"));
                seance.setDate(rs.getDate("date"));
                seances.add(seance); // Add the seance object to the seances list
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return seances;
    }
    public void supprimerReservation(Seance seance) throws SQLException {
        try {
            String query = "DELETE FROM `hygie_app`.`reservation` WHERE seance_id=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, seance.getId());
            statement.executeUpdate();
            System.out.println("Reservation deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
        }

    }

    public List<Seance> getAllSeances() throws SQLException {
        List<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance`";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setId(rs.getInt("id"));
                seance.setTitre(rs.getString("titre"));
                seance.setImage(rs.getString("image"));
                seance.setDescription(rs.getString("description"));
                seance.setPrix(rs.getFloat("prix"));
                seance.setDate(rs.getDate("date"));
                seances.add(seance);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return seances;
    }

    public List<Seance> rechercherSeance(String recherche) {
        List<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance` WHERE titre LIKE ? OR description LIKE ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, "%" + recherche.trim() + "%");
            pst.setString(2, "%" + recherche.trim() + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setTitre(rs.getString("titre"));
                seance.setDescription(rs.getString("description"));
                seance.setPrix(rs.getFloat("prix"));
                seance.setDate(rs.getDate("date"));
                seances.add(seance);
            }
        } catch (SQLException ex) {
            System.err.println("Error executing query: " + ex.getMessage());
        }
        return seances;
    }

    public List<Seance> trierSeancesParTitre() {
        List<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance` ORDER BY titre";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setTitre(rs.getString("titre"));
                seance.setDescription(rs.getString("description"));
                seance.setPrix(rs.getFloat("prix"));
                seance.setDate(rs.getDate("date"));
                seances.add(seance);
            }
        } catch (SQLException ex) {
            System.err.println("Error executing query: " + ex.getMessage());
        }
        return seances;
    }

    public List<Seance> trierSeancesParDescription() {
        List<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance` ORDER BY description";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setTitre(rs.getString("titre"));
                seance.setDescription(rs.getString("description"));
                seance.setPrix(rs.getFloat("prix"));
                seance.setDate(rs.getDate("date"));
                seances.add(seance);
            }
        } catch (SQLException ex) {
            System.err.println("Error executing query: " + ex.getMessage());
        }
        return seances;
    }

    public List<Seance> trierSeancesParPrix() {
        List<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance` ORDER BY prix";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setTitre(rs.getString("titre"));
                seance.setDescription(rs.getString("description"));
                seance.setPrix(rs.getFloat("prix"));
                seance.setDate(rs.getDate("date"));
                seances.add(seance);
            }
        } catch (SQLException ex) {
            System.err.println("Error executing query: " + ex.getMessage());
        }
        return seances;
    }

    public List<Seance> trierSeancesParDate() {
        List<Seance> seances = new ArrayList<>();
        try {
            String query = "SELECT * FROM `hygie_app`.`seance` ORDER BY date";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setTitre(rs.getString("titre"));
                seance.setDescription(rs.getString("description"));
                seance.setPrix(rs.getFloat("prix"));
                seance.setDate(rs.getDate("date"));
                seances.add(seance);
            }
        } catch (SQLException ex) {
            System.err.println("Error executing query: " + ex.getMessage());
        }
        return seances;
    }







}