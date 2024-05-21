package javiergs.weather;

import javax.swing.*;
import java.awt.*;

/**
 * WeatherPanel is a JPanel that contains a label with the temperature in Celsius.
 *
 * @author javiergs
 * @version 1.0
 */
public class WeatherPanel extends JPanel {
	
	public WeatherPanel() {
		setLayout(new GridLayout(1, 1));
		JLabel label = new JLabel(". . . ");
		add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 24));
		// get the temperature
		WeatherDelegate weatherDelegate = new WeatherDelegate();
		try {
			double value = weatherDelegate.getTemperature();
			label.setText(" " + value + " °C");
			if (value > 30) {
				label.setForeground(Color.RED);
			} else if (value < 10) {
				label.setForeground(new Color(90, 140, 255)); // blue
			} else {
				label.setForeground(Color.BLACK);
			}
		} catch (Exception e) {
			label.setText("Error: " + e.getMessage());
		}
	}
	
}