package seance_re.gui;
import com.sun.glass.ui.CommonDialogs;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import seance_re.entities.Seance;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import seance_re.services.ServiceSeance;
import sun.util.resources.LocaleData;

import javax.swing.*;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import javafx.stage.FileChooser.ExtensionFilter;

import java.net.URL;
import java.util.ResourceBundle;

public class Seance1 implements Initializable{
    @FXML
    private DatePicker date_seance;

    @FXML
    private TextField description_seance;

    @FXML
    private TextField prix_seance;

    @FXML
    private TextField titre_seance;
    @FXML
    private Button upload;
    private File selectedFile;
    @FXML
    private TableView<Seance> tableView;
    @FXML
    private TableColumn<Seance, String> titreCol;
    @FXML
    private TableColumn<Seance, ImageView> imageCol;
    @FXML
    private TableColumn<Seance, String> descriptionCol;
    @FXML
    private TableColumn<Seance, Double> prixCol;
    @FXML
    private TableColumn<Seance, Date> dateCol;
    private ObservableList<Seance> observableList;
    private ServiceSeance ServiceSeance;
    private Seance selectedSeance;
    @FXML
    Button supprimer;
    @FXML
    Button modifier;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableList = FXCollections.observableArrayList();
        ServiceSeance = new ServiceSeance();

        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("imageData"));

        tableView.setItems(observableList);
        loadData();

       date_seance.setValue(LocalDate.now());
        supprimer.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedSeance = newSelection;
                titre_seance.setText(selectedSeance.getTitre());
                description_seance.setText(selectedSeance.getDescription());
                prix_seance.setText(Float.toString(selectedSeance.getPrix()));
            }
        });


        // TODO
    }
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("All Files", "*.*"));
         selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            upload.setText(selectedFile.getName());
        } else {
            System.out.println("file is not valid!");
        }
        ServiceSeance sp = new ServiceSeance();
        String filePath = selectedFile.toString();

        sp.setSelectedFile(filePath);
    }
        @FXML
    private void ajouterAction(ActionEvent event) {
            if (selectedFile == null) {
                System.out.println("Please select a file!");
                return;
            }
        LocalDate  dateString = date_seance.getValue();
        System.out.println("Selected date: " + dateString);

            ServiceSeance sp = new ServiceSeance();
            String filePath;
            filePath = selectedFile.toString();
            sp.setSelectedFile(filePath);
            String imageData = sp.getImageData(selectedFile);

            Seance p = new Seance(titre_seance.getText(), imageData , description_seance.getText(), Float.parseFloat(prix_seance.getText()), Date.valueOf(dateString));

        sp.Ajouter(p);


        }

    private void loadData() {
        observableList.clear();
        ObservableList<Seance> seances = FXCollections.observableArrayList(ServiceSeance.Afficher());
        observableList.addAll(seances);
        tableView.refresh();
    }
    @FXML
    private void supprimer(ActionEvent event) {
        Seance seanceASupprimer = tableView.getSelectionModel().getSelectedItem();
        if (seanceASupprimer == null) {
            return;
        }
        // Show a confirmation dialog before deleting the seance
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer la séance?");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer la séance sélectionnée?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

try {


    // Remove seance from the TableView and the database
    ServiceSeance.supprimerSeance(seanceASupprimer);
    observableList.remove(seanceASupprimer);
    tableView.refresh();
} catch (SQLException e) {
    e.printStackTrace();
}

        }
    }
    private void clearInputs() {
        titre_seance.clear();
        description_seance.clear();
        prix_seance.clear();
        date_seance.setValue(LocalDate.now());

    }
    @FXML

    private void handleBtnModifier(ActionEvent event) {
        if (selectedSeance == null) {
            return;
        }

        LocalDate dateString = date_seance.getValue();
        ServiceSeance sp = new ServiceSeance();
        String filePath;
        String imageData = "";
        if (selectedFile != null) {
            filePath = selectedFile.toString();
            sp.setSelectedFile(filePath);
            imageData = sp.getImageData(selectedFile);
        }

        Seance seanceModifiee = new Seance(titre_seance.getText(), imageData, description_seance.getText(), Float.parseFloat(prix_seance.getText()), Date.valueOf(dateString));
        seanceModifiee.setId(selectedSeance.getId());

        try {
            ServiceSeance.modifierSeance(seanceModifiee);
            observableList.remove(selectedSeance);
            observableList.add(seanceModifiee);
            clearInputs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
