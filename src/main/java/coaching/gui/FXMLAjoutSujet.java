package coaching.gui;

import coaching.entity.Sujet;
import coaching.services.SujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class FXMLAjoutSujet implements Initializable {

    @FXML
    public TextField titreSujetInput;

    @FXML
    public TextField descriptionSujetInput;

    @FXML
    public TextField dateSujetInput;
    @FXML
    public Button ajoutSujetButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateSujetInput.setText(String.valueOf(new Date(System.currentTimeMillis())));
    }

    @FXML
    public void ajoutSujet(ActionEvent event) {
        if (Objects.equals(titreSujetInput.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Titre Sujet est Obligatoire");
            alert.showAndWait();
        } else if (titreSujetInput.getText().length() < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Titre Sujet doit avoir 10 Caractéres minimum");
            alert.showAndWait();
        } else if (descriptionSujetInput.getText().length() < 20) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Description Sujet doit avoir 20 Caractéres minimum");
            alert.showAndWait();
        } else if (Objects.equals(descriptionSujetInput.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Description Sujet est Obligatoire");
            alert.showAndWait();
        } else {
            SujetService sujetService = new SujetService();
            String titreSujet = titreSujetInput.getText();
            String descriptionSujet = descriptionSujetInput.getText();
            Sujet sujet = new Sujet();
            sujet.setTitre(titreSujet);
            sujet.setDescription(descriptionSujet);
            sujet.setDate(new Date(System.currentTimeMillis()));
            sujetService.insertSujet(sujet);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout de sujet ");
            alert.setHeaderText("Le sujet est bien ajoutée ");
            alert.setContentText("OK!");
            alert.showAndWait();
            Stage stage = (Stage) ajoutSujetButton.getScene().getWindow();
            stage.close();
        }
    }
}


