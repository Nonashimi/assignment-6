package ex3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
    private String location;
    private double temperature;
    private String condition;

    public Weather(String location, double temperature, String condition) {
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "location='" + location + '\'' +
                ", temperature=" + temperature +
                ", condition='" + condition + '\'' +
                '}';
    }
}



class OpenWeatherMapAPI implements WeatherAPI {
    @Override
    public Weather getWeatherData(String location) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=5fbce899f96807831b9d7b7c75573555&units=metric";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return new Weather(location, 25.0, "Sunny");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


interface WeatherAPI {
    Weather getWeatherData(String location);
}

class WeatherAdapter implements WeatherAPI {
    private final WeatherAPI sourceAPI;

    public WeatherAdapter(WeatherAPI sourceAPI) {
        this.sourceAPI = sourceAPI;
    }

    @Override
    public Weather getWeatherData(String location) {
        return sourceAPI.getWeatherData(location);
    }
}

class WeatherApp {
    public static void main(String[] args) {
        WeatherAPI weatherAPI = new OpenWeatherMapAPI();
        WeatherAdapter adapter = new WeatherAdapter(weatherAPI);

        Weather weatherData = adapter.getWeatherData("Almaty");
        System.out.println("Weather in " + weatherData.getLocation() + ":");
        System.out.println("Temperature: " + weatherData.getTemperature() + "°C");
        System.out.println("Condition: " + weatherData.getCondition());
    }
}
