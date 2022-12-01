package pbol.uts2.guiComponents.panels;

import pbol.uts2.database.Employee;

import javax.swing.*;
import java.awt.*;

public class ParentPanel extends JPanel {

	private Employee employee;
	public ParentPanel() {
		employee = new Employee("Ma000007","","123");
		CardLayout  layout = new CardLayout();
		setLayout(layout);

		LandingPanel landingPanel = new LandingPanel(this);
		add(landingPanel,"landingPanel");

		LoginPanel loginPanel = new LoginPanel(this);
		add(loginPanel, "loginPanel");

		ConnectionPanel connectionPanel = new ConnectionPanel(this);
		add(connectionPanel, "connectionPanel");

		UserPanel userPanel = new UserPanel(this);
		add(userPanel, "userPanel");

		AdminPanel adminPanel = new AdminPanel(this);
		add(adminPanel, "adminPanel");
	}


	public CardLayout getLayout() {
		return (CardLayout) super.getLayout();
	}

	public void showPanel(String panel) {
		((CardLayout)super.getLayout()).show(this, panel);
	}

	public Employee getEmployee(){
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void resetEmployee() {
		employee = null;
	}
}
