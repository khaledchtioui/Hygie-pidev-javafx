package javafxapplication1.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxapplication1.entities.Article;
import javafxapplication1.entities.Commentaire;
import javafxapplication1.services.ArticleService;
import javafxapplication1.services.ComService;

public class DisplayArticleInfoController  implements Initializable {
    
    @FXML
    private Label lblTitle;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblDateCreation;

    @FXML
    private Label lblImage;

    @FXML
    private TableView <Commentaire> Commentaires;

    @FXML
    private TableColumn<Commentaire, String> CommentairesColContenue;
    
     @FXML
    private TableColumn<Commentaire, Date> CommentairesColDateCreation;
   

    @FXML
    private TableColumn<Commentaire, Void> CommentairesColDelete;

    @FXML
    private Label Listabel;

    private Article selectedArticle;
 
    
    public void setArticle(Article article) {
         selectedArticle = article;
        lblTitle.setText(article.getTitre());
        lblCategory.setText(article.getCategorie());
        
        String truncatedDescription = article.getDescription().substring(0, Math.min(article.getDescription().length(), 20));
        lblDescription.setText(truncatedDescription);
        lblDateCreation.setText(article.getDateCreation().toString());
        lblImage.setText(article.getImage());

        // TODO: Populate the related articles table view based on the given article
    }
    
    
        @FXML
        private void AfficherCom(ActionEvent event) {
            ComService com_s = new ComService();
            
            ArrayList<Commentaire> coms = com_s.Afficher(selectedArticle.getId());
            ObservableList<Commentaire> articleList = FXCollections.observableArrayList(coms);

            CommentairesColContenue.setCellValueFactory(new PropertyValueFactory<>("contenue"));
            CommentairesColDateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
            



            Commentaires.setItems(articleList);
        }
        
        
        
        
          @FXML
         void ouvrirAjouterCommentaire(ActionEvent event) throws IOException {
        // Step 5: Load the FXML file for the secondary view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterComForm.fxml"));
        Parent root = loader.load();

        // Step 6: Get the controller for the secondary view
        AjouterComFormController controller = loader.getController();
        
        // Set up the controller as needed
         controller.setArticle(selectedArticle);

        // Step 7: Create a new Stage object to hold the secondary view
        Stage stage = new Stage();
        Scene scene = new Scene(root);
         stage.setTitle("Ajouter Commenatire");
        // Step 8: Show the new Stage object to display the secondary view
        stage.setScene(scene);
        stage.show();
    }
    
   public void initialize(URL url, ResourceBundle rb) {

                        CommentairesColDelete.setCellFactory(column -> {
                            return new TableCell<Commentaire, Void>() {
                                private final Button deleteButton = new Button("Delete");

                                {
                                    deleteButton.setOnAction((ActionEvent event) -> {
                                        Commentaire selectedcom = getTableView().getItems().get(getIndex());
                                        ComService com_serv = new ComService();
                                        com_serv.Supprimer(selectedcom);
                                        Commentaires.getItems().remove(selectedcom);
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Comment Deleted");
                                            alert.setHeaderText(null);
                                            alert.setContentText("The comment has been successfully deleted.");
                                            alert.showAndWait();
                                    });
                                }

                                @Override
                                protected void updateItem(Void item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (!empty) {
                                        setGraphic(deleteButton);
                                    } else {
                                        setGraphic(null);
                                                }
                                            }
                                        };
                                });

   }

}
