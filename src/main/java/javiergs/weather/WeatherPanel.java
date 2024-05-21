package javiergs.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Panel for the Weather
 * It shows the current temperature from AccuWeather
 *
 * @author javiergs
 * @version 1.0
 */
public class WeatherPanel extends JPanel {
	
	public WeatherPanel() {
		setLayout(new GridLayout(1, 1));
		try {
			double value = getTemperature();
			add(new JLabel(" " + value));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private double getTemperature() throws Exception {
		String locationKey = "331999"; // San Luis Obispo
		// get key from properties file
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		String apiKey = properties.getProperty("WEATHER_API_KEY");
		String urlStr = properties.getProperty("WEATHER_SERVER") + "/ "
			+ locationKey + "?apikey=" + apiKey;
		// create connection
		URL url = new URL(urlStr);
		URLConnection connection = url.openConnection();
		InputStream inputStream = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String response = "";
		String inputLine = "";
		while ((inputLine = reader.readLine()) != null) {
			response += inputLine;
		}
		inputStream.close();
		return parse(response);
	}
	
	private double parse(String response) {
		System.out.println(response);
		JSONArray jsonResponse = new JSONArray(response);
		JSONObject currentWeather = jsonResponse.getJSONObject(0);
		JSONObject temperatureObj = currentWeather.getJSONObject("Temperature");
		JSONObject metricObj = temperatureObj.getJSONObject("Metric");
		return metricObj.getDouble("Value");
	}
	
}