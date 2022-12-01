package pbol.uts2.guiComponents.buttons;

import pbol.uts2.guiComponents.panels.ParentPanel;

import javax.swing.*;
import java.awt.*;

public class LandingButton extends JButton {

	ParentPanel parentPanel;
	public LandingButton(ParentPanel parentPanel) {
		super("Kembali");
		this.parentPanel = parentPanel;
		addActionListener(actionEvent -> {
			parentPanel.resetEmployee();
			CardLayout cardLayout = parentPanel.getLayout();
			cardLayout.show(parentPanel, "landingPanel");
		});
	}
}
