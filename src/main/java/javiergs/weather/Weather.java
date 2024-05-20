package javiergs.weather;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import org.json.*;

public class Weather extends JPanel {
	
	/**
	 * Constructor
	 */
	public Weather() {
		setLayout(new GridLayout(1,1));
		try {
			double value = getTemperature();
			add(new JLabel (" " + value));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the weather from AccuWeather
	 *
	 * @return The weather
	 */
	private double getTemperature() throws Exception {
		
		String locationKey = "331999"; // San Luis Obispo
		String apiKey = "NrWxKW22dkUWwTyCrQP1rqRTTmVlpXlf";
		String urlStr = "http://dataservice.accuweather.com/currentconditions/v1/"
			+ locationKey
			+ "?apikey=" + apiKey;
		
		URL url = new URL(urlStr);
		URLConnection connection = url.openConnection();
		InputStream inputStream = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String response = "";
		String inputLine ="";
		while ((inputLine = reader.readLine()) != null) {
			response += inputLine;
		}
		inputStream.close();
		return parse (response);
	}
	
	/**
	 * Parses the JSON response
	 *
	 * @param response in JSON format
	 * @return the weather temperature
	 */
	private double parse (String response) {
		System.out.println(response);
		JSONArray jsonResponse = new JSONArray(response);
		JSONObject currentWeather = jsonResponse.getJSONObject(0);
		JSONObject temperatureObj = currentWeather.getJSONObject("Temperature");
		JSONObject metricObj = temperatureObj.getJSONObject("Metric");
		return metricObj.getDouble("Value");
	}
	
	/**
	 * javiergs.virtualearth.Main method just for testing
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args)  {
		JFrame test = new JFrame("javiergs.weather.Weather");
		test.setLayout(new GridLayout(1, 1));
		test.add(new Weather());
		test.setSize(200, 200);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

