package seance_re.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seance_re.entities.Seance;
import seance_re.services.ServiceSeance;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

import javax.money.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ResevationMain implements Initializable {


    @FXML
    private GridPane cartItemGridPane;



    // Declare the ObservableList instance variable
    private ServiceSeance serviceSeance;
    private ArrayList<ReservationItem> ReservationItems;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceSeance = new ServiceSeance();
        ReservationItems = new ArrayList<>();
        // Récupération des séances depuis la base de données

        ArrayList<Seance> seances = null;
        try {
            seances = serviceSeance.afficherSeancesReservees();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Parcours de la liste des séances et création des cartes d'articles
        int row = 0;
        int col = 0;
        for (Seance seance : seances) {
            // Création de la carte d'article
           ReservationItem ReservationItem = new ReservationItem(seance, event -> {
                // Code pour ajouter l'article au panier
            });
            ReservationItems.add(ReservationItem); // Add the cart item controller to the list
            VBox cartVBox = new VBox(ReservationItem); // Add the currencyComboBox to the VBox
            // Ajout de la carte d'article au GridPane
            cartItemGridPane.add(cartVBox, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }


    }



