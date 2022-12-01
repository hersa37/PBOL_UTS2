package pbol.uts2.guiComponents.panels;

import pbol.uts2.database.Database;
import pbol.uts2.database.Employee;
import pbol.uts2.database.Inventory;
import pbol.uts2.guiComponents.buttons.LandingButton;
import pbol.uts2.guiComponents.tables.InventoryTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.time.LocalDate;

public class TabledPanel extends JTable {

	private InventoryTable table;
	private JLabel errorLabel;

	public TabledPanel(String[] columnNames, int dateCol, ParentPanel parentPanel) {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		setBackground(new Color(56, 60, 74));
		errorLabel = new JLabel("");

		Object[][] data = {{}};
		table = new InventoryTable(columnNames, data, dateCol);
		JScrollPane scrollPane = new JScrollPane(table);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 250, SpringLayout.NORTH, scrollPane);
		add(scrollPane);

		JButton refreshButton = new JButton("Refresh");
		layout.putConstraint(SpringLayout.NORTH, refreshButton, 10, SpringLayout.SOUTH, scrollPane);
		layout.putConstraint(SpringLayout.WEST, refreshButton, 0, SpringLayout.WEST, scrollPane);
		layout.putConstraint(SpringLayout.EAST, refreshButton, 120, SpringLayout.WEST, refreshButton);
		refreshButton.addActionListener(actionEvent -> {
			Thread thread = new Thread(() -> errorLabel.setText("Loading...."));
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				errorLabel.setText(e.getMessage());
			}

			Thread thread1 = new Thread(() -> {
				Database database = new Database();
				try {
					switch (columnNames.length) {
						case 3 -> table.setData(database.getFreeInventory());
						case 5 -> table.setData(database.getEmployeeInventory(parentPanel.getEmployee()));
						case 8 -> table.setData(database.getInventories());
					}
					errorLabel.setText("");
				} catch (Exception e) {
					errorLabel.setText(e.getMessage());
				}
			});
			thread1.start();

		});
		add(refreshButton);

		JButton button = new JButton();
		switch (columnNames.length) {
			case 3 -> {
				button.setText("Checkout");
				button.addActionListener(actionEvent -> checkoutButton(parentPanel));
			}
			case 5 -> {
				button.setText("Return");
				button.addActionListener(actionEvent -> returnButton(parentPanel));
			}
			case 8 -> {
				button.setText("Remove");
				button.addActionListener(actionEvent -> removeButton());
			}
		}

		//ErrorLabel
		layout.putConstraint(SpringLayout.NORTH, errorLabel, 10, SpringLayout.SOUTH, scrollPane);
		layout.putConstraint(SpringLayout.WEST, errorLabel, 200, SpringLayout.EAST, refreshButton);
		add(errorLabel);

		JTextField skuTF = new JTextField();
		JButton findSKU = new JButton("Find SKU");
		layout.putConstraint(SpringLayout.NORTH, findSKU, 15, SpringLayout.SOUTH, refreshButton);
		layout.putConstraint(SpringLayout.WEST, findSKU, 0, SpringLayout.WEST, refreshButton);
		layout.putConstraint(SpringLayout.EAST, findSKU, 0, SpringLayout.EAST, refreshButton);
		findSKU.addActionListener(actionEvent -> findSKU(skuTF));
		add(findSKU);

		layout.putConstraint(SpringLayout.NORTH, skuTF, 0, SpringLayout.NORTH, findSKU);
		layout.putConstraint(SpringLayout.WEST, skuTF, 10, SpringLayout.EAST, findSKU);
		layout.putConstraint(SpringLayout.EAST, skuTF, 80, SpringLayout.WEST, skuTF);
		skuTF.addActionListener(actionEvent -> findSKU(skuTF));
		add(skuTF);

		LandingButton landingButton = new LandingButton(parentPanel);
		layout.putConstraint(SpringLayout.EAST, landingButton, 0, SpringLayout.EAST, scrollPane);
		layout.putConstraint(SpringLayout.SOUTH, landingButton, -50, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, landingButton, -120, SpringLayout.EAST, landingButton);
		landingButton.addActionListener(actionEvent -> table.resetTable());
		add(landingButton);

		JButton printButton = new JButton("Print");
		layout.putConstraint(SpringLayout.EAST, printButton, 0, SpringLayout.EAST, landingButton);
		layout.putConstraint(SpringLayout.WEST, printButton, 0, SpringLayout.WEST, landingButton);
		layout.putConstraint(SpringLayout.SOUTH, printButton, -10, SpringLayout.NORTH, landingButton);
		printButton.addActionListener(actionEvent -> toFile(parentPanel.getEmployee()));
		add(printButton);

		//Button
		layout.putConstraint(SpringLayout.SOUTH, button, -10, SpringLayout.NORTH, printButton);
		layout.putConstraint(SpringLayout.EAST, button, 0, SpringLayout.EAST, landingButton);
		layout.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, landingButton);
		add(button);
	}

	private void checkoutButton(ParentPanel parentPanel) {
		Database database = new Database();
		int sku = table.getSKUValue();
		try {
			Inventory inventory = new Inventory(sku);
			Date currentDate = new Date(System.currentTimeMillis());
			LocalDate returnDate = currentDate.toLocalDate().plusDays(14);
			inventory.setTanggal_keluar(currentDate);
			inventory.setTanggal_kembali(Date.valueOf(returnDate));
			inventory.setPeminjam(parentPanel.getEmployee().getId());
			table.setData(database.checkoutInventory(inventory));
			errorLabel.setText("Berhasil checkout.");
		} catch (Exception e) {
			errorLabel.setText(e.getMessage());
		}
	}

	private void returnButton(ParentPanel parentPanel) {
		Database database = new Database();
		int sku = table.getSKUValue();
		try {
			Inventory inventory = new Inventory(sku);
			inventory.setPeminjam(parentPanel.getEmployee().getId());
			table.setData(database.returnInventory(inventory));
			errorLabel.setText("Berhasil mengembalikan");
		} catch (Exception e) {
			errorLabel.setText(e.getMessage());
		}
	}

	private void removeButton() {
		Database database = new Database();
		int sku = table.getSKUValue();
		try {
			Inventory inventory = new Inventory(sku);
			table.setData(database.removeInventory(inventory));
		} catch (Exception e) {
			errorLabel.setText(e.getMessage());
		}
	}

	private void findSKU(JTextField tf) {
		TableRowSorter<TableModel> filter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(filter);
		String sku = tf.getText();
		if (sku.trim().length() == 0) {
			filter.setRowFilter(null);
		} else {
			filter.setRowFilter(RowFilter.regexFilter("(?i)" + sku));
		}
	}

	private void toFile(Employee employee) {
		try {
			String path = "printout_" + employee.getId() + "_"+ LocalDate.now();
			Writer output = new FileWriter(path);
			StringBuilder string = new StringBuilder("|");
			for (int i = 0; i < table.getColumnCount(); i++) {
				string.append(String.format(" %15s |", table.getColumnName(i)));
			}
			for (int i = 0; i < table.getRowCount(); i++) {
				string.append("\n|");
				for (int j = 0; j < table.getColumnCount(); j++) {
					string.append(String.format(" %15s |", table.getValueAt(i, j)));
				}
			}
			output.write(string.toString());
			output.close();
		} catch (IOException e) {
			errorLabel.setText(e.getMessage());
		}
	}


}
