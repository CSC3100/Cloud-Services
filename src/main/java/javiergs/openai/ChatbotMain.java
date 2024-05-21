package javiergs.openai;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for the Chatbot
 * It creates a JFrame and adds the ChatbotPanel to it
 *
 * @version 1.0
 * @author javiergs
 */
public class ChatbotMain extends JFrame {
	
	public ChatbotMain() {
		ChatbotPanel chatbotPanel = new ChatbotPanel();
		add(chatbotPanel);
	}
	
	public static void main(String[] args) {
		ChatbotMain test = new ChatbotMain();
		test.setTitle ("ChatBot Powered by ChatGPT");
		test.setLayout(new GridLayout(1, 1));
		test.setSize(400, 400);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setVisible(true);
	}

}
