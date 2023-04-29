package seance_re.gui;
import javax.money.CurrencyUnit;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.MonetaryConversions;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import javax.money.convert.ExchangeRate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import seance_re.entities.Seance;
import seance_re.services.ServiceSeance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;

public class CartitemController extends VBox {
    // Initialize the currencyComboBox

    // Declare the UI elements
    private ImageView imageView;
    private Label titleLabel;
    private Label descriptionLabel;
    private Label priceLabel;
    private Label dateLabel;
    private Button addToCartButton;

    // Declare the domain objects and services
    private Seance seance;
    private ServiceSeance serviceSeance;
    private ComboBox<String> currencyComboBox;

    public CartitemController(Seance seance, EventHandler<ActionEvent> onAddToCartClicked) {

        // Initialize the domain objects and services
        this.seance = seance;
        this.serviceSeance = new ServiceSeance();
        // Initialize the UI elements
        this.imageView = new ImageView();
        this.titleLabel = new Label();
        this.descriptionLabel = new Label();
        this.priceLabel = new Label();
        // Initialize the price label with the converted price
        this.dateLabel = new Label();
        this.currencyComboBox = new ComboBox<>();

        // ...

        // Initialize the price label with the converted price
        this.addToCartButton = new Button("Ajouter au panier");

        // Set the button's on-click event handler
        addToCartButton.setOnAction(onAddToCartClicked);
        this.setStyle("-fx-padding: 10px; -fx-background-color: #f0f0f0;");

        // Set the VBox's style class and external stylesheet
        this.titleLabel.getStyleClass().add("title-label");
        this.descriptionLabel.getStyleClass().add("description-label");
        this.priceLabel.getStyleClass().add("price-label");
        this.dateLabel.getStyleClass().add("date-label");
        this.addToCartButton.getStyleClass().add("add-to-cart-button");

        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
        VBox.setMargin(titleLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(descriptionLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(priceLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(dateLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(addToCartButton, new Insets(10, 0, 0, 0));

        // Set the UI elements' properties
        try {
            // Set the image
            String encodedImage = seance.getImage();
            if (encodedImage != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
                Image decodedImage = new Image(new ByteArrayInputStream(decodedBytes));
                imageView.setImage(decodedImage);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);
            }

            // Set the title, description, price and date
            titleLabel.setText(seance.getTitre());
            descriptionLabel.setText(seance.getDescription());
            dateLabel.setText(String.format("%s", seance.getDate()));
// Initialize the currencyComboBox
            ObservableList<String> currencyOptions = FXCollections.observableArrayList("USD", "GBP", "JPY");
            currencyComboBox.setItems(currencyOptions);
            currencyComboBox.getSelectionModel().selectFirst();

// Set the event handler for the currencyComboBox
            currencyComboBox.setOnAction(event -> {
                // Get the selected currency code

                // Définir une instance de CurrencyUnit pour EUR
                CurrencyUnit eur = Monetary.getCurrency("USD");

// Obtenir le code de devise sélectionné à partir de la liste déroulante
                String selectedCurrencyCode = currencyComboBox.getSelectionModel().getSelectedItem();

                try {
                    // Définir une instance de CurrencyUnit pour la devise sélectionnée
                    CurrencyUnit selectedCurrency = Monetary.getCurrency(selectedCurrencyCode);

                    // Créer des objets MonetaryAmount avec des montants de 1 unité dans chaque devise
                    MonetaryAmount oneEur = Monetary.getDefaultAmountFactory().setCurrency(eur).setNumber(1).create();
                    MonetaryAmount oneSelectedCurrency = Monetary.getDefaultAmountFactory().setCurrency(selectedCurrency).setNumber(1).create();

                    // Obtenir le taux de change entre les deux devises
                    CurrencyConversion conversion = MonetaryConversions.getConversion(selectedCurrency);
                    ExchangeRate exchangeRate = conversion.getExchangeRate(oneEur);
                    // Get the number format for the selected currency
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

// Convert the price to the selected currency and format it using the currency format
                    BigDecimal convertedPrice = BigDecimal.valueOf(seance.getPrix()).multiply(exchangeRate.getFactor().numberValueExact(BigDecimal.class));
                    String formattedPrice = currencyFormat.format(convertedPrice);

// Set the formatted price on the label
                    priceLabel.setText(formattedPrice);

                } catch (UnknownCurrencyException e) {
                    // Gérer l'exception si le code de devise sélectionné n'est pas reconnu
                    priceLabel.setText("Erreur: devise inconnue");
                }


            });



// Initialize the price label with the converted price
            String initialPrice = String.format("%s EUR", seance.getPrix());
            priceLabel.setText(initialPrice);

// Add the currencyComboBox to the VBox
            this.getChildren().add(currencyComboBox);
// Initialize the currencyComboBox


// Add the currencyComboBox to the VBox
            // Add the UI elements to the VBox
            this.getChildren().addAll(imageView, titleLabel, descriptionLabel, priceLabel, dateLabel, addToCartButton);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Set the button's on-click event handler
        addToCartButton.setOnAction(event -> {
            try {
                serviceSeance.reserveSeance(seance);
                // Refresh the reservations table here
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
    private String convertPrice(String price, String currency) {
        try {
            CurrencyUnit sourceCurrency = Monetary.getCurrency("EUR");
            CurrencyUnit targetCurrency = Monetary.getCurrency(currency);

            MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setCurrency(sourceCurrency)
                    .setNumber(Double.parseDouble(price)).create();

            CurrencyConversion conversion = MonetaryConversions.getConversion(targetCurrency);
            MonetaryAmount convertedPrice = monetaryAmount.with(conversion);

            return String.format("%s %s", convertedPrice.getNumber(), targetCurrency.getCurrencyCode());
        } catch (NumberFormatException | UnknownCurrencyException e) {
            e.printStackTrace();
            return price;
        }
    }


}