package pbol.uts2.guiComponents;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import pbol.uts2.guiComponents.panels.ParentPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author echa
 */
public class GUIV2 {
	JFrame frame;
	JPanel parentPanel;


	public GUIV2() {
		init();
	}

	private void init() {
		frame = new JFrame("D-sub");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(0,1));
		frame.setSize(1000, 500);
		frame.setResizable(false);
		parentPanel = new ParentPanel();
		frame.add(parentPanel);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUIV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		new GUIV2();
	}
}
