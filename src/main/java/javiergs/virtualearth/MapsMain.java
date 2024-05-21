package javiergs.virtualearth;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for Bing Maps
 * It creates a JFrame and adds a JPanel with a map from Bing Maps
 *
 * @version 1.0
 * @author javiergs
 */
public class MapsMain extends JFrame {
	
	public MapsMain() {
		MapsPanel mapsPanel = new MapsPanel();
		add(mapsPanel);
	}
	
	public static void main(String[] args) {
		MapsMain mapsMain = new MapsMain();
		mapsMain.setTitle ("ChatBot Powered by ChatGPT");
		mapsMain.setLayout(new GridLayout(1, 1));
		mapsMain.setSize(400, 400);
		mapsMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapsMain.setVisible(true);
	}
	
}