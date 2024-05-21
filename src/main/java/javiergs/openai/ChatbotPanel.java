package javiergs.openai;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * ChatbotPanel class
 * It is a JPanel that contains a text area for the chatbot output, a text field for the user input,
 * and a button to send the input.
 *
 * @version 1.0
 * @author javiergs
 */
public class ChatbotPanel extends JPanel implements PropertyChangeListener, ActionListener {
	
	private JTextPane outputArea;
	private JTextField inputField;
	private JButton sendButton;
	
	public ChatbotPanel() {
		// output text area
		outputArea = new JTextPane();
		outputArea.setEditable(false);
		outputArea.setContentType("text/html");
		outputArea.setFocusable(false);
		JScrollPane scrollPane = new JScrollPane(outputArea);
		scrollPane.setBorder(BorderFactory.createTitledBorder("ChatGPT:"));
		// user input field
		inputField = new JTextField();
		inputField.addActionListener(this);
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		// assembly of components
		setLayout(new BorderLayout());
		JPanel south = new JPanel(new BorderLayout());
		south.add(inputField, BorderLayout.CENTER);
		south.add(sendButton, BorderLayout.EAST);
		add(scrollPane, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		inputField.requestFocus();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String input = inputField.getText();
		// disable sending
		sendButton.setText("Sending...");
		sendButton.setEnabled(false);
		// create a new ChatbotBrain object
		ChatbotBrain chatbotBrain = new ChatbotBrain(input);
		chatbotBrain.addPropertyChangeListener(this);
		Thread thread = new Thread(chatbotBrain);
		thread.start();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals("output"))
			return;
		String output = (String) evt.getNewValue();
		// format the output
		StyledDocument doc = outputArea.getStyledDocument();
		Style styleOrange = outputArea.addStyle("Orange", null);
		StyleConstants.setForeground(styleOrange, new Color(50, 100, 0, 255));
		Style styleGray = outputArea.addStyle("Gray", null);
		StyleConstants.setForeground(styleGray, Color.GRAY);
		try {
			doc.insertString(doc.getLength(), "You -> " + inputField.getText() + "\n", styleOrange);
			doc.insertString(doc.getLength(), "ChatGPT -> " + output + "\n", styleGray);
			
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		// enable sending
		sendButton.setText("Send");
		sendButton.setEnabled(true);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
	
}