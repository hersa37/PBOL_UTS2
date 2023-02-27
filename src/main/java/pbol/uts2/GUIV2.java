package pbol.uts2;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme;
import pbol.uts2.database.Employee;
import pbol.uts2.guiComponents.panels.AdminPanel;
import pbol.uts2.guiComponents.panels.ParentPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Main class untuk GUI database
 *
 * @author hersa, joanisa, shine
 */
public class GUIV2 {
	JFrame frame;
	JPanel parentPanel;

	public GUIV2() {
		init();
	}

	public static void main(String[] args) {
		try {       //Menentukan penampilan GUI menggunakan Flat Arc Dark Look and Feel
			javax.swing.UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUIV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		new GUIV2();
	}


	private void init() {
		frame = new JFrame("Inventory Management System");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(0, 1));
		frame.setSize(1000, 500);
		frame.setResizable(false);
		parentPanel = new ParentPanel();    //Parent panel berfungsi sebagai container untuk semua komponen GUI
		frame.add(parentPanel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
