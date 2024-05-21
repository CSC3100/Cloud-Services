package javiergs.virtualearth;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Panel for Bing Maps creates a JPanel with a map from Bing Maps
 *
 * @author javiergs
 * @version 1.0
 */
public class MapsPanel extends JPanel {
	
	public MapsPanel() {
		setLayout(new GridLayout(1, 1));
		try {
			ImageIcon imageIcon = getMap();
			imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
			add(new JLabel(imageIcon));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ImageIcon getMap() throws Exception {
		String location = "35.270378,-120.680656";
		// get key from properties file
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		// create connection
		String apiKey = properties.getProperty("MAPS_API_KEY");
		String mapUrl = properties.getProperty("MAPS_SERVER") + "/ " +
			location + "?zoomLevel=10&mapSize=400,400&key=" + apiKey;
		URL mapImageUrl = new URL(mapUrl);
		URLConnection connection = mapImageUrl.openConnection();
		InputStream inputStream = connection.getInputStream();
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		ImageIcon imageIcon = new ImageIcon(bufferedImage);
		return imageIcon;
	}
	
}