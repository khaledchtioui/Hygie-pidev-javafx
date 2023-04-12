package coaching.gui;

import coaching.entity.Reponsee;
import coaching.entity.Sujet;
import coaching.services.ReponseeService;
import coaching.services.SujetService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class UserInterfaceController implements Initializable {
    @FXML
    public TableColumn<Sujet, String> columnTitre;
    @FXML
    public TableColumn<Sujet, String> columnContenu;
    @FXML
    public TableColumn<Sujet, String> columnDescription;
    @FXML
    public TableColumn<Sujet, DateCell> columnDate;

    @FXML
    public TableView<Sujet> sujetTableView;

    @FXML
    public TableView<Reponsee> reponseTableView;

    @FXML
    public TextArea reponseInput;
    @FXML
    public Button ajouterReponse;
    @FXML
    public Button deleteReponsee;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        columnTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        List<Sujet> list;
        SujetService sujetService = new SujetService();
        list = sujetService.readAllSujets();
        sujetTableView.setItems((FXCollections.observableArrayList(list)));
    }

    @FXML
    public void getselectedSujet() {
        Sujet selectedSujet = sujetTableView.getSelectionModel().getSelectedItem();
        if (selectedSujet == null) {
            return;
        }
        int idSujet = selectedSujet.getId();
        ReponseeService reponseeService = new ReponseeService();
        List<Reponsee> reponseeList = reponseeService.readAssociatedReponses(idSujet);
        columnContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        reponseTableView.setItems((FXCollections.observableArrayList(reponseeList)));
    }

    @FXML

    public void ajouterReponse(ActionEvent event) {
        if (sujetTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Choix de Sujet est Obligatoire");
            alert.showAndWait();
        } else if (Objects.equals(reponseInput.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La Réponse sur le sujet est Obligatoire");
            alert.showAndWait();
        } else {
            int idSujet = sujetTableView.getSelectionModel().getSelectedItem().getId();
            ReponseeService reponseeService = new ReponseeService();
            Reponsee reponsee = new Reponsee();
            reponsee.setSujet_id(idSujet);
            reponsee.setContenu(reponseInput.getText());
            reponsee.setDate(new Date(System.currentTimeMillis()));
            reponseeService.insertReponsee(reponsee);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout de Réponse ");
            alert.setHeaderText("La réponse est bien ajoutée ");
            alert.setContentText("OK!");
            alert.showAndWait();
            reponseInput.clear();
        }
    }

    @FXML
    public void getselectedReponse() {

    }

    @FXML
    public void deleteReponsee(ActionEvent event) throws SQLException {
        int reponseeId = reponseTableView.getSelectionModel().getSelectedItem().getId();
        ReponseeService reponseeService = new ReponseeService();
        Reponsee rep = reponseeService.returnReponsee(reponseeId);
        int sujetId = rep.getSujet_id();
        SujetService sujetService = new SujetService();
        Sujet sujet = sujetService.retournerSujet(sujetId);
        sujet.setNbreponse(sujet.getNbreponse() - 1);
        sujetService.updateNbreReponseOnSujet(sujet);
        reponseeService.deleteReponse(reponseeId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression de Réponse ");
        alert.setHeaderText("La réponse est bien supprimée ");
        alert.setContentText("OK!");
        alert.showAndWait();
    }

}
