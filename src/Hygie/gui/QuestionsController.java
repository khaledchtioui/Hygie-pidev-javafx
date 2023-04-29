/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.gui;

import hygie.entities.Questionnaire;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import hygie.entities.Questions;
import hygie.services.ServiceQuestion;
import hygie.services.ServiceQuiz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Khaled
 */
public class QuestionsController implements Initializable {

    private int id ; 
    private Questionnaire q;
    @FXML
    private TableColumn<Map<String, Object>, String> questionQuestionColumn;
    @FXML
    private TableColumn<Map<String, Object>, String> questionTypeColumn;
    @FXML
    private TableColumn<Map<String, Object>, Integer> questionPointColumn;
    @FXML
    private Button modiferbtn;
    @FXML
    private Button deletbtn;

    public void setQ(Questionnaire q) {
        this.q = q;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QuestionsController{" + "id=" + id + ", q=" + q.getId() + '}';
    }

   

   
    
    @FXML
    private TextField Questiontf;
      @FXML
    private ComboBox<String> combotype;
    private String[] choix ={"Choix Unique" ,"Choix multiple"}   ;
 //   private int questionnaireId;
    @FXML
    private TextField Point;
    @FXML
    private Button Ajouterbutton;

   

     private ObservableList<Map<String, Object>> getData() {
        // Call your getAll2() method to retrieve the data from the database
        ServiceQuestion serviceQuestion = new ServiceQuestion();
        
      //   System.out.println("aloo"+this.getQuestionnaireId());// Instantiate your service class
        List<Map<String, Object>> resultList = serviceQuestion.getAllbyquiz(q.getId()); // Call the getAll2() method to get the data

        // Convert the List<Map<String, Object>> to an ObservableList
        ObservableList<Map<String, Object>> observableList = FXCollections.observableArrayList(resultList);

        return observableList;
    }
    
    
    

   @FXML
private TableView<Map<String, Object>> tableView;


    
         private   ServiceQuestion quess =new ServiceQuestion();


    /**
     * Initializes the controller class.
     */
             public void initialize(URL url, ResourceBundle rb) {
                 
      

               
    }    

            private void openNewInterface2(int id) {
        
        


       // Create a new stage (window) for the new interface
    Stage stage = new Stage();
    
    // Load the FXML file for the new interface
           
    try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("Reponse.fxml"));
            Parent root = loader.load();

            // Get the controller
            ReponseController controller = loader.getController();
            // Set the ID value
            controller.setId(id);
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

    public void afficher() {
        combotype.getItems().addAll(choix)  ;
        tableView.setItems(getData());
      //  System.out.println(questionnaireId+"dd");
        // Set up TableColumn cell value factories
     
     
        TableColumn<Map<String, Object>, Integer> questionIdColumn = new TableColumn<>("Question ID");
        questionIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue().get("question_id")).asObject());

        questionQuestionColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("question_question")));

        questionTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("question_type")));

        questionPointColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue().get("question_point")).asObject());

        tableView.getColumns().addAll(questionQuestionColumn, questionTypeColumn, questionPointColumn);
 
      
        
         tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
       Map<String, Object> rowData = tableView.getSelectionModel().getSelectedItem();
        if (rowData != null) {
             id = (Integer) rowData.get("question_id");
             System.out.println("alooo"+id);
            String question =  (String) rowData.get("question_question")  ;
            String type = (String) rowData.get("question_type")  ;
          int point = (Integer) rowData.get("question_point");
            System.out.println(question+type+point);
            Questiontf.setText(question);
            Point.setText(String.valueOf(point));
            combotype.setValue(type);
            
         //  System.out.println("ID de la ligne sélectionnée : " + questionId);
            // Faites quelque chose avec l'ID récupéré
        }
            }
        });
        
        
             
                
        
  
                   tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double-click events
                        Map<String, Object> rowData = tableView.getSelectionModel().getSelectedItem();
             int xid = (Integer) rowData.get("question_id");
                System.out.println("idddddd"+xid);
                if (xid != 0) {
                    // Open a new interface or window with the selected questionnaire data
                    openNewInterface2(xid);
                }
            }
        });
       
        
        


        

    }    

    @FXML
    private void ajouterAction(ActionEvent event    ) {
        int x = 2 ; 
        if(combotype.getValue()=="Choix multiple")
            x=1 ;
       
        if(Questiontf.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Question  est Obligatoire");
            alert.showAndWait();
        }
        else if(Point.getText().matches("\\d+")==false)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Point Questionnaire  doit contenir que des chiffres");
            alert.showAndWait();
        }
        else
        {
              Questions qs = new Questions(Questiontf.getText(),x,Integer.valueOf(Point.getText()),q);
        ServiceQuestion sp =new ServiceQuestion();
        
            
                    sp.Ajouter(qs);
                    tableView.setItems(getData());

                    clear();

        }
        
        
        
  //      questionnaireTableView.refresh();
    }
    
    
    
    
      @FXML
       private void Delete() {
                   ServiceQuestion sp =new ServiceQuestion();
          System.out.println(id);
        Questions q=new Questions(id)  ;
           sp.supprimer(q)  ;
       //  List<Questionnaire> list = new ArrayList<>();
        //list = questionnaireService.getAll();
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppresssion du Questionnaire ");
        alert.setHeaderText("Le Questionnaire est bien supprimée ");
        alert.setContentText("OK!");
        alert.showAndWait();
        clear();
               tableView.setItems(getData());

      //  questionnaireTableView.setItems((FXCollections.observableArrayList(list)));
      //quiztf.clear();
      //  afficheQuiz();

    }
       private void clear() {
                
Questiontf.clear();
Point.clear();
combotype.setValue("Null");
    }
    
    
       @FXML
    private void Modifier() {
       
        int x = 2 ; 
        if(combotype.getValue()=="Choix multiple")
            x=1 ;
         Questions q = new Questions(id,Questiontf.getText(),x,Integer.valueOf(Point.getText()),new Questionnaire(60));
       
        
        
        ServiceQuestion sp =new ServiceQuestion();
        sp.modifier(Questiontf.getText(),x,Integer.valueOf(Point.getText()),q);
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification du Question ");
        alert.setHeaderText("Le Questionnaire est bien modifié ");
        alert.setContentText("OK!");
        alert.showAndWait();
         List<Questionnaire> list = new ArrayList<>();
                             tableView.setItems(getData());

         clear();
      
    }
  
    
   
}
