package pbol.uts2.guiComponents.panels;

import javax.swing.*;
import java.awt.*;

public class LandingPanel extends JPanel {

	JButton loginButton, credentialsButton;

	public LandingPanel(ParentPanel parent){
		super();
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		loginButton = new JButton("Masuk ke database");
		layout.putConstraint(SpringLayout.WEST, loginButton, 400, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, loginButton, 200, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, loginButton, -400, SpringLayout.EAST, this);
		loginButton.addActionListener(actionEvent -> parent.showPanel("loginPanel"));
		this.add(loginButton);

		credentialsButton = new JButton("Database Connection");
		layout.putConstraint(SpringLayout.NORTH, credentialsButton, 50, SpringLayout.NORTH, loginButton);
		layout.putConstraint(SpringLayout.WEST, credentialsButton, 400, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, credentialsButton, -400, SpringLayout.EAST, this);

		credentialsButton.addActionListener(actionEvent -> parent.showPanel("connectionPanel"));
		this.add(credentialsButton);

		JLabel title = new JLabel("Inventory Management System", SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 25));
		layout.putConstraint(SpringLayout.SOUTH, title, -30, SpringLayout.NORTH, loginButton);
		layout.putConstraint(SpringLayout.WEST, title, -200, SpringLayout.WEST, loginButton);
		layout.putConstraint(SpringLayout.EAST, title, 200, SpringLayout.EAST, loginButton);
		this.add(title);



	}
}
