package coaching.gui;

import coaching.entity.Reponsee;
import coaching.entity.Sujet;
import coaching.services.ReponseeService;
import coaching.services.SujetService;
import coaching.utils.Mail;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static coaching.utils.BadWords.chackwords;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.fasterxml.jackson.databind.JsonMappingException ;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


public class UserInterfaceController implements Initializable {
    @FXML
    public TableColumn<Sujet, String> columnTitre;
    @FXML
    public TableColumn<Sujet, String> columnContenu;
    @FXML
    public TableColumn<Sujet, String> columnDescription;
    @FXML
    public TableColumn<Sujet, DateCell> columnDate;

    @FXML
    public TableView<Sujet> sujetTableView;

    @FXML
    public TableView<Reponsee> reponseTableView;
    @FXML
    private TableColumn<Reponsee, String> action;
    @FXML
    private TableColumn<Reponsee, Integer> nbdislike;

    @FXML
    private TableColumn<Reponsee, Integer> nblike;
    @FXML
    public TextArea reponseInput;
    @FXML
    public Button ajouterReponse;
    @FXML
    public Button deleteReponsee;

    public static final String ACCOUNT_SID="AC46489cfdf5e1760860546f753dafcec0";
    public static final String AUTH_TOKEN="e2a00a8a6c0a16748c12c82707b8bae7";

    int attention=0;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        columnTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        List<Sujet> list;
        SujetService sujetService = new SujetService();
        list = sujetService.readAllSujets();
        sujetTableView.setItems((FXCollections.observableArrayList(list)));
    }

    @FXML
    public void getselectedSujet() {
        Sujet selectedSujet = sujetTableView.getSelectionModel().getSelectedItem();
        if (selectedSujet == null) {
            return;
        }
        int idSujet = selectedSujet.getId();
        ReponseeService reponseeService = new ReponseeService();
        List<Reponsee> reponseeList = reponseeService.readAssociatedReponses(idSujet);
        columnContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        nblike.setCellValueFactory(new PropertyValueFactory<>("nblike"));
        nbdislike.setCellValueFactory(new PropertyValueFactory<>("nbdislike"));


        Callback<TableColumn<Reponsee, String>, TableCell<Reponsee, String>> cellFoctory;
        cellFoctory = (TableColumn<Reponsee, String> param) -> {
            // make cell containing buttons
            final TableCell<Reponsee, String> cell = new TableCell<Reponsee, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);

                    } else {

                        Button like = new Button("like");
                        Button dislike = new Button("dislike");

                        like.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        dislike.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        like.setOnMouseClicked((MouseEvent event) -> {


                            Reponsee reposne = reponseTableView.getSelectionModel().getSelectedItem();
                            System.out.println(reposne);
                            //ReponseeService rs=new ReponseeService();
                            int nblike = reponseeService.getnblike(reposne.getId());
                            nblike++;
                            reponseeService.setlike(reposne.getId(), nblike);

                        });
                        dislike.setOnMouseClicked((MouseEvent event) -> {


                            Reponsee reponse = reponseTableView.getSelectionModel().getSelectedItem();
                            ReponseeService rs = new ReponseeService();
                            int nbdislike = rs.getnbdislike(reponse.getId());
                                nbdislike++;
                                rs.setdislike(reponse.getId(), nbdislike);
                        });

                        HBox managebtn = new HBox(like, dislike);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(like, new Insets(2, 2, 0, 3));
                        HBox.setMargin(dislike, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                    }
                    setText(null);
                }

            };

            return cell;
        };

        action.setCellFactory(cellFoctory);
        reponseTableView.setItems((FXCollections.observableArrayList(reponseeList)));
    }

    @FXML

    public void ajouterReponse(ActionEvent event) throws IOException {
        if (sujetTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Choix de Sujet est Obligatoire");
            alert.showAndWait();
        } else if (Objects.equals(reponseInput.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La Réponse sur le sujet est Obligatoire");
            alert.showAndWait();
        } else {
            if (chackwords(reponseInput.getText()).equals("false")) {
                int idSujet = sujetTableView.getSelectionModel().getSelectedItem().getId();
                ReponseeService reponseeService = new ReponseeService();
                Reponsee reponsee = new Reponsee();
                reponsee.setSujet_id(idSujet);
                reponsee.setContenu(reponseInput.getText());
                reponsee.setDate(new Date(System.currentTimeMillis()));
                reponseeService.insertReponsee(reponsee);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajout de Réponse ");
                alert.setHeaderText("La réponse est bien ajoutée ");
                alert.setContentText("OK!");
                alert.showAndWait();
                reponseInput.clear();
            } else {
                attention++;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warnning ");
                alert.setHeaderText("Bad words");
                alert.setContentText("Attention");
                alert.showAndWait();

                if(attention>0)
                {
                    System.out.println(attention);
                    Mail.envoyer("wassim","bouassida","wassim.bouassida@esprit.tn");
                    sendSms(52616964,"ATTENTION! la prochaine fois vous serez sanctionnez") ;

                }
            }
        }
    }
    @FXML
    public void getselectedReponse() {

    }

    @FXML
    public void deleteReponsee(ActionEvent event) throws SQLException {
        int reponseeId = reponseTableView.getSelectionModel().getSelectedItem().getId();
        ReponseeService reponseeService = new ReponseeService();
        Reponsee rep = reponseeService.returnReponsee(reponseeId);
        int sujetId = rep.getSujet_id();
        SujetService sujetService = new SujetService();
        Sujet sujet = sujetService.retournerSujet(sujetId);
        sujet.setNbreponse(sujet.getNbreponse() - 1);
        sujetService.updateNbreReponseOnSujet(sujet);
        reponseeService.deleteReponse(reponseeId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression de Réponse ");
        alert.setHeaderText("La réponse est bien supprimée ");
        alert.setContentText("OK!");
        alert.showAndWait();
    }


    public void sendSms(int numero ,String reponse) {
        try {

            Twilio.init(ACCOUNT_SID,AUTH_TOKEN) ;
            Message message = Message.creator(new PhoneNumber("+216"+numero),
                    new PhoneNumber("+16206478408"),
                    "\n \n alert "
                            +reponse).create();

            System.out.println("sms");
//System.out.println(numero);

        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            Alert alert =new Alert(AlertType.INFORMATION) ;
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Réponse n'a pas été prise");
            alert.showAndWait() ;
            //return "Error "+e;
        }
    }
}
