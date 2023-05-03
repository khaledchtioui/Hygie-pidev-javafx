package coaching.gui;

import coaching.entity.Reponsee;
import coaching.entity.Sujet;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLSujetAssociatedReponsee implements Initializable {

    private List<Reponsee> associatedReponsee;

    @FXML
    public TableView<Reponsee> associatedReponseeTableView;
    @FXML
    public TableColumn<Sujet, String> columnContenu;
    @FXML
    public TableColumn<Sujet, DateCell> columnDate;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        columnContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        associatedReponseeTableView.setItems((FXCollections.observableArrayList(associatedReponsee)));
    }

    @FXML
    public void supprimerReponse(ActionEvent event) {

    }

    public List<Reponsee> getAssociatedReponsee() {
        return associatedReponsee;
    }

    public void setAssociatedReponsee(List<Reponsee> associatedReponsee) {
        this.associatedReponsee = associatedReponsee;
    }

}


