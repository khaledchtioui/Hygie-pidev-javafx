/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.gui;

import hygie.entities.Questionnaire;
import hygie.entities.Questions;
import hygie.entities.Reponse;
import hygie.services.ServiceQuestion;
import hygie.services.ServiceReponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bouncycastle.crypto.agreement.srp.SRP6Util;

/**
 * FXML Controller class
 *
 * @author Khaled
 */
public class ReponseController implements Initializable {
         private int id ;
    @FXML
    private Button excel;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
         
         
         


    @FXML
    private TextField reponsetf;
    @FXML
private TableView<Map<String, Object>> tableView;
        private String[] choix ={"True" ,"False"}   ;
        

    private TextField Point;
    @FXML
    private ComboBox<String> combotype;
    @FXML
    private Button Ajouterbutton;
    @FXML
    private Button modiferbtn;
    @FXML
    private Button deletbtn;
    @FXML
    private TableColumn<Map<String, Object>, String> reponseColumn;
    @FXML
    private TableColumn<Map<String, Object>, String> TypeColumn;


    
    
    
     private ObservableList<Map<String, Object>> getData() {
        // Call your getAll2() method to retrieve the data from the database
            ServiceReponse sr = new ServiceReponse();
        
      //   System.out.println("aloo"+this.getQuestionnaireId());// Instantiate your service class
        List<Map<String, Object>> resultList = sr.getbyquestion(id); // Call the getAll2() method to get the data

        // Convert the List<Map<String, Object>> to an ObservableList
        ObservableList<Map<String, Object>> observableList = FXCollections.observableArrayList(resultList);

        return observableList;
    }
    
    
            public void refrech() {
                    tableView.getItems().clear();
            combotype.getItems().addAll(choix);
                    tableView.setItems(getData());
    TableColumn<Map<String, Object>, Integer> IdColumn = new TableColumn<>("reponse ID");
    IdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue().get("reponseid")).asObject());


        // Set up TableColumn cell value factories
     
        reponseColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("reponse")));

      
        TypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("type")));

      tableView.getColumns().addAll(reponseColumn, TypeColumn);
 
            }

    
        public void afficher() {
            tableView.getItems().clear();
            combotype.getItems().addAll(choix);
                    tableView.setItems(getData());
    TableColumn<Map<String, Object>, Integer> IdColumn = new TableColumn<>("reponse ID");
    IdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue().get("reponseid")).asObject());


        // Set up TableColumn cell value factories
     
        reponseColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("reponse")));

      
        TypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("type")));

      tableView.getColumns().addAll(reponseColumn, TypeColumn);
 
       
      
excel.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        ServiceReponse rs = new ServiceReponse()  ;
           FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier Excel");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Fichiers Excel", "*.xlsx", "*.xls"),
            new ExtensionFilter("Tous les fichiers", "*.*"));
        Window stage = null;
        File selectedFile = fileChooser.showOpenDialog(stage);   
        if (selectedFile != null) {
       
                // Créer un objet Workbook pour représenter le fichier Excel
                Workbook workbook = null;
               try {
                   workbook = WorkbookFactory.create(selectedFile);
               } catch (IOException ex) {
                   Logger.getLogger(ReponseController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (EncryptedDocumentException ex) {
                   Logger.getLogger(ReponseController.class.getName()).log(Level.SEVERE, null, ex);
               }

                // Sélectionner la feuille de calcul à lire
                Sheet sheet = workbook.getSheetAt(0);

                // Ouvrir une connexion à la base de données SQLite
              //  Connection conn = DriverManager.getConnection("jdbc:sqlite:donnees.db");

                // Parcourir les lignes de la feuille de calcul
                
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
    Row row = sheet.getRow(i);
    if (row != null) {
  // Lire les valeurs des colonnes "titre" et "type"
                    String titre = row.getCell(0).getStringCellValue();
                     String type   ;
                    if (row.getCell(1).getCellType() == CellType.STRING) {
                     type = row.getCell(1).getStringCellValue();
    } else if (row.getCell(1).getCellType() == CellType.NUMERIC) {
        type = String.valueOf(row.getCell(1).getNumericCellValue());
    } else {
        // handle other cell types if needed
        continue;
    }
                    
                    
                    
                    System.out.println(type);
                    
                    System.out.println(titre);
                     boolean x = false ; 
                     x = type.equalsIgnoreCase("true") || type.equals("1.0");

            
       
                rs.Ajouter(new Reponse(titre,x,new Questions(id)));
             }
}

                
                
                    tableView.setItems(getData());
                    clear();
     
              

               try {
                   // Fermer le fichier Excel, la connexion à la base de données et la boîte de dialogue
                   workbook.close();
               } catch (IOException ex) {
                   Logger.getLogger(ReponseController.class.getName()).log(Level.SEVERE, null, ex);
               }
           
        }
    }
});

 
       
        
        


        

    }    

    
    
    /**
     * Initializes the controller class.
     */
        
        
        
        
          @FXML
       private void Delete() {
                   ServiceReponse sp =new ServiceReponse();
          System.out.println(id);
        Reponse r=new Reponse(id)  ;
           sp.supprimer(r)  ;
       //  List<Questionnaire> list = new ArrayList<>();
        //list = questionnaireService.getAll();
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppresssion du Reponse ");
        alert.setHeaderText("La Reponse est bien supprimée ");
        alert.setContentText("OK!");
        alert.showAndWait();
               tableView.setItems(getData());
               
                    tableView.setItems(getData());
                    clear();


      //  questionnaireTableView.setItems((FXCollections.observableArrayList(list)));
      //quiztf.clear();
      //  afficheQuiz();

    }
 
       
        @FXML
    private void ajouterAction() {
        boolean x = false ; 
        if(combotype.getValue()=="True")
            x=true ;
       
        if(reponsetf.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("reponse  est Obligatoire");
            alert.showAndWait();
        }
       
        else
        {
              Reponse r = new Reponse(reponsetf.getText().toString(),x,new Questions(id));
        ServiceReponse sp =new ServiceReponse();
        
                    sp.Ajouter(r);
                    tableView.setItems(getData());
                    clear();

        }
        
        
        
  //      questionnaireTableView.refresh();
    }
    
       private void clear() {
                
reponsetf.clear();
Point.clear();
combotype.setValue("Null");
    }
    
       
       
           @FXML
    private void Modifier() {
       
        boolean x = false; 
        if(combotype.getValue()=="True")
            x=true ;
         Reponse r = new Reponse(id,reponsetf.getText().toString(),x);
       
        
        
        ServiceReponse sp =new ServiceReponse();
        sp.modifier(r);
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification du reponse ");
        alert.setHeaderText("La reponse est bien modifié ");
        alert.setContentText("OK!");
        alert.showAndWait();
         List<Questionnaire> list = new ArrayList<>();
                             tableView.setItems(getData());
                              clear();

                              
      
    }
  
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
             
        
          tableView.setOnMouseClicked(event -> {
    if (event.getClickCount() == 1) { // Vérifier si c'est un clic simple
        Map<String, Object> rowData = tableView.getSelectionModel().getSelectedItem();
        if (rowData != null) {
             id = (Integer) rowData.get("reponseid");
            String reponse =  (String) rowData.get("reponse")  ;
            String type = (String) rowData.get("type")  ;
            reponsetf.setText(reponse);
           // Point.setText(String.valueOf(point));
            combotype.setValue(type);
            
         //  System.out.println("ID de la ligne sélectionnée : " + questionId);
            // Faites quelque chose avec l'ID récupéré
        }
    }
});

       
        
        
        
        // TODO
    }    
    
}
