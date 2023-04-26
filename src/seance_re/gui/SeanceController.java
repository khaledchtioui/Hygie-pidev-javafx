package seance_re.gui;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SeanceController implements Initializable {
    @FXML
    private Label Menu;

    @FXML
    private Label Menuback;
    @FXML
    private StackPane contentArea;

    @FXML
    private AnchorPane slider;
    @FXML
    private Button Forum;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((javafx.event.ActionEvent e)-> {
                Menu.setVisible(false);
                Menuback.setVisible(true);
            });
        });

        Menuback.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                Menuback.setVisible(false);
            });
        });

        try {
            Parent fxml = javafx.fxml.FXMLLoader.load(getClass().getResource("Seance1.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (Exception e) {
            System.out.println("can't load the page");

        }


    }
    public void Seance1(ActionEvent event) {
        try {
            Parent fxml = javafx.fxml.FXMLLoader.load(getClass().getResource("Seance1.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (Exception e) {
            System.out.println("can't load the page");

        }

    }
    @FXML
    private void openforum(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Home_front.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hygie");
        stage.show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }


    }



