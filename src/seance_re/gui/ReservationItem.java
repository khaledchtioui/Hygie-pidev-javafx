package seance_re.gui;
import com.google.api.services.calendar.model.*;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.stripe.Stripe;
import java.io.IOException;
import java.math.RoundingMode;
import java.security.GeneralSecurityException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Currency;
import com.paypal.core.PayPalEnvironment;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.http.HttpResponse;
import java.net.URISyntaxException;
import java.util.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import seance_re.entities.Seance;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seance_re.entities.Seance;
import seance_re.services.ServiceSeance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.sql.SQLException;
import java.util.List;

public class ReservationItem extends VBox {
    private ImageView imageView;
    private Label titleLabel;
    private Label descriptionLabel;
    private Label priceLabel;
    private Label dateLabel;
    private Button payButton;


    // Declare the domain objects and services
    private Seance seance;
    private ServiceSeance serviceSeance;
    private static final String APPLICATION_NAME = "hygie";
    private static final String CLIENT_ID = "414083663163-t74k13l9n739g5m2lj5u42738237shd8.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    public ReservationItem(Seance seance, EventHandler<ActionEvent> onAddToCartClicked) {
        // Initialize the domain objects and services
        this.seance = seance;
        this.serviceSeance = new ServiceSeance();
        // Initialize the UI elements
        this.imageView = new ImageView();
        this.titleLabel = new Label();
        this.descriptionLabel = new Label();
        this.priceLabel = new Label();
        this.dateLabel = new Label();
        this.titleLabel.getStyleClass().add("title-label");
        this.descriptionLabel.getStyleClass().add("description-label");
        this.priceLabel.getStyleClass().add("price-label");
        this.dateLabel.getStyleClass().add("date-label");

        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
        VBox.setMargin(titleLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(descriptionLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(priceLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(dateLabel, new Insets(0, 0, 5, 0));
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
            priceLabel.setText(String.format("%s", seance.getPrix()));
            Button payButton = new Button("Payer maintenant");
            payButton.setOnAction(event -> {
                // Create a dialog to choose payment method
                Alert paymentMethodDialog = new Alert(Alert.AlertType.CONFIRMATION);
                paymentMethodDialog.setTitle("Choose Payment Method");
                paymentMethodDialog.setHeaderText("Please select your preferred payment method.");
                paymentMethodDialog.setContentText("Choose your payment method:");
                // Initialize Stripe API
                Stripe.apiKey = "sk_test_XXXXXX"; // Your Stripe API key

// Convert amount from String to Long
                Long amount = Long.parseLong("1000"); // Amount in cents

// Create PaymentIntent object
                PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()
                        .setCurrency("usd")
                        .setAmount(amount)
                        .build();

                // Add buttons for Stripe and PayPal
                ButtonType stripeButton = new ButtonType("Stripe");
                ButtonType paypalButton = new ButtonType("PayPal");
                paymentMethodDialog.getButtonTypes().setAll(stripeButton, paypalButton);

                // Show the dialog and wait for user response
                Optional<ButtonType> result = paymentMethodDialog.showAndWait();

                // Check which button was clicked and open the appropriate payment window
                if (result.isPresent() && result.get() == stripeButton) {
                    openPaymentWindow();
                } else {
                    openPayPalPaymentWindow();
                }
            });

            this.getChildren().addAll(imageView, titleLabel, descriptionLabel, priceLabel, dateLabel, payButton);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void openPaymentWindow() {
        // Code pour ouvrir une fenêtre de paiement avec l'API Stripe
        Stripe.apiKey = "sk_test_51MhDvuAyhtm752dQ76Ig12VR7O8GtAAJv1XrAkHorERJKRzNWTIRwpGgh1PHg65ilRGqDyir3Gk4SSbCZY0a1o2c008VreFCaF";
        Map<String, Object> params = new HashMap<>();
        params.put("amount", BigDecimal.valueOf(seance.getPrix()).multiply(BigDecimal.valueOf(100)).longValueExact());
        params.put("currency", "eur");
        params.put("payment_method_types", Collections.singletonList("card"));

        SessionCreateParams createParams = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("eur")
                                                .setUnitAmount((Long) params.get("amount"))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(seance.getTitre())
                                                                .setDescription(seance.getDescription())
                                                                .build())
                                                .build())
                                .build())
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://example.com/success")
                .setCancelUrl("https://example.com/cancel")
                .build();

        Session session = null;
        try {
            session = Session.create(createParams);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        String url = session.getUrl();
        // Ouvrir une nouvelle fenêtre avec l'URL de paiement

        WebView webVie = new WebView();
        webVie.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.startsWith("https://example.com/success")) {
                // URL de succès détectée, ajouter l'événement au calendrier
                addEventToCalendar();
            }
        });
        Stage stage = new Stage();
        webVie.getEngine().load(url);
        stage.setScene(new Scene(webVie, 800, 600));
        stage.show();


    }

    private void openPayPalPaymentWindow() {
        String PAYPAL_CLIENT_ID = "AWR_4AGp033DZSBeoYP7Hj8BOofMDFHOWWYk9qweuq2yEZtbXmJQ78Y5Gms_mHKn_4317XIPwEkScoRv";
        String PAYPAL_SECRET_KEY = "EI96tACw8ERcchEe5HxABf_3kcLJdqGUUbQ_kzQaJoCo7H6C_Vpp7c2E2ObRXZJaE9-2E8_4RR_C50p3";
        PayPalEnvironment PAYPAL_ENVIRONMENT = new PayPalEnvironment.Sandbox(PAYPAL_CLIENT_ID, PAYPAL_SECRET_KEY);
        PayPalHttpClient payPalHttpClient = new PayPalHttpClient(PAYPAL_ENVIRONMENT);

        // Configurez les détails du paiement, tels que le montant, la devise et la description
        BigDecimal amount = BigDecimal.valueOf(seance.getPrix()).setScale(2, RoundingMode.HALF_UP);
        Currency currency = Currency.getInstance("USD"); // Devise du paiement
        String description = "Achat sur notre site web"; // Description du paiement

        // Créez une demande de paiement avec PayPalCheckoutSdk API
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(new OrderRequest()
                .checkoutPaymentIntent("CAPTURE")
                .purchaseUnits(Arrays.asList(new PurchaseUnitRequest()
                        .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode(currency.getCurrencyCode())
                                .value(amount.toString())
                        )
                        .description(description)
                ))
        );

        try {
            // Récupérez l'URL de l'approbation du paiement et ouvrez-la dans une nouvelle fenêtre de navigateur
            HttpResponse<Order> response = payPalHttpClient.execute(request);
            List<LinkDescription> links = response.result().links();
            String approvalUrl = null;
            for (LinkDescription link : links) {
                if ("approve".equals(link.rel())) {
                    approvalUrl = link.href();
                    break;
                }
            }
            if (approvalUrl == null) {
                throw new RuntimeException("Impossible de trouver l'URL d'approbation du paiement");
            }
            WebView webView = new WebView();
            webView.getEngine().load(approvalUrl);
            Stage stage = new Stage();
            stage.setScene(new Scene(webView, 800, 600));
            stage.show();

        } catch (IOException  e) {
            e.printStackTrace();
            // Gérez les erreurs
        }
    }


        public static Calendar getCalendarService() throws IOException {
        try {
            Credential credential = authorize();
            return new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException e) {
            // handle the exception
            e.printStackTrace();
            return null;
        }
    }
    private static final GoogleAuthorizationCodeFlow flow;

    static {
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    CLIENT_ID,
                    CLIENT_SECRET,
                    SCOPES)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Failed to create GoogleAuthorizationCodeFlow", e);
        }
    }

    public static Credential authorize() {
        try {
            // Autoriser l'application à accéder au compte de l'utilisateur
            Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
            return credential;
        } catch (IOException e) {
            // handle the exception
            e.printStackTrace();
            return null;
        }
    }
    private void addEventToCalendar() {
        try {
            Calendar service = getCalendarService();
            Event event = new Event()
                    .setSummary(seance.getTitre())
                    .setDescription(seance.getDescription());

            // Set the start and end time of the event
            DateTime startTime = new DateTime(seance.getDate());
            DateTime endTime = new DateTime(seance.getDate().getTime() + 3600000); // End time is 1 hour after start time
            EventDateTime start = new EventDateTime().setDateTime(startTime);
            EventDateTime end = new EventDateTime().setDateTime(endTime);
            event.setStart(start).setEnd(end);

            service.events().insert("primary", event).execute();

            // Open Google Calendar
            String url = "https://calendar.google.com/calendar/";
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(url));
            } else {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("xdg-open " + url);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




}




