package seance_re.gui;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import seance_re.entities.Seance;
import seance_re.services.ServiceSeance;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class SeanceController implements Initializable {
    @FXML
    private Label Menu;

    @FXML
    private Label Menuback;
    @FXML
    private StackPane contentArea;

    @FXML
    private AnchorPane slider;


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
            Parent fxml1 = javafx.fxml.FXMLLoader.load(getClass().getResource("Forum.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
            contentArea.getChildren().setAll(fxml1);

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
    public void Forum(ActionEvent event) {
        try {
            Parent fxml1 = javafx.fxml.FXMLLoader.load(getClass().getResource("Forum.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml1);
        } catch (Exception e) {
            System.out.println("can't load the page");

        }

        }



    }



