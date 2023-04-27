package coaching.gui;

import coaching.entity.Reponsee;
import coaching.entity.Sujet;
import coaching.services.ReponseeService;
import coaching.services.SujetService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class AdminInterfaceController implements Initializable {


    @FXML
    public TableColumn<Sujet, String> columnTitre;
    @FXML
    public TableColumn<Sujet, String> columnDescription;
    @FXML
    public TableColumn<Sujet, DateCell> columnDate;
    @FXML
    public TableColumn<Sujet, String> columnReponse;

    @FXML
    public TableView<Sujet> sujetTableView;

    @FXML
    public TextField titreInput;
    @FXML
    public TextField descriptionInput;
    @FXML
    public Button showSujet;
    @FXML
    public Button deleteSujetButton;
    @FXML
    public Button updateSujetButton;


    @FXML
    public Button showAssociatedReponse;

    @FXML
    public Button ajouterSujet;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        columnTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnReponse.setCellValueFactory(new PropertyValueFactory<>("nbreponse"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        List<Sujet> list;
        SujetService sujetService = new SujetService();
        list = sujetService.readAllSujets();
        sujetTableView.setItems((FXCollections.observableArrayList(list)));
    }

    @FXML
    public void showSujet(ActionEvent event) {
        columnTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnReponse.setCellValueFactory(new PropertyValueFactory<>("nbreponse"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        List<Sujet> list;
        SujetService sujetService = new SujetService();
        list = sujetService.readAllSujets();
        sujetTableView.setItems((FXCollections.observableArrayList(list)));
    }

    @FXML
    public void ajouterSujet(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AjoutSujet.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void getSelectedSujet() {
        Sujet sujet = sujetTableView.getSelectionModel().getSelectedItem();
        titreInput.setText(sujet.getTitre());
        descriptionInput.setText(sujet.getDescription());
    }


    public void updateSujet(ActionEvent event) throws SQLException {
        if (Objects.equals(titreInput.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Titre Sujet est Obligatoire");
            alert.showAndWait();
        } else if (Objects.equals(descriptionInput.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Description Sujet est Obligatoire");
            alert.showAndWait();
        } else {

            String titreSujet = titreInput.getText();
            String descriptionSujet = descriptionInput.getText();
            int idSujet = sujetTableView.getSelectionModel().getSelectedItem().getId();
            SujetService sujetService = new SujetService();
            Sujet sujetTrouvé = sujetService.retournerSujet(idSujet);
            Sujet sujet = new Sujet();
            sujet.setId(idSujet);
            sujet.setTitre(titreSujet);
            sujet.setDescription(descriptionSujet);
            sujet.setNbreponse(sujetTrouvé.getNbreponse());
            sujetService.updateSujet(sujet);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification du Sujet ");
            alert.setHeaderText("Le Sujet est bien modifié ");
            alert.setContentText("OK!");
            alert.showAndWait();
            List<Sujet> list;
            list = sujetService.readAllSujets();
            sujetTableView.setItems((FXCollections.observableArrayList(list)));
            titreInput.clear();
            descriptionInput.clear();
        }
    }

    @FXML
    public void deleteSujet(ActionEvent event) {
        int idSujet = sujetTableView.getSelectionModel().getSelectedItem().getId();
        SujetService sujetService = new SujetService();
        sujetService.deleteSujet(idSujet);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppresssion du Sujet ");
        alert.setHeaderText("Le Sujet est bien supprimé");
        alert.setContentText("OK!");
        alert.showAndWait();
        List<Sujet> list;
        list = sujetService.readAllSujets();
        sujetTableView.setItems((FXCollections.observableArrayList(list)));
    }


    public void showAssociatedReponse(ActionEvent actionEvent) throws IOException {
        Sujet selectedSujet = sujetTableView.getSelectionModel().getSelectedItem();
        if (selectedSujet == null) {
            return;
        }
        int idSujet = selectedSujet.getId();
        ReponseeService reponseeService = new ReponseeService();
        List<Reponsee> reponseeList = reponseeService.readAssociatedReponses(idSujet);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AdminInterfaceController.class.getResource("AssociatedReponsee.fxml"));
        FXMLSujetAssociatedReponsee controller = new FXMLSujetAssociatedReponsee();
        controller.setAssociatedReponsee(reponseeList);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
        newStage.centerOnScreen();
        newStage.toFront();
    }


}
