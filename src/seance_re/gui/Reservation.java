package seance_re.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import seance_re.entities.Seance;
import seance_re.services.ServiceSeance;
import seance_re.entities.Seance;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Reservation implements Initializable {

    // These fields correspond to the TableView and its columns in the FXML file
    @FXML
    private TableView<Seance> tableR;

    @FXML
    private TableColumn<Seance, Integer> id;

    @FXML
    private TableColumn<Seance, String> titreR;

    @FXML
    private TableColumn<Seance, String> imageR;

    @FXML
    private TableColumn<Seance, String> descriptionR;

    @FXML
    private TableColumn<Seance, Double> prixR;

    @FXML
    private TableColumn<Seance, Date> dateR;

    private ObservableList<Seance> observableList;
    private ServiceSeance serviceSeance;
    @FXML
    private Button supprimer;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableR.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        observableList = FXCollections.observableArrayList();
        serviceSeance = new ServiceSeance();

        // Associate each column to the corresponding property of the Seance object
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreR.setCellValueFactory(new PropertyValueFactory<>("titre"));
        imageR.setCellValueFactory(new PropertyValueFactory<>("image"));
        descriptionR.setCellValueFactory(new PropertyValueFactory<>("description"));
        prixR.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateR.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Link the observable list to the table
        tableR.setItems(observableList);

        // Load the data into the observable list
        loadData();
    }

    private void loadData() {
        observableList.clear();
        try {
            // Retrieve the reserved sessions from the database and convert them to an observable list
            ObservableList<Seance> seances = FXCollections.observableArrayList(serviceSeance.afficherSeancesReservees());
            System.out.println("Nombre de seances récupérées : " + seances.size()); // Debugging statement
            observableList.addAll(seances);
            tableR.refresh();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Seance newSeance = new Seance();
    }
    @FXML
    private void supprimer() {
        // Get the selected reservation from the table view
        Seance selectedReservation = tableR.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            try {
                // Delete the reservation from the database
                serviceSeance.supprimerReservation(selectedReservation);

                // Remove the reservation from the table view
                observableList.remove(selectedReservation);
            } catch (SQLException e) {
                // Handle the exception
                e.printStackTrace();
            }
        }
    }
}
