/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import hygie.entities.Reclamation;
import hygie.services.ServiceReclamation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLReclamationController implements Initializable {

    @FXML
    private TextField texttitre;
    @FXML
    private TextField texttype;
    @FXML
    private TextField textstatus;
    @FXML
    private Button btnajouter;
    @FXML
    private TextField textdes;
    @FXML
    private Button btnsupp;
    @FXML
    private Button btnmodifier;
    @FXML
    private TableView<Reclamation> table_reclamation;
    @FXML
    private TableColumn<Reclamation, Integer> idr;
    @FXML
    private TableColumn<Reclamation, String> dater;
    @FXML
    private TableColumn<Reclamation, String> titrer;
    @FXML
    private TableColumn<Reclamation, String> typer;
    @FXML
    private TableColumn<Reclamation, String> descriptionr;
    @FXML
    private TableColumn<Reclamation, String> statusr;

    /**
     * Initializes the controller class.
     */
    private void UpdateTable() {
        
            List<Reclamation> list=new ArrayList<>();
        
        ServiceReclamation ss=new ServiceReclamation();
       list = ss.getAll();
        
        ObservableList<Reclamation> obs=FXCollections.observableArrayList(list);
        idr.setCellValueFactory(new PropertyValueFactory<Reclamation ,Integer>("id"));
        dater.setCellValueFactory(new PropertyValueFactory<Reclamation ,String>("date"));
        titrer.setCellValueFactory(new PropertyValueFactory<Reclamation ,String>("titre"));
        typer.setCellValueFactory(new PropertyValueFactory<Reclamation ,String>("type"));
        descriptionr.setCellValueFactory(new PropertyValueFactory<Reclamation ,String>("descreption"));
        statusr.setCellValueFactory(new PropertyValueFactory<Reclamation ,String>("status"));


       
        table_reclamation.setItems(obs);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         UpdateTable();
        // TODO
    }    

    @FXML
    private void ajouter(ActionEvent event) {
         String Dep=textdes.getText();

    if(Dep.isEmpty()){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Vous devez remplir tous les champs"); 
             alert.showAndWait();
    }else{
   Reclamation t = new Reclamation(String.valueOf(texttitre.getText()),String.valueOf(texttype.getText()),String.valueOf(textdes.getText()),String.valueOf(textstatus.getText()));
        ServiceReclamation s =new ServiceReclamation();
        s.ajouter(t);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText("Insertion avec succés");
                alert.showAndWait();
        UpdateTable();
    }
    }

    @FXML
    private void supprimer(ActionEvent event) {
                ServiceReclamation is=new ServiceReclamation();
        Reclamation selected_it =  table_reclamation.getSelectionModel().getSelectedItem();
        is.delete(selected_it.getId());
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText("Suppression avec succés");
                alert.showAndWait();
        UpdateTable();
    }

    @FXML
    private void modifier(ActionEvent event) {
      //  if (text.getText().isEmpty() || ) {
       // Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
      //  a.showAndWait();
  //} else {
        ServiceReclamation sr = new ServiceReclamation();
        Reclamation r;

        // Get the selected row from the TableView
        Reclamation selectedStation = table_reclamation.getSelectionModel().getSelectedItem();

        // Get the id_station value from the selected row
        int id_rec = selectedStation.getId();

        r = new Reclamation(id_rec, texttitre.getText(), texttype.getText(),textdes.getText(),textstatus.getText());
        sr.modifier(r);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "reclamation modifiée", ButtonType.OK);
        alert.show();
        UpdateTable();
        
   // }
}

    
}
