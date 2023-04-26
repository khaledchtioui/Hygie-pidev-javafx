package seance_re.gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyConverter {

    private static final String API_KEY = "33ORO1ZWNQT8IQFL";
    private static final String API_URL = "http://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=%s&apikey=" + API_KEY;

    public static double getPriceInEUR(double price, String currency) throws IOException {
        double exchangeRate = getExchangeRate(currency, "EUR");
        return price / exchangeRate;
    }


    public static double convertPrice(double price, String fromCurrency, String toCurrency) throws IOException {
        double fromExchangeRate = getExchangeRate(fromCurrency, "USD");
        double toExchangeRate = getExchangeRate(toCurrency, "USD");
        double usdPrice = price / fromExchangeRate;
        return usdPrice * toExchangeRate;
    }


    private static double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        String url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCurrency + "&to_currency=" + toCurrency + "&apikey=" + API_KEY;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject exchangeRate = jsonResponse.getJSONObject("Realtime Currency Exchange Rate");
        return exchangeRate.getDouble("5. Exchange Rate");
    }

}
