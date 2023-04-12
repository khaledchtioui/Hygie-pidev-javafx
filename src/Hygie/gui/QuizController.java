/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hygie.gui;

import Hygie.entities.Questionnaire;
import Hygie.services.QuizService;
import Hygie.services.ServiceQuiz;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Khaled
 */
public class QuizController implements Initializable {

    
     @FXML
    private TextField quiztf;
     private int id ;

    private Label Listabel;
        
        
   @FXML
    private TableView<Questionnaire> questionnaireTableView;

    @FXML
    private TableColumn<Questionnaire, Integer> idColumn;

    @FXML
    private TableColumn<Questionnaire, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Questionnaire, String> nomColumn;
    @FXML
    private Button ajouter;

    private          ServiceQuiz questionnaireService =new ServiceQuiz() ;

  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ServiceQuiz rs =new ServiceQuiz() ;
      
            
        List<Questionnaire> questionnaires=rs.getAll() ;
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
      
        // Populate the TableView with data
        questionnaireTableView.getItems().addAll(questionnaires);
        // TODO
        
        
         questionnaireTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
               id=  Integer.valueOf(newValue.getId());
            //    System.out.println(id);

                quiztf.setText(newValue.getNom());
            }
        });
         
         
            questionnaireTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double-click events
                Questionnaire selectedQuestionnaire = questionnaireTableView.getSelectionModel().getSelectedItem();
                if (selectedQuestionnaire != null) {
                    // Open a new interface or window with the selected questionnaire data
                    openNewInterface(selectedQuestionnaire,id);
                }
            }
        });
         
         
    }    
    @FXML
    private void openNewInterface(Questionnaire questionnaire,int id) {
    // Create a new stage (window) for the new interface
    Stage stage = new Stage();
    
    // Load the FXML file for the new interface
            FXMLLoader loader = new FXMLLoader();
loader.setLocation(QuestionsController.class.getResource("Questions.fxml"));
    Parent root ;
    try {
     //   System.out.println("dd"+id);
       QuestionsController newQuestionsController  = new QuestionsController();
   //  newQuestionsController.setQ(questionnaire);
      //loader.setController(newQuestionsController);
    // Set the scene of the new stage with the loaded FXML file
            root = loader.load();

    Scene scene = new Scene(root);
    stage.setScene(scene);
    
    // Show the new stage
    stage.show();
        
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    // Get the controller of the new interface
  
}

    
    
     @FXML
    private void ajouterAction(ActionEvent event    ) {
       if(quiztf.getText().isEmpty())
       {
               Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Titre Questionnaire  est Obligatoire");
            alert.showAndWait();
       }
       else if(Character.isDigit(quiztf.getText().charAt(0)))
       {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Titre Questionnaire  ne doit pas commencer par un numéro");
            alert.showAndWait();
       }
       else if (quiztf.getText().trim().length()<3)
       {
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Titre Questionnaire  doit contenir au moins  3 caractere");
            alert.showAndWait();
       }
       else
       {
           
         Questionnaire q = new Questionnaire(quiztf.getText());
        ServiceQuiz sp =new ServiceQuiz();
        sp.Ajouter(q);
             quiztf.clear();
              
              Afficher();
        
       }
    }
     @FXML
    private void Afficher() {
                ServiceQuiz sp =new ServiceQuiz();

         quiztf.clear();
// Vider le contenu de la TableView
questionnaireTableView.getItems().clear();

                      List<Questionnaire> questionnaires=sp.getAll() ;

                      questionnaireTableView.getItems().addAll(questionnaires);

              
        questionnaireTableView.refresh();
        
     //   ServiceQuiz sp = new ServiceQuiz();
       // Listabel.setText(sp.getAll().toString());
       
    }
    
      @FXML
    private void Mofifier() {
         Questionnaire q = new Questionnaire(id,quiztf.getText());
        ServiceQuiz sp =new ServiceQuiz();
        sp.modifier(quiztf.getText(),q);
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification du Questionnaire ");
        alert.setHeaderText("Le Questionnaire est bien modifié ");
        alert.setContentText("OK!");
        alert.showAndWait();
           //  quiztf.clear();
        Afficher();

    }
       @FXML
       private void Delete() {
        Questionnaire q=new Questionnaire(id)  ;
           questionnaireService.supprimer(q)  ;
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppresssion du Questionnaire ");
        alert.setHeaderText("Le Questionnaire est bien supprimée ");
        alert.setContentText("OK!");
        alert.showAndWait();
       Afficher();

    }
    
    
      @FXML
    private void reloadTableView() {
         ServiceQuiz rs =new ServiceQuiz() ;
        
        // Clear the data model
        
        // Refresh the TableView to reflect the changes
            
        List<Questionnaire> questionnaires=rs.getAll() ;
                questionnaires.clear();

        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
      
        // Populate the TableView with data
        questionnaireTableView.getItems().addAll(questionnaires);
           questionnaireTableView.refresh();
    }
    

      @FXML
    private void afficheQuiz() {
       
          ServiceQuiz rs =new ServiceQuiz() ;
      
            
        List<Questionnaire> questionnaires=rs.getAll() ;
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
      
        // Populate the TableView with data
        questionnaireTableView.getItems().addAll(questionnaires);
          
       
    }

}
