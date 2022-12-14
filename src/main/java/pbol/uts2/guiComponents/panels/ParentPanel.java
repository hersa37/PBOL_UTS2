package pbol.uts2.guiComponents.panels;

import pbol.uts2.database.Employee;

import javax.swing.*;
import java.awt.*;

public class ParentPanel extends JPanel {

	private Employee employee;  //Employee yang digunakan untuk sesi disimpan di parent panel
	private UserPanel userPanel;
	private AdminPanel adminPanel;
	private CardLayout layout;

	/**
	 * Constructor panel. Menggunakan CardLayout untuk menentukan panel apa yang akan ditunjukkan.
	 */
	public ParentPanel() {
		employee = new Employee("Ma000007", "", "123");
		layout = new CardLayout();
		setLayout(layout);

		LandingPanel landingPanel = new LandingPanel(this);
		add(landingPanel, "landingPanel");       //Menambahkan panel ke layout dan beri nama yang bisa dipanggil.

		LoginPanel loginPanel = new LoginPanel(this);
		add(loginPanel, "loginPanel");

		ConnectionPanel connectionPanel = new ConnectionPanel(this);
		add(connectionPanel, "connectionPanel");

		userPanel = new UserPanel(this);
		add(userPanel, "userPanel");

		adminPanel = new AdminPanel(this);
		add(adminPanel, "adminPanel");
	}

	/**
	 * Ambil layout dari parent panel
	 *
	 * @return layout parent panel
	 */
	public CardLayout getLayout() {
		return (CardLayout) super.getLayout();
	}

	/**
	 * Menunjukkan panel yang sudah ditambahkan ke CardLayout
	 *
	 * @param panel : nama panel yang ditampilkan
	 */
	public void showPanel(String panel) {
		((CardLayout) super.getLayout()).show(this, panel);
	}

	/**
	 * Ambil employee sesi ini
	 *
	 * @return employee dari sesi ini
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * Mengatur employee sesi ini
	 *
	 * @param employee dari sesi ini
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * Method untuk menghapus employee dari sesi ini
	 */
	public void resetEmployee() {
		employee = null;
	}

	/**
	 * Method untuk menghapus panel user dan admin saat ini dan membuat yang baru
	 */
	public void resetPanels() {
		this.remove(adminPanel);
		this.remove(userPanel);
		adminPanel = new AdminPanel(this);
		userPanel = new UserPanel(this);
		this.add(adminPanel, "adminPanel");
		this.add(userPanel, "userPanel");
	}
}
