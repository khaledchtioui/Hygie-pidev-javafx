package seance_re.gui;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import seance_re.entities.Seance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.io.File;
import java.util.function.UnaryOperator;


import java.sql.Date;
import java.util.*;
import java.time.LocalDate;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.fxml.Initializable;
import seance_re.services.ServiceSeance;

import javafx.scene.image.ImageView;

import java.sql.*;
import javafx.stage.FileChooser.ExtensionFilter;


import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    @FXML
    private TableColumn<Seance, String>  idCol;
    private ObservableList<Seance> observableList;
    private ServiceSeance ServiceSeance;
    private Seance selectedSeance;
    @FXML
    Button supprimer;
    @FXML
    Button modifier;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date_seance.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        titre_seance.textProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si la nouvelle valeur ne contient que des lettres alphabétiques
            if (!newValue.matches("^[a-zA-Z ]*$")) {
                // Si la nouvelle valeur contient autre chose que des lettres, la remplacer par l'ancienne valeur
                titre_seance.setText(oldValue);
            }
        });
        Pattern pattern = Pattern.compile("\\d*\\.?\\d*");

        // Create a filter that only allows float values
        UnaryOperator<Change> filter = change -> {
            String newText = change.getControlNewText();
            if (pattern.matcher(newText).matches()) {
                try {
                    float value = Float.parseFloat(newText);
                    if (value >= 0) {
                        return change;
                    }
                } catch (NumberFormatException e) {
                    // Value is not a valid float, do nothing
                }
            }
            return null;
        };

        // Create a text formatter with the filter
        TextFormatter<String> formatter = new TextFormatter<>(filter);

        // Set the text formatter to the text field
        prix_seance.setTextFormatter(formatter);
        tableView.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        observableList = FXCollections.observableArrayList();
        ServiceSeance = new ServiceSeance();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));


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
        TableColumn<Seance, Void> reservationColumn = new TableColumn<>("Reservation");


// Add the reservation column to the table view
// Create a cell factory for the reservation column
        reservationColumn.setCellFactory(column -> new TableCell<Seance, Void>() {
            private final Button reservationButton = new Button("Reserve");

            // Set the button action when it is clicked
            {
                reservationButton.setOnAction(e -> {
                    Seance seanceSelectionnee = getTableView().getSelectionModel().getSelectedItem();
                    if(seanceSelectionnee != null) {
                        try {
                            ServiceSeance.reserveSeance(seanceSelectionnee);
                            // Actualiser la table des réservations ici
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            }

            // Render the cell with the reservation button
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(reservationButton);
                }
            }
        });
        tableView.getColumns().add(reservationColumn);

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
    private void ajouterAction() {
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
            try {
    Seance p = new Seance(titre_seance.getText(), imageData, description_seance.getText(), Float.parseFloat(prix_seance.getText()), Date.valueOf(dateString));
                sp.Ajouter(p);
                loadData();
            } catch (NumberFormatException e) {
                // Show an error message if the price value is not a valid float
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price value!");
                alert.showAndWait();
            }


        }

    private void loadData() {
        observableList.clear();
        ObservableList<Seance> seances = FXCollections.observableArrayList(ServiceSeance.Afficher());
        observableList.addAll(seances);
        tableView.refresh();
    }





    @FXML
    private void supprimer() {
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
                // Check if the Seance object has a valid id
                if (seanceASupprimer.getId() == 0) {
                    System.out.println("Error deleting seance: invalid id");
                    return;
                }
                // Remove seance from the TableView and the database
                ServiceSeance.supprimerSeance(seanceASupprimer);
                observableList.remove(seanceASupprimer);
                tableView.refresh();
                System.out.println("Seance deleted successfully.");
            } catch (SQLException e) {
                System.out.println("Error deleting seance: " + e.getMessage());
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

    private void handleBtnModifier() {
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
