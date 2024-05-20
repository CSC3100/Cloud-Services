package javiergs.virtualearth;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Main extends JPanel {
	
	/**
	 * Constructor
	 */
	public Main() {
		setLayout(new GridLayout(1,1));
		try {
			ImageIcon imageIcon = getMap();
			imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			add(new JLabel (imageIcon));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the map from Bing Maps
	 * @return The map
	 */
	private ImageIcon getMap() throws Exception {
		String location = "40.714728,-73.998672";
		String apiKey = "AiSeFXl4iTK4QhhhkPTfUOulN5_rFaTBC62yquebCDL9anZNIP333urvmwXnuFz6";
		String mapUrl = "https://dev.virtualearth.net/REST/v1/Imagery/Map/Road/"
			+ location + "?zoomLevel=10&mapSize=200,200&key="
			+ apiKey;
		
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
	
	/**
	 * javiergs.virtualearth.Main method just for testing
	 * @param args Command line arguments
	 */
	public static void main(String[] args)  {
		JFrame test = new JFrame("Bing Maps");
		test.setLayout(new GridLayout(1,1));
		test.add(new Main());
		test.setSize(200,200);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}




