package javiergs.openai;

import org.json.JSONArray;
import org.json.JSONObject;

import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * This class is responsible for the chatbot logic
 * It sends the user input to the OpenAI API and gets the response
 *
 * @version 1.0
 * @author javiergs
 */
public class ChatbotBrain extends PropertyChangeSupport implements Runnable {
	
	private String input;
	
	public ChatbotBrain(String input) {
		super(new Object());
		this.input = input;
	}
	
	@Override
	public void run() {
		String output = getChatGPTResponse();
		firePropertyChange("output", null, output);
	}
	
	private String getChatGPTResponse() {
		try {
			// get key from properties file
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			String OPENAI_API_KEY = properties.getProperty("OPENAI_API_KEY");
			String OPENAI_SERVER_URL = properties.getProperty("OPENAI_SERVER");
			// create connection
			HttpURLConnection connection = (HttpURLConnection) new URL(OPENAI_SERVER_URL).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
			JSONObject data = new JSONObject();
			data.put("model", "gpt-3.5-turbo-instruct");
			data.put("prompt", input);
			data.put("max_tokens", 4000);
			connection.setDoOutput(true);
			// send data
			connection.getOutputStream().write(data.toString().getBytes());
			// get the response code
			int responseCode = connection.getResponseCode();
			if (responseCode != 200)
				return "Sorry, something went wrong!";
			// read the response
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
			String response = "";
			String line;
			while ((line = reader.readLine()) != null) {
				response += line;
			}
			reader.close();
			// parse the response
			JSONObject responseJson = new JSONObject(response);
			JSONArray choices = responseJson.getJSONArray("choices");
			String text = choices.getJSONObject(0).getString("text");
			return text.trim();
		} catch (Exception e) {
			return "IOException: " + e.getMessage();
		}
	}
	
}