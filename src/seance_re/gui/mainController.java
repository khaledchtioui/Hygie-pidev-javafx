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
import java.util.*;

public class mainController implements Initializable {


    @FXML
    private GridPane cartItemGridPane;



    // Declare the ObservableList instance variable
    private ServiceSeance serviceSeance;
    private ArrayList<CartitemController> cartItemControllers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceSeance = new ServiceSeance();
        cartItemControllers = new ArrayList<>();
        // Récupération des séances depuis la base de données

        ArrayList<Seance> seances = serviceSeance.Afficher();

        // Set event handler for currencyComboBox


        // Parcours de la liste des séances et création des cartes d'articles
        int row = 0;
        int col = 0;
        for (Seance seance : seances) {
            // Création de la carte d'article
            CartitemController cartitemController = new CartitemController(seance, event -> {
                // Code pour ajouter l'article au panier
            });
            cartItemControllers.add(cartitemController); // Add the cart item controller to the list
            VBox cartVBox = new VBox(cartitemController); // Add the currencyComboBox to the VBox
            // Ajout de la carte d'article au GridPane
            cartItemGridPane.add(cartVBox, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }



    public double convert(double amount, String fromCurrencyCode, String toCurrencyCode) {
        try {
            CurrencyUnit fromCurrency = Monetary.getCurrency(fromCurrencyCode);
            CurrencyUnit toCurrency = Monetary.getCurrency(toCurrencyCode);
            CurrencyConversion conversion = MonetaryConversions.getConversion(toCurrency);
            MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setCurrency(fromCurrency).setNumber(amount).create();
            MonetaryAmount convertedAmount = conversion.apply(monetaryAmount);
            return convertedAmount.getNumber().doubleValueExact();
        } catch (UnknownCurrencyException e) {
            System.err.println("Unknown currency code: " + e.getCurrencyCode());
            return 0.0;
        }
    }





}