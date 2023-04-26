package seance_re.gui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
public class home_front implements Initializable {
    @FXML
    private StackPane seanceRes;

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
}
