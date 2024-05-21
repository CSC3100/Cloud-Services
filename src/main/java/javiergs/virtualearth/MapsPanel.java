package javiergs.virtualearth;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Properties;
import javax.swing.*;

/**
 * Panel for Bing Maps
 * It creates a JPanel with a map from Bing Maps
 *
 * @author javiergs
 * @version 1.0
 */
public class MapsPanel extends JPanel {
	
	public MapsPanel() {
		setLayout(new GridLayout(1, 1));
		try {
			ImageIcon imageIcon = getMap();
			imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			add(new JLabel(imageIcon));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ImageIcon getMap() throws Exception {
		String location = "40.714728,-73.998672";
		// get key from properties file
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		// create connection
		String apiKey = properties.getProperty("MAPS_API_KEY");
		String mapUrl = properties.getProperty("MAPS_SERVER") + "/ " +
			location + "?zoomLevel=10&mapSize=200,200&key=" + apiKey;
		URL mapImageUrl = new URL(mapUrl);
		URLConnection connection = mapImageUrl.openConnection();
		InputStream inputStream = connection.getInputStream();
		OutputStream outoutStream = new FileOutputStream("image.jpg");
		byte[] b = new byte[2048];
		int length;
		while ((length = inputStream.read(b)) != -1) {
			outoutStream.write(b, 0, length);
		}
		inputStream.close();
		outoutStream.close();
		return new ImageIcon("image.jpg");
	}
	
	public static void main(String[] args) {
		JFrame test = new JFrame("Bing Maps");
		test.setLayout(new GridLayout(1, 1));
		test.add(new MapsPanel());
		test.setSize(200, 200);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}