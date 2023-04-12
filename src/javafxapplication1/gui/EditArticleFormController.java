package javafxapplication1.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafxapplication1.entities.Article;
import javafxapplication1.services.ArticleService;

public class EditArticleFormController {

    @FXML
    private TextField titreField;

    @FXML
    private TextField categorieField;

    @FXML
    private TextField descriptionField;



    @FXML
    private TextField imageField;

    @FXML
    private TextField statusField;

    @FXML
    private Label editLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    
    private Article editedArticle;
    
     private DisplayArticlesFXMLController tableController;

    public void setTableController(DisplayArticlesFXMLController tableController) {
        this.tableController = tableController;
    }

    @FXML
    void cancelEdit() {
        // close the window
        cancelButton.getScene().getWindow().hide();
    }


@FXML
void saveEdit() {
    // create a new article object with the updated values
    Article updatedArticle = new Article(
            editedArticle.getId(),
            titreField.getText(),
            descriptionField.getText(),
         
            editedArticle.getDateCreation(),
               categorieField.getText(),
            imageField.getText(),
            statusField.getText().equals("Published")
    );
    // update the article in the database
    ArticleService as = new ArticleService();
    as.Modifier(updatedArticle);
    // close the window
    
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Article Modifié");
    alert.setHeaderText(null);
    alert.setContentText("L'article a été Modifié avec succès!");

    // add event handler to OK button
    ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    alert.getButtonTypes().setAll(okButton);
    Optional<ButtonType> result = alert.showAndWait();

    // check if OK button was clicked
    if (result.isPresent() && result.get() == okButton) {
        saveButton.getScene().getWindow().hide();
}
    
    
}


    public void setArticle(Article article) {
         this.editedArticle = article;

        titreField.setText(article.getTitre());
        categorieField.setText(article.getCategorie());
        descriptionField.setText(article.getDescription());

        imageField.setText(article.getImage());
        statusField.setText(article.isPublished() ? "Published" : "Not Published");
    }


    @FXML
    void initialize() {
        // TODO: initialize the form
    }
}
