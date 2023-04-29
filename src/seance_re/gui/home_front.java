package seance_re.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javax.swing.text.html.ImageView;

public class home_front implements Initializable {
    @FXML
    private StackPane seanceRes;
   @FXML
   private Button Reservation;
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
        try {
            Parent fxml = javafx.fxml.FXMLLoader.load(getClass().getResource("SeanceFront.fxml"));
            seanceRes.getChildren().removeAll();
            seanceRes.getChildren().setAll(fxml);

        } catch (Exception e) {
            System.out.println("can't load the page");
        }

    }
    public void  Reservation(ActionEvent event) {
        try {
            Parent fxmlR = javafx.fxml.FXMLLoader.load(getClass().getResource("ReservationFront.fxml"));
            seanceRes.getChildren().removeAll();
            seanceRes.getChildren().setAll(fxmlR);
        } catch (Exception e) {
            System.out.println("can't load the page");
        }
    }
}
