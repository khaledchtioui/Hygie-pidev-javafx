/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxapplication1.entities.Article;
import javafxapplication1.entities.Commentaire;
import javafxapplication1.services.ArticleService;
import javafxapplication1.services.ComService;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class AjouterComFormController implements Initializable {

      
    @FXML
    private TextField contenue;

    @FXML
    private Label Listabel;
    

    
     private Article selectedArticle;
 
    
    public void setArticle(Article article) {
         selectedArticle = article;

    }
    
    @FXML
    public void ajouterAction(ActionEvent event) {
        
        
        if (contenue.getText().isEmpty() ) {
            Listabel.setText("Veuillez remplir tous les champs.");
        } else {
            Commentaire com = new Commentaire(contenue.getText(), selectedArticle.getId());
          
            ComService com_serv = new ComService();
            com_serv.Ajouter(com);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commentaire ajouté");
            alert.setHeaderText(null);
            alert.setContentText("Commentaire a été ajouté avec succès!");
            alert.showAndWait();

            // close the window
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
