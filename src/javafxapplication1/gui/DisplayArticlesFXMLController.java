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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxapplication1.entities.Article;
import javafxapplication1.services.ArticleService;

/**
 * FXML Controller class
 *
 */
public class DisplayArticlesFXMLController implements Initializable {



    @FXML
    private Label Listabel;
    


    @FXML
    private TableView<Article> articleTable;
    
    @FXML
    private TableColumn<Article, Void> colInfo;
        
        
    @FXML
    private TableColumn<Article, String> colTitre;
    @FXML
    private TableColumn<Article, String> colCategorie;
    @FXML
    private TableColumn<Article, String> colDescription;
    @FXML
    private TableColumn<Article, Date> colDateCreation;
    @FXML
    private TableColumn<Article, String> colImage;
    
    @FXML
    private TableColumn<Article, Boolean> colStatus;
    
    
    @FXML
    private TableColumn<Article, Void> colDelete;
    
     @FXML
    private TableColumn<Article, Void> colEdit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                        // Define the delete button cell factory
                        colDelete.setCellFactory(column -> {
                            return new TableCell<Article, Void>() {
                                private final Button deleteButton = new Button("Delete");

                                {
                                    deleteButton.setOnAction((ActionEvent event) -> {
                                        Article selectedArticle = getTableView().getItems().get(getIndex());
                                        ArticleService as = new ArticleService();
                                        as.Supprimer(selectedArticle);
                                        articleTable.getItems().remove(selectedArticle);
                                           Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Article Deleted");
                                            alert.setHeaderText(null);
                                            alert.setContentText("The Article has been successfully deleted.");
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



                        colEdit.setCellFactory(column -> {
                             return new TableCell<Article, Void>() {
                        private final Button editButton = new Button("Edit");

                        {
                            editButton.setOnAction((ActionEvent event) -> {
                                Article selectedArticle = getTableView().getItems().get(getIndex());
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditArticleForm.fxml"));
                                    Parent root = loader.load();

                                    EditArticleFormController controller = loader.getController();
                                    controller.setArticle(selectedArticle);

                                    Stage stage = new Stage();
                                    Scene scene = new Scene(root);
                                    stage.setTitle("Edit Article");
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setGraphic(editButton);
                            } else {
                                        setGraphic(null);
                                    }
                                }
                            };
                        });


                          // Define the info button cell factory
                colInfo.setCellFactory(column -> {
                    return new TableCell<Article, Void>() {
                        private final Button infoButton = new Button("Info");

                        {
                    infoButton.setOnAction((ActionEvent event) -> {
                        Article selectedArticle = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("DisplayArticleInfo.fxml"));
                            Parent root = loader.load();

                            DisplayArticleInfoController controller = loader.getController();
                            controller.setArticle(selectedArticle);

                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle("Article Info");
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setGraphic(infoButton);
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                });
}


    @FXML
    void ouvrirAjouterArticle(ActionEvent event) throws IOException {
        // Step 5: Load the FXML file for the secondary view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterArticleForm.fxml"));
        Parent root = loader.load();

        // Step 6: Get the controller for the secondary view
        AjouterArticleFormController controller = loader.getController();
        // Set up the controller as needed

        // Step 7: Create a new Stage object to hold the secondary view
        Stage stage = new Stage();
        Scene scene = new Scene(root);
         stage.setTitle("Ajouter un Article");
        // Step 8: Show the new Stage object to display the secondary view
        stage.setScene(scene);
        stage.show();
    }
    
        




    @FXML
    private void Afficher(ActionEvent event) {
        ArticleService sp = new ArticleService();
        ArrayList<Article> articles = sp.Afficher();
        ObservableList<Article> articleList = FXCollections.observableArrayList(articles);

        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("published"));

        articleTable.setItems(articleList);
    }

   

    
    

}
