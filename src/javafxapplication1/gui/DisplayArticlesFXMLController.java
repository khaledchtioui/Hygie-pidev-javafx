package javafxapplication1.gui;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ComboBox<String> categorieComboBox;

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
    private TableColumn<Article, ImageView> colQRCode;

    
    @FXML
    private TableColumn<Article, Boolean> colStatus;
    
    
    @FXML
    private TableColumn<Article, Void> colDelete;
    
     @FXML
    private TableColumn<Article, Void> colEdit;
     
     @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
                        // Populate category combo box
                     ArticleService sp = new ArticleService();
                     ArrayList<Article> articles = sp.Afficher();
                     ObservableList<String> categories = FXCollections.observableArrayList();
                     categories.add("ALL"); // Add an empty category option
                     for (Article article : articles) {
                         String category = article.getCategorie();
                         if (!categories.contains(category)) {
                             categories.add(category);
                         }
                     }
                     categorieComboBox.setItems(categories);

 
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
        public void rechercher(ActionEvent event) {
            String searchText = searchField.getText();
            // perform search operation using searchText
            // for example, you can filter the table items based on the search text
            FilteredList<Article> filteredData = new FilteredList<>(articleTable.getItems());
            filteredData.setPredicate(article -> {
                // check if the search text is empty
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }

                // check if the search text matches any of the article properties
                String lowerCaseSearchText = searchText.toLowerCase();
                if (article.getTitre().toLowerCase().contains(lowerCaseSearchText)) {
                    return true; // match found in Titre property
                } else if (article.getCategorie().toLowerCase().contains(lowerCaseSearchText)) {
                    return true; // match found in Categorie property
                } else if (article.getDescription().toLowerCase().contains(lowerCaseSearchText)) {
                    return true; // match found in Description property
                } else {
                    return false; // no match found
                }
            });

            // set the filtered data to the table
            articleTable.setItems(filteredData);
        }

    

        @FXML
        private void Afficher(ActionEvent event) {
            ArticleService sp = new ArticleService();
            ArrayList<Article> articles = sp.Afficher();

            // Filter the articles based on the selected category
            String selectedCategory = categorieComboBox.getSelectionModel().getSelectedItem();
            if (selectedCategory != null && !selectedCategory.isEmpty() && !selectedCategory.equals("ALL")) {
                articles = articles.stream()
                        .filter(article -> article.getCategorie().equals(selectedCategory))
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            ObservableList<Article> articleList = FXCollections.observableArrayList(articles);

            colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
            colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
            colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("published"));
            colQRCode.setCellValueFactory(cellData -> new SimpleObjectProperty<>(generateQRCode(cellData.getValue())));

            articleTable.setItems(articleList);
        }



/*
    
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
    
*/
    
    private ImageView generateQRCode(Article article) {
    // Combine article information to generate QR code content
    String content = article.getId() + " - " + article.getTitre() + " - " + article.getCategorie() + " - " + article.getDescription();

    // Set up QR code parameters
    int size = 200;
    String encoding = "UTF-8";

    // Generate QR code
    try {
        BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        ImageView imageView = new ImageView(image);
        return imageView;
    } catch (WriterException e) {
        e.printStackTrace();
        return null;
    }
}


   

    
    

}
