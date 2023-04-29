/*package seance_re.services;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
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
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private static final String STRIPE_SECRET_KEY = "your_stripe_secret_key_here";
    private static final String PAYPAL_CLIENT_ID = "your_paypal_client_id_here";
    private static final String PAYPAL_SECRET_KEY = "your_paypal_secret_key_here";
    private static final PayPalEnvironment PAYPAL_ENVIRONMENT = new PayPalEnvironment.Sandbox(PAYPAL_CLIENT_ID, PAYPAL_SECRET_KEY);
    private final PayPalHttpClient payPalHttpClient;

    public PaymentService() {
        payPalHttpClient = new PayPalHttpClient(PAYPAL_ENVIRONMENT);
        Stripe.apiKey = STRIPE_SECRET_KEY;
    }

    public void openPaymentWindow(Seance seance) throws StripeException, IOException {
        float price = seance.getPrix();

        // Use Stripe to create a payment intent
        PaymentIntent.create(new PaymentIntent.CreateParams.Builder()
                .setCurrency("eur")
                .setAmount(BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(100)).intValue())
                .build());
        // Use PayPal to create an order
        Order order = new Order();
        order.checkoutPaymentIntent(new CheckoutPaymentIntentRequest()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode("EUR")
                        .value(BigDecimal.valueOf(price).toString()))
                .intent(OrderIntent.CAPTURE));
        order.applicationContext(new ApplicationContext()
                .brandName("Seance-RE")
                .landingPage("BILLING")
                .userAction("PAY_NOW"));

        // Add the seance details to the order
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item()
                .name(seance.getTitre())
                .quantity("1")
                .unitAmount(new Money()
                        .currencyCode("EUR")
                        .value(BigDecimal.valueOf(price).toString())));
        order.purchaseUnits(new ArrayList<>(List.of(new PurchaseUnit()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .itemTotal(new Money()
                                .currencyCode("EUR")
                                .value(BigDecimal.valueOf(price).toString())))))));
        order.checkoutPaymentIntent(new CheckoutPaymentIntentRequest()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .itemTotal(new Money()
                                .currencyCode("EUR")
                                .value(BigDecimal.valueOf(price).toString())))));

        // Use PayPal to create the order and get the redirect URL for the payment window
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(order);
        HttpResponse<Order> response = payPalHttpClient.execute(request);
        Order createdOrder = response.result();
        String redirectUrl = createdOrder.links().stream()
                .filter(link -> link.rel().equals("approve"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find PayPal redirect URL"))
                .href();

        // Open the payment window in the user's browser
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(redirectUrl));
        } else {
            // Handle the case where desktop browsing is not supported
            throw new RuntimeException("Desktop browsing not supported");
        }
    }
}
*/