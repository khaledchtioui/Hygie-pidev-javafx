package seance_re.gui;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.Comparator;
import javafx.collections.FXCollections;

import java.io.ByteArrayInputStream;
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
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> sortComboBox;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date_seance.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
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
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        // Créer une nouvelle colonne pour l'affichage de l'image décodée
        TableColumn<Seance, Image> decodedImageCol = new TableColumn<>("Image");

// Créer la cell factory personnalisée pour l'affichage de l'image décodée
        decodedImageCol.setCellFactory(col -> new TableCell<Seance, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Définir l'image dans l'ImageView
                    imageView.setImage(item);
                    // Redimensionner l'image pour qu'elle rentre dans la cellule
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    // Afficher l'ImageView dans la cellule
                    setGraphic(imageView);
                }
            }
        });

// Ajouter la nouvelle colonne à la TableView
        tableView.getColumns().add(decodedImageCol);

// Définir la fonction de décodage pour la colonne d'affichage de l'image décodée
        decodedImageCol.setCellValueFactory(cellData -> {
            // Récupérer l'image encodée à partir de la colonne image existante
            String encodedImage = cellData.getValue().getImage();
            if (encodedImage == null) {
                return new SimpleObjectProperty<>(null);
            }
            // Décompresser l'image encodée
            byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
            // Créer une nouvelle instance de Image à partir des données décompressées
            Image decodedImage = new Image(new ByteArrayInputStream(decodedBytes));
            // Retourner l'image décodée
            return new SimpleObjectProperty<>(decodedImage);
        });


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
                    if (seanceSelectionnee != null) {
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

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            observableList.clear();
            List<Seance> seances = ServiceSeance.rechercherSeance(newValue);
            observableList.addAll(seances);
            tableView.setItems(observableList);
        });

        ObservableList<String> sortOptions = FXCollections.observableArrayList("Titre (asc)", "Titre (des)", "Prix (asc)", "Prix (des)", "Date (asc)", "Date (des)");
        sortComboBox.setItems(sortOptions);
        sortComboBox.setValue("Titre (asc)"); // set default sort option
        sortComboBox.setOnAction(event -> {
            String selectedSortOption = sortComboBox.getValue();
            boolean isAscending = selectedSortOption.endsWith("(asc)"); // check if selected option is ascending or descending
            switch (selectedSortOption) {
                case "Titre (asc)":
                case "Titre (des)":
                    // sort by Titre column
                    if (isAscending) {
                        tableView.getItems().sort(Comparator.comparing(Seance::getTitre));
                    } else {
                        tableView.getItems().sort(Comparator.comparing(Seance::getTitre).reversed());
                    }
                    break;
                case "Prix (asc)":
                case "Prix (des)":
                    // sort by Prix column
                    if (isAscending) {
                        tableView.getItems().sort(Comparator.comparing(Seance::getPrix));
                    } else {
                        tableView.getItems().sort(Comparator.comparing(Seance::getPrix).reversed());
                    }
                    break;
                case "Date (asc)":
                case "Date (des)":
                    // sort by Date column
                    if (isAscending) {
                        tableView.getItems().sort(Comparator.comparing(Seance::getDate));
                    } else {
                        tableView.getItems().sort(Comparator.comparing(Seance::getDate).reversed());
                    }
                    break;
            }
        });

// ...



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
        } else {
            // If no file has been selected, retrieve the existing image of the seance
            imageData = selectedSeance.getImage();
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

    public class ImageTableCell<S> extends TableCell<S, String> {

        private final ImageView imageView;

        public ImageTableCell() {
            imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            setGraphic(imageView);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                imageView.setImage(null);
            } else {
                byte[] imageData = Base64.getDecoder().decode(item);
                Image image = new Image(new ByteArrayInputStream(imageData));
                imageView.setImage(image);
            }
        }
    }
    public void handleSearch() {
        String searchText = searchBar.getText().toLowerCase();
        ObservableList<Seance> filteredData = tableView.getItems().filtered(
                s -> s.getTitre().toLowerCase().contains(searchText)
        );
        tableView.setItems(filteredData);
    }
    @FXML
    public void searchBar() {
        // Get the search string entered by the user
        String rechercheAvancee = searchBar.getText().trim();

        // Clear the previous list of seances
        observableList.clear();

        // Fetch the list of seances matching the search criteria
        List<Seance> seances = ServiceSeance.rechercherSeance(rechercheAvancee);

        // Add the seances to the observable list
        observableList.addAll(seances);

        // Update the table view with the new list of seances
        tableView.setItems(observableList);
    }



}