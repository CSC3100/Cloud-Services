package javiergs.weather;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for Weather
 * It creates a JFrame and adds a JPanel with the current temperature from AccuWeather
 *
 * @author javiergs
 * @version 1.0
 */
public class WeatherMain extends JFrame {
	
	public WeatherMain() {
		WeatherPanel mapsPanel = new WeatherPanel();
		add(mapsPanel);
	}
	
	public static void main(String[] args) {
		WeatherMain mapsMain = new WeatherMain();
		mapsMain.setTitle("Application Powered by AccuWeather");
		mapsMain.setLayout(new GridLayout(1, 1));
		mapsMain.setSize(400, 400);
		mapsMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapsMain.setVisible(true);
	}
	
}