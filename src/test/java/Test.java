import database.Database;
import database.Employee;
import database.Inventory;
import database.PasswordInvalidException;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;

public class Test {

	public Test() {
		JFrame frame = new JFrame();
		frame.setSize(200,300);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		String[] columnNames = {"SKU", "Nama", "Satuan", "Tanggal Keluar", "Tanggal Kembali"};
		Object[][] data = {{"11111", "55555",124556,154353,1235}};

		JTable table = new JTable(new DatabaseTableModel(columnNames, data));

		frame.add(table);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
//		Employee employee = new Employee("Ma000007","Galih","123");
//		Database database = new Database();
//		try {
//			database.addEmployee(employee);
//		} catch (SQLException | FileNotFoundException e) {
//			System.out.println(e.getMessage());
//		}
////		new Test();
////		Date date = new Date(System.currentTimeMillis());
////		System.out.println(date);
////		System.out.println(System.currentTimeMillis());
//		String name = "echa";
//
//		System.out.println(name.substring(0,2));
		Inventory inventory = new Inventory(101,"Ex", 10001, null, null, null, null, null);

	}
}
