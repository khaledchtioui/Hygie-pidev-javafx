/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.gui;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
//

import org.controlsfx.control.Notifications;
//

import hygie.entities.Questionnaire;
import hygie.services.ServiceQuiz;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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


    private ServiceQuiz questionnaireService =new ServiceQuiz() ;
    @FXML
    private Button Delete;
    @FXML
    private Button generate;
    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> combotri;  
    private String[] choixx ={"Titre" ,"Date"}   ;

    List<Questionnaire>   questionnaires22     ;
 
    private   List<Questionnaire> questionnaires2     ;
    
    
    

    /**
     * Initializes the controller class.
     */
    
    
    
    public void showNotification(String message) {
    Label label = new Label(message);
    Rectangle rectangle = new Rectangle(400, 50);
    rectangle.setFill(Color.GREEN);

    StackPane stackPane = new StackPane(rectangle, label);
    stackPane.setAlignment(Pos.CENTER);

    Notifications notifications = Notifications.create()
            .hideAfter(Duration.seconds(6))
            .position(Pos.BOTTOM_RIGHT)
            .darkStyle()
            .graphic(stackPane)
            .title("Notification");

    notifications.show();
}
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                    combotri.getItems().addAll(choixx);

        
             ServiceQuiz rs =new ServiceQuiz() ;
      
      
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
                    openNewInterface(selectedQuestionnaire,id);
                }
            }
        });
            
            
                             questionnaireTableView.getItems().clear();
        questionnaires2=rs.getAll() ;
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
      
        // Populate the TableView with data
        questionnaireTableView.getItems().addAll(questionnaires2);
        // TODO
        
        
            
            
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                
                    if (newValue == null || newValue.isEmpty()) {
                       
                        
                             questionnaireTableView.getItems().clear();
        List<Questionnaire> questionnaires1=rs.getAll() ;
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
      
        questionnaireTableView.getItems().addAll(questionnaires1);
        
        

                    }
else
                    {
                   questionnaireTableView.getItems().clear();
                questionnaires2=rs.getAllsearch(newValue);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
      
        questionnaireTableView.refresh();
        questionnaireTableView.getItems().addAll(questionnaires2);
                
                    }
                        
              
                
                
});    

            combotri.setOnAction(e -> {
    String choix = combotri.getValue();
                trierDonnees(choix);
    
    
    
    
    }
    )    ;
            
   

         
        // TODO
    }    
   
    
  
    
    public void trierDonnees(String choix) {
    switch (choix) {
      
        case "Date":
            Collections.sort(questionnaires2, new Comparator<Questionnaire>() {
        @Override
        public int compare(Questionnaire o1, Questionnaire o2) {

   return o1.getDate().compareTo(o2.getDate())    ;

        }
    } );
            break;
        case "Titre":
 Collections.sort(questionnaires2, new Comparator<Questionnaire>() {
        @Override
        public int compare(Questionnaire o1, Questionnaire o2) {

   return o1.getNom().compareTo(o2.getNom())    ;

        }
    } );            break;
        default:
            
 Collections.sort(questionnaires2, new Comparator<Questionnaire>() {
        @Override
        public int compare(Questionnaire o1, Questionnaire o2) {

   return o1.getId()-o2.getId()    ;

        }
    } );            
            
            
            
            break;
    }
    
    ServiceQuiz rs = new ServiceQuiz()  ;
    
    questionnaireTableView.getItems().clear();
     questionnaires22=rs.getAll() ;
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
      
        // Populate the TableView with data
        questionnaireTableView.getItems().addAll(questionnaires2);
       
    
    
    
}

    
    
    
     @FXML

            public void handle(ActionEvent event) {
                // Call the exportToExcel method to generate the Excel file
               ExcelExporter.exportToExcel();
               
            }
    private void openNewInterface(Questionnaire questionnaire,int id) {
        
        


       // Create a new stage (window) for the new interface
    Stage stage = new Stage();
    
    // Load the FXML file for the new interface
           
    try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("Questions.fxml"));
            Parent root = loader.load();

            // Get the controller
            QuestionsController controller = loader.getController();

            // Set the ID value
            controller.setId(questionnaire.getId());
            controller.setQ(questionnaire);
            controller.afficher();
            System.out.println(controller);

    Scene scene = new Scene(root);
    stage.setScene(scene);
    
    // Show the new stage
    stage.show();
        
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }
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
    private void Afficher() {
                ServiceQuiz sp =new ServiceQuiz();

         quiztf.clear();
// Vider le contenu de la TableView
questionnaireTableView.getItems().clear();

                      List<Questionnaire> questionnaires=sp.getAll() ;

                      questionnaireTableView.getItems().addAll(questionnaires);

              
        questionnaireTableView.refresh();
        
     //   ServiceQuiz sp = new ServiceQuiz();
      //  Listabel.setText(sp.getAll().toString());
       
    }
     @FXML
    private void handleGeneratePDF() {
        Stage stage = (Stage) generate.getScene().getWindow();
        PDFGenerator.generatePDF(stage,id);
       // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("PDF Export");
       // alert.setHeaderText("PDF file created and saved successfully.");
     //   alert.showAndWait();
        
        
//int flags = MB_OK | MB_ICONINFORMATION;
//  User32.INSTANCE.MessageBox(null, message, title, flags);
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
