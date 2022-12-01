package pbol.uts2.guiComponents.panels;

import pbol.uts2.guiComponents.tables.InventoryTable;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel{

	InventoryTable table;
	public UserPanel(ParentPanel parentPanel) {
		super();
		setLayout(new GridLayout(0,1));
		JTabbedPane userTab = new JTabbedPane();
		add(userTab);

		String[] returnColumn = {"SKU", "Nama", "Tanggal Keluar", "Tanggal kembali", "Satuan"};
		TabledPanel returnTab = new TabledPanel(returnColumn, 3, parentPanel);
		userTab.addTab("Return", returnTab);

		String[] checkoutColumn = {"SKU", "Nama", "Satuan"};
		TabledPanel checkoutTab = new TabledPanel(checkoutColumn, 0, parentPanel);
		userTab.addTab("Checkout", checkoutTab);

	}
}
