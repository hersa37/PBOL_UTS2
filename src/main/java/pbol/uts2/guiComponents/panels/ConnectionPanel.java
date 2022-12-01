package pbol.uts2.guiComponents.panels;

import pbol.uts2.guiComponents.buttons.LandingButton;

import javax.swing.*;

public class ConnectionPanel extends JPanel {
	public ConnectionPanel(ParentPanel parentPanel) {
		super();
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		JLabel url = new JLabel("URL");
		layout.putConstraint(SpringLayout.NORTH, url, 120, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, url, 250, SpringLayout.WEST, this);
		add(url);

		JTextField urlTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, urlTF, 0, SpringLayout.NORTH, url);
		layout.putConstraint(SpringLayout.WEST, urlTF, 100, SpringLayout.WEST, url);
		layout.putConstraint(SpringLayout.EAST, urlTF, -250, SpringLayout.EAST, this);
		add(urlTF);

		JLabel userID = new JLabel("User ID");
		layout.putConstraint(SpringLayout.NORTH, userID, 15, SpringLayout.SOUTH, urlTF);
		layout.putConstraint(SpringLayout.WEST, userID, 0, SpringLayout.WEST, url);
		add(userID);

		JTextField userIDTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, userIDTF, 0, SpringLayout.NORTH, userID);
		layout.putConstraint(SpringLayout.WEST, userIDTF, 0, SpringLayout.WEST, urlTF);
		layout.putConstraint(SpringLayout.EAST, userIDTF, 0, SpringLayout.EAST, urlTF);
		add(userIDTF);

		JLabel pass = new JLabel("Password");
		layout.putConstraint(SpringLayout.NORTH, pass, 15, SpringLayout.SOUTH, userIDTF);
		layout.putConstraint(SpringLayout.WEST, pass, 0, SpringLayout.WEST, url);
		add(pass);

		LandingButton landingButton = new LandingButton(parentPanel);
		add(landingButton);
	}
}
