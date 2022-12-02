package pbol.uts2.guiComponents.panels;

import pbol.uts2.database.Database;
import pbol.uts2.database.Inventory;
import pbol.uts2.guiComponents.buttons.LandingButton;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;

public class AdminPanel extends JPanel {

	public AdminPanel(ParentPanel parentPanel) {
		super();
		setLayout(new GridLayout(0,1));
		JTabbedPane adminTab = new JTabbedPane();
		add(adminTab);

		String[] returnColumn = {"SKU", "Nama", "Tanggal Keluar", "Tanggal kembali", "Satuan"};
		TabledPanel returnTab = new TabledPanel(returnColumn, 3, parentPanel);
		adminTab.addTab("Return", returnTab);

		String[] checkoutColumn = {"SKU", "Nama", "Satuan"};
		TabledPanel checkoutTab = new TabledPanel(checkoutColumn, 0, parentPanel);
		adminTab.addTab("Checkout", checkoutTab);

		JPanel addInv = addPanel(parentPanel);
		adminTab.addTab("Add", addInv);

		String[] removeColumn = {"SKU","Nama", "Harga", "Tanggal_Masuk", "Tanggal_Keluar", "Tanggal_Kembali", "Satuan", "Peminjam"};
		TabledPanel removeTab = new TabledPanel(removeColumn, 5, parentPanel);
		adminTab.add("Remove", removeTab);
	}

	private JPanel addPanel(ParentPanel parentPanel){
		JPanel addInv = new JPanel();
		SpringLayout layout = new SpringLayout();
		addInv.setLayout(layout);

		JLabel sku = new JLabel("SKU");
		layout.putConstraint(SpringLayout.WEST, sku, 300, SpringLayout.WEST, addInv);
		layout.putConstraint(SpringLayout.NORTH, sku, 100, SpringLayout.NORTH, addInv);
		addInv.add(sku);

		JTextField skuTF = new JTextField();
		layout.putConstraint(SpringLayout.WEST, skuTF, 100, SpringLayout.WEST, sku);
		layout.putConstraint(SpringLayout.NORTH, skuTF, 0, SpringLayout.NORTH, sku);
		layout.putConstraint(SpringLayout.EAST, skuTF, -300, SpringLayout.EAST, addInv);
		addInv.add(skuTF);

		JLabel nama = new JLabel("Nama");
		layout.putConstraint(SpringLayout.NORTH, nama, 10, SpringLayout.SOUTH, skuTF);
		layout.putConstraint(SpringLayout.WEST, nama, 0, SpringLayout.WEST, sku);
		addInv.add(nama);

		JTextField namaTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, namaTF, 0, SpringLayout.NORTH, nama);
		layout.putConstraint(SpringLayout.WEST, namaTF, 0, SpringLayout.WEST, skuTF);
		layout.putConstraint(SpringLayout.EAST, namaTF, 0, SpringLayout.EAST, skuTF);
		addInv.add(namaTF);

		JLabel harga = new JLabel("Harga");
		layout.putConstraint(SpringLayout.NORTH, harga, 10, SpringLayout.SOUTH, namaTF);
		layout.putConstraint(SpringLayout.WEST, harga, 0, SpringLayout.WEST, sku);
		addInv.add(harga);

		JTextField hargaTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, hargaTF, 0, SpringLayout.NORTH, harga);
		layout.putConstraint(SpringLayout.WEST, hargaTF, 0, SpringLayout.WEST, namaTF);
		layout.putConstraint(SpringLayout.EAST, hargaTF, 0, SpringLayout.EAST, skuTF);
		addInv.add(hargaTF);

		JLabel satuan = new JLabel("Satuan");
		layout.putConstraint(SpringLayout.NORTH, satuan, 10, SpringLayout.SOUTH, hargaTF);
		layout.putConstraint(SpringLayout.WEST, satuan, 0, SpringLayout.WEST, sku);
		addInv.add(satuan);

		JTextField satuanTF = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, satuanTF, 0, SpringLayout.NORTH, satuan);
		layout.putConstraint(SpringLayout.WEST, satuanTF, 0, SpringLayout.WEST, hargaTF);
		layout.putConstraint(SpringLayout.EAST, satuanTF, 0, SpringLayout.EAST, skuTF);
		addInv.add(satuanTF);

		LandingButton landingButton = new LandingButton(parentPanel);
		layout.putConstraint(SpringLayout.SOUTH, landingButton, -50, SpringLayout.SOUTH, addInv);
		layout.putConstraint(SpringLayout.EAST, landingButton, -175, SpringLayout.EAST, addInv);
		layout.putConstraint(SpringLayout.WEST, landingButton, -100, SpringLayout.EAST, landingButton);
		addInv.add(landingButton);

		JLabel errorLabel = new JLabel("");
		layout.putConstraint(SpringLayout.NORTH, errorLabel, 10, SpringLayout.SOUTH, satuanTF);
		layout.putConstraint(SpringLayout.WEST, errorLabel, 0, SpringLayout.WEST, satuanTF);
		layout.putConstraint(SpringLayout.EAST, errorLabel, 0, SpringLayout.EAST, satuanTF);
		addInv.add(errorLabel);

		JButton addButton = new JButton("Add");
		layout.putConstraint(SpringLayout.SOUTH, addButton, -10, SpringLayout.NORTH, landingButton);
		layout.putConstraint(SpringLayout.EAST, addButton, 0, SpringLayout.EAST, landingButton);
		layout.putConstraint(SpringLayout.WEST, addButton, 0, SpringLayout.WEST, landingButton);
		addButton.addActionListener(actionEvent -> {
			Inventory inventory = new Inventory(
					Integer.parseInt(skuTF.getText()),
					namaTF.getText(),
					Integer.parseInt(hargaTF.getText()),
					new Date(System.currentTimeMillis()),
					null,
					null,
					satuanTF.getText(),
					null);
			Database database = new Database();
			try {
				database.addInventory(inventory);
				errorLabel.setText("Berhasil memasukkan barang.");
				skuTF.setText("");
				namaTF.setText("");
				hargaTF.setText("");
				satuanTF.setText("");
			} catch (SQLException | FileNotFoundException e) {
				errorLabel.setText(e.getMessage());
			}
		});
		addInv.add(addButton);

		return addInv;
	}


}
