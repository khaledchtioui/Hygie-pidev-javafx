/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package seance_re;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seance_re.utilities.MyDB;
import seance_re.entities.Seance;
import seance_re.services.ServiceSeance;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
/**
 *
 * @author idrissbenyounes
 */
public class Seance_re  extends Application {
    @Override
public void start(Stage primaryStage) {
try {
    Parent root = FXMLLoader.load(getClass().getResource("gui/Seance.fxml"));
    Parent parent1 = FXMLLoader.load(getClass().getResource("gui/Seance1.fxml"));
    Scene scene1 = new Scene(parent1);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene1);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Seance");
    primaryStage.initStyle(StageStyle.DECORATED);
    primaryStage.show();

}catch (Exception ex) {
Logger.getLogger(Seance_re.class.getName()).log(Level.SEVERE, null, ex);

}
}


    public static void main(String[] args) {
launch(args);
}


    }



