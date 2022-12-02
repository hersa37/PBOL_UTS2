package pbol.uts2.guiComponents.panels;

import pbol.uts2.database.Database;
import pbol.uts2.guiComponents.buttons.LandingButton;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Panel berisi operasi untuk menghubungkan aplikasi ke database. Terdapat 2 operasi utama yang bisa dilakukan:
 * - Test
 * - Simpan koneksi.
 * Test hanya mencoba koneksi ke database berdasarkan URL, ID, dan password yang diberikan tanpa menyimpan koneksinya
 * <p>
 * Simpan koneksi melakukan pengecekan apakah detail yang dimasukkan valid, lalu menyimpannya jika memang valid.
 */
public class ConnectionPanel extends JPanel {
	public ConnectionPanel(ParentPanel parentPanel) {
		super();
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		//URL label dan TextField
		JLabel url = new JLabel("URL");
		layout.putConstraint(SpringLayout.NORTH, url, 120, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, url, 250, SpringLayout.WEST, this);
		add(url);

		JTextField urlTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, urlTF, 0, SpringLayout.NORTH, url);
		layout.putConstraint(SpringLayout.WEST, urlTF, 100, SpringLayout.WEST, url);
		layout.putConstraint(SpringLayout.EAST, urlTF, -250, SpringLayout.EAST, this);
		add(urlTF);

		//ID label dan text field
		JLabel userID = new JLabel("User ID");
		layout.putConstraint(SpringLayout.NORTH, userID, 15, SpringLayout.SOUTH, urlTF);
		layout.putConstraint(SpringLayout.WEST, userID, 0, SpringLayout.WEST, url);
		add(userID);

		JTextField userIDTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, userIDTF, 0, SpringLayout.NORTH, userID);
		layout.putConstraint(SpringLayout.WEST, userIDTF, 0, SpringLayout.WEST, urlTF);
		layout.putConstraint(SpringLayout.EAST, userIDTF, 0, SpringLayout.EAST, urlTF);
		add(userIDTF);

		//Password label dan password field
		JLabel pass = new JLabel("Password");
		layout.putConstraint(SpringLayout.NORTH, pass, 15, SpringLayout.SOUTH, userIDTF);
		layout.putConstraint(SpringLayout.WEST, pass, 0, SpringLayout.WEST, url);
		add(pass);

		JPasswordField passwordField = new JPasswordField();
		layout.putConstraint(SpringLayout.NORTH, passwordField, 0, SpringLayout.NORTH, pass);
		layout.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, userIDTF);
		layout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, userIDTF);
		add(passwordField);

		//Label untuk menampilkan exception
		JLabel errorLabel = new JLabel("");
		layout.putConstraint(SpringLayout.NORTH, errorLabel, 10, SpringLayout.SOUTH, passwordField);
		layout.putConstraint(SpringLayout.WEST, errorLabel, 0, SpringLayout.WEST, passwordField);
		layout.putConstraint(SpringLayout.EAST, errorLabel, 0, SpringLayout.EAST, passwordField);
		add(errorLabel);

		LandingButton landingButton = new LandingButton(parentPanel);
		layout.putConstraint(SpringLayout.SOUTH, landingButton, -100, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, landingButton, -170, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, landingButton, -100, SpringLayout.EAST, landingButton);
		landingButton.addActionListener(actionEvent -> errorLabel.setText(""));
		add(landingButton);

		JButton save = new JButton("Save");
		layout.putConstraint(SpringLayout.SOUTH, save, -10, SpringLayout.NORTH, landingButton);
		layout.putConstraint(SpringLayout.WEST, save, 0, SpringLayout.WEST, landingButton);
		layout.putConstraint(SpringLayout.EAST, save, 0, SpringLayout.EAST, landingButton);
		save.addActionListener(actionEvent -> {
			Thread thread = new Thread(() -> errorLabel.setText("Connecting..."));
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				errorLabel.setText(e.getMessage());
			}

			Thread thread1 = new Thread(() -> {
				Database database = new Database();
				try {
					database.testConnection(urlTF.getText(), userIDTF.getText(), String.valueOf(passwordField.getPassword()));
					database.saveCredentials(urlTF.getText(), userIDTF.getText(), String.valueOf(passwordField.getPassword()));
					errorLabel.setText("Berhasil menyimpan koneksi");
				} catch (SQLException | IOException e) {
					errorLabel.setText(e.getMessage());
				}
			});
			thread1.start();
		});
		add(save);

		JButton test = new JButton("Test");
		layout.putConstraint(SpringLayout.SOUTH, test, -10, SpringLayout.NORTH, save);
		layout.putConstraint(SpringLayout.WEST, test, 0, SpringLayout.WEST, save);
		layout.putConstraint(SpringLayout.EAST, test, 0, SpringLayout.EAST, save);
		test.addActionListener(actionEvent -> {
			Thread thread = new Thread(() -> errorLabel.setText("Connecting..."));
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				errorLabel.setText(e.getMessage());
			}

			Thread thread1 = new Thread(() -> {
				Database database = new Database();
				try {
					database.testConnection(urlTF.getText(), userIDTF.getText(), String.valueOf(passwordField.getPassword()));
					errorLabel.setText("Berhasil terhubung");
				} catch (SQLException e) {
					errorLabel.setText(e.getMessage());
				}
			});
			thread1.start();
		});
		add(test);
	}
}
