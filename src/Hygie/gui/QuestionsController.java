/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hygie.gui;

import Hygie.entities.Questionnaire;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Hygie.entities.Questions;
import Hygie.services.ServiceQuestion;
import Hygie.services.ServiceQuiz;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Khaled
 */
public class QuestionsController implements Initializable {

    private int id ; 
    private Questionnaire q;

    public void setQ(Questionnaire q) {
        this.q = q;
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
        List<Map<String, Object>> resultList = serviceQuestion.getAll2(); // Call the getAll2() method to get the data

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combotype.getItems().addAll(choix)  ;
        tableView.setItems(getData());
      //  System.out.println(questionnaireId+"dd");
        // Set up TableColumn cell value factories
     
     
        TableColumn<Map<String, Object>, Integer> questionIdColumn = new TableColumn<>("Question ID");
        questionIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue().get("question_id")).asObject());

        TableColumn<Map<String, Object>, String> questionQuestionColumn = new TableColumn<>("Question");
        questionQuestionColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("question_question")));

        TableColumn<Map<String, Object>, String> questionTypeColumn = new TableColumn<>("Question Type");
        questionTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("question_type")));

        TableColumn<Map<String, Object>, Integer> questionPointColumn = new TableColumn<>("Question Point");
        questionPointColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue().get("question_point")).asObject());

        tableView.getColumns().addAll(questionIdColumn, questionQuestionColumn, questionTypeColumn, questionPointColumn);
 
        tableView.setOnMouseClicked(event -> {
    if (event.getClickCount() == 1) { // Vérifier si c'est un clic simple
        Map<String, Object> rowData = tableView.getSelectionModel().getSelectedItem();
        if (rowData != null) {
             id = (Integer) rowData.get("question_id");
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


       
        
        


        

    }    

    @FXML
    private void afficheQuiz(MouseEvent event) {
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
              Questions q = new Questions(Questiontf.getText(),x,Integer.valueOf(Point.getText()),new Questionnaire(60));
        ServiceQuestion sp =new ServiceQuestion();
        
            
                    sp.Ajouter(q);
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
    @FXML
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
