package javiergs;

import javiergs.openai.ChatbotPanel;
import javiergs.virtualearth.MapsPanel;
import javiergs.weather.WeatherPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for the Cloud Services Example
 * It creates a JFrame and adds a JPanel with the Chatbot, Maps, and Weather
 *
 * @author javiergs
 * @version 1.0
 */
public class Main extends JFrame {
	
	public Main() {
		setLayout(new BorderLayout());
		ChatbotPanel chatbotPanel = new ChatbotPanel();
		MapsPanel mapsPanel = new MapsPanel();
		WeatherPanel weatherPanel = new WeatherPanel();
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(weatherPanel);
		panel.add(mapsPanel);
		add(panel, BorderLayout.CENTER);
		add(chatbotPanel, BorderLayout.WEST);
		weatherPanel.setBorder(BorderFactory.createTitledBorder("Weather:"));
		mapsPanel.setBorder(BorderFactory.createTitledBorder("Maps:"));
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setTitle("Cloud Services Example");
		main.setSize(800, 600);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
	
}