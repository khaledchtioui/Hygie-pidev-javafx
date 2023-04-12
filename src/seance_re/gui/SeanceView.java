package seance_re.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SeanceView implements Initializable {

    @FXML
    private StackPane contentareaS;

    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    public void Seance(ActionEvent event) {
        try {
            Parent fxmlS = javafx.fxml.FXMLLoader.load(getClass().getResource("SeanceView.fxml"));
            contentareaS.getChildren().removeAll();
            contentareaS.getChildren().setAll(fxmlS);
        } catch (Exception e) {
            System.out.println("can't load the page");
        }
    }

    public void Reservation(ActionEvent event) {
        try {
            Parent fxmlR = javafx.fxml.FXMLLoader.load(getClass().getResource("Reservation.fxml"));
            contentareaS.getChildren().removeAll();
            contentareaS.getChildren().setAll(fxmlR);
        } catch (Exception e) {
            System.out.println("can't load the page");
        }
    }

}
