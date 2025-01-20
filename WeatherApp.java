import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WeatherApp {

    // API endpoint and key (you can replace it with your own API key)
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY&units=metric";

    public static void main(String[] args) {
        try {
            // Create an HTTP client
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // Create an HTTP GET request
            HttpGet request = new HttpGet(API_URL);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            // Convert the response to a string
            String responseString = EntityUtils.toString(entity);

            // Parse the response string using Gson
            JsonObject jsonResponse = JsonParser.parseString(responseString).getAsJsonObject();

            // Extract weather data
            double temperature = jsonResponse.getAsJsonObject("main").get("temp").getAsDouble();
            int humidity = jsonResponse.getAsJsonObject("main").get("humidity").getAsInt();
            int pressure = jsonResponse.getAsJsonObject("main").get("pressure").getAsInt();
            String weatherDescription = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
            double windSpeed = jsonResponse.getAsJsonObject("wind").get("speed").getAsDouble();

            // Display the extracted data in a structured format
            System.out.println("Weather Information for London:");
            System.out.println("--------------------------------");
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Pressure: " + pressure + " hPa");
            System.out.println("Weather: " + weatherDescription);
            System.out.println("Wind Speed: " + windSpeed + " m/s");

            // Close the HTTP client and response
            response.close();
            httpClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
