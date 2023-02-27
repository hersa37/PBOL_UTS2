package pbol.uts2.guiComponents.buttons;

import pbol.uts2.guiComponents.panels.ParentPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Template tombol untuk kembali ke halaman awal (landing page)
 */
public class LandingButton extends JButton {

	ParentPanel parentPanel;

	/**
	 * Constructor with parent panel to go to
	 * @param parentPanel Panel yang mengandung semua komponen GUI
	 */
	public LandingButton(ParentPanel parentPanel) {
		super("Kembali");
		this.parentPanel = parentPanel;
		addActionListener(actionEvent -> {
			parentPanel.resetEmployee();        //Memastikan employee null agar tidak digunakan di sesi berikutnya
			CardLayout cardLayout = parentPanel.getLayout();
			cardLayout.show(parentPanel, "landingPanel");   //Panggil layout parent panel dan tunjukkan landing panel
			parentPanel.resetPanels();
		});
	}
}
