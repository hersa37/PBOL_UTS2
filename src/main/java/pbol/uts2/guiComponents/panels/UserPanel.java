package pbol.uts2.guiComponents.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Panel yang menyimpan semua operasi yang dapat dilakukan user. Panel menyimpan {@link JTabbedPane} yang menyimpan panel
 * Return dan Checkout.
 * <p>
 * Return dan Chekout dibuat berdasarkan {@link TabledPanel} di mana pembedanya ditentukan berdasarkan jumlah kolomnya.
 *
 * @see AdminPanel
 * @see TabledPanel
 */
public class UserPanel extends JPanel {

	public UserPanel(ParentPanel parentPanel) {
		super();
		setLayout(new GridLayout(0, 1));
		JTabbedPane userTab = new JTabbedPane();
		add(userTab);

		String[] returnColumn = {"SKU", "Nama", "Tanggal Keluar", "Tanggal kembali", "Satuan"};
		TabledPanel returnTab = new TabledPanel(returnColumn, 3, parentPanel);
		userTab.addTab("Return", returnTab); //(<nama tab>, <komponen>)

		String[] checkoutColumn = {"SKU", "Nama", "Satuan"};
		TabledPanel checkoutTab = new TabledPanel(checkoutColumn, 0, parentPanel);
		userTab.addTab("Checkout", checkoutTab);

	}
}
