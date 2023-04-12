/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxapplication1.entities.Article;
import javafxapplication1.services.ArticleService;

/**
 *
 * @author Yassine
 */
public class AjouterArticleFormController {
    
    
    @FXML
    private TextField tftitle;

    @FXML
    private TextField tfcategorie;

    @FXML
    private TextField tfdescription;

    @FXML
    private TextField tfimage;
    
    @FXML
    private Label Listabel;
    
    @FXML
    private Label confirmationLabel;


    
@FXML
public void ajouterAction(ActionEvent event) {
    if (tftitle.getText().isEmpty() || tfcategorie.getText().isEmpty() || tfdescription.getText().isEmpty() || tfimage.getText().isEmpty()) {
        Listabel.setText("Veuillez remplir tous les champs.");
    } else {
        Article art = new Article(tftitle.getText(), tfdescription.getText(), tfcategorie.getText(), tfimage.getText());
        ArticleService as = new ArticleService();
        as.Ajouter(art);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Article ajouté");
        alert.setHeaderText(null);
        alert.setContentText("L'article a été ajouté avec succès!");
        alert.showAndWait();

        // close the window
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}

    
    
    
    
    
}
