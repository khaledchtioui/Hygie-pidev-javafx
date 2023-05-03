package coaching.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXCoaching extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(JavaFXCoaching.class.getResource("GeneralInterface.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setTitle("Coaching");
        stage.setWidth(650);
        stage.setHeight(580);
    }

    

}



