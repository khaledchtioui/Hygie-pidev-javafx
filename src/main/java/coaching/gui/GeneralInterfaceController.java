package coaching.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class GeneralInterfaceController {
    @FXML
    private Button ouvrirEspaceAdminButton;
    @FXML
    private Button ouvrirEspaceUserButton;

    @FXML
    public void espaceAdmin(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("EspaceCoach.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void espaceUser(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("EspaceUser.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
