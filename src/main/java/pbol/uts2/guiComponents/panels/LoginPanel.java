package pbol.uts2.guiComponents.panels;

import pbol.uts2.database.Database;
import pbol.uts2.database.Employee;
import pbol.uts2.guiComponents.buttons.LandingButton;
import pbol.uts2.database.PasswordInvalidException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
	private JLabel errorLabel;
	public LoginPanel(ParentPanel parentPanel) {
		super();
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		JLabel employeeId = new JLabel("Employee ID");
		layout.putConstraint(SpringLayout.NORTH, employeeId, 150, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, employeeId, 300, SpringLayout.WEST, this);
		add(employeeId);

		JTextField idTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, idTF, 150, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, idTF, 50, SpringLayout.EAST, employeeId);
		layout.putConstraint(SpringLayout.EAST, idTF, -300, SpringLayout.EAST, this);
		add(idTF);

		JLabel employeePass = new JLabel("Password");
		layout.putConstraint(SpringLayout.NORTH, employeePass, 40, SpringLayout.SOUTH, employeeId);
		layout.putConstraint(SpringLayout.WEST, employeePass, 0, SpringLayout.WEST, employeeId);
		add(employeePass);

		JPasswordField passwordField = new JPasswordField();
		layout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, idTF);
		layout.putConstraint(SpringLayout.NORTH, passwordField, 40, SpringLayout.SOUTH, employeeId);
		layout.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, idTF);
		passwordField.addActionListener(actionEvent -> login(parentPanel, idTF, passwordField));
		add(passwordField);

		errorLabel = new JLabel("");
		layout.putConstraint(SpringLayout.WEST, errorLabel, 0, SpringLayout.WEST, passwordField);
		layout.putConstraint(SpringLayout.EAST, errorLabel, 0, SpringLayout.EAST, passwordField);
		layout.putConstraint(SpringLayout.NORTH, errorLabel, 20, SpringLayout.SOUTH, passwordField);
		add(errorLabel);

		JButton loginButton = new JButton("Login");
		layout.putConstraint(SpringLayout.EAST, loginButton, -150, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, loginButton, -120, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, loginButton, 30, SpringLayout.EAST, idTF);
		loginButton.addActionListener(actionEvent -> login(parentPanel,idTF, passwordField));
		add(loginButton);

		LandingButton landingButton = new LandingButton(parentPanel);
		layout.putConstraint(SpringLayout.EAST, landingButton, 0, SpringLayout.EAST, loginButton);
		layout.putConstraint(SpringLayout.NORTH, landingButton, 15, SpringLayout.SOUTH, loginButton);
		layout.putConstraint(SpringLayout.WEST, landingButton, 0, SpringLayout.WEST, loginButton);
		add(landingButton);
	}

	private void login(ParentPanel parentPanel, JTextField id, JPasswordField pass){
		Thread thread = new Thread(() -> errorLabel.setText("Loading..."));
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			errorLabel.setText(e.getMessage());
		}

		Thread thread1 = new Thread(() -> {
			try {
				Database database = new Database();
				parentPanel.setEmployee(database.validateEmployee(new Employee(id.getText(), "", String.valueOf(pass.getPassword()))));
				errorLabel.setText("");
				id.setText("");
				pass.setText("");
				if(parentPanel.getEmployee().getId().startsWith("Ma")) {
					parentPanel.showPanel("adminPanel");
				} else {
					parentPanel.showPanel("userPanel");
				}
			} catch (SQLException | FileNotFoundException | PasswordInvalidException | IllegalArgumentException e){
				errorLabel.setText(e.getMessage());
			} catch (NullPointerException e) {
				errorLabel.setText("User tidak ditemukan");
			}
		});
		thread1.start();
	}
}
