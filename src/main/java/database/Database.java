package database;

import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Database {
	public static void main(String[] args) throws SQLException, FileNotFoundException, PasswordInvalidException {
//		Employee echa = new Employee("He000006", "Echa", "passwordku");
		Database data = new Database();
//		data.addEmployee(echa);
		Employee echa = new Employee("He000006","","passwordku");
		System.out.println(data.validateEmployee(echa));
	}


	Connection getConnection() throws FileNotFoundException {

        /*
        URL, user, dan password disimpan dalam file bernama credentials
        Pakai Scanner untuk baca file, tentukan new line sebagai pemisah antar kata
         */
		Scanner credentials = new Scanner(new File("credentials"));
		credentials.useDelimiter(Pattern.compile("\n"));
		String[] creds = new String[3];
		int i = 0;
        /*
        Simpan tiap baris file dalam array String
        index 0 = url, 1 = user, 2 = password

         String usr = "College_troublemad";
        String pwd = "3c6cc66453d1ce9f4dd76803bc7b1eed459641f4";*/
		while (credentials.hasNext()) {
			creds[i] = credentials.next();
			i++;
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(creds[0], creds[1], creds[2]);
		} catch (SQLException ex) {
			System.out.println("Koneksi tidak berhasil.");
			System.out.println(ex.getMessage());
		}
		/*
		conn = null dihandle method yang panggil
		 */

		return conn;
	}

	public LinkedList<Inventory> getInventories() throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list = null;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("SELECT * FROM Inventory ");
			resultSet = statement.executeQuery();
			list = createInventoryList(resultSet);
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return list;
	}

	public LinkedList<Inventory> getEmployeeInventory(Inventory employeeInventory) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("SELECT * FROM Inventory WHERE Peminjam = ?");
			statement.setString(1, employeeInventory.getPeminjam());
			resultSet = statement.executeQuery();
			list = createInventoryList(resultSet);
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return list;
	}

	public LinkedList<Inventory> getFreeInventory() throws SQLException, NullPointerException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("SELECT * FROM Inventory WHERE Peminjam IS null");
			resultSet = statement.executeQuery();
			list = createInventoryList(resultSet);
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return list;
	}



	private LinkedList<Inventory> createInventoryList(ResultSet resultSet) throws SQLException {
		LinkedList<Inventory> list = new LinkedList<>();
		while (resultSet.next()) {
			list.add(new Inventory(
					resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getInt(3),
					resultSet.getDate(4),
					resultSet.getDate(5),
					resultSet.getDate(6),
					resultSet.getString(7),
					resultSet.getString(8)
			));
		}
		return list;
	}

	public void addEmployee(Employee employee) throws SQLException, FileNotFoundException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("INSERT INTO employees (id, name, password) values (?,?,?)");
			statement.setString(1, employee.getId());
			statement.setString(2, employee.getNama());
			statement.setString(3, BCrypt.hashpw(employee.getPass(),BCrypt.gensalt()));
			statement.execute();
		} finally {
			statement.close();
			connection.close();
		}
	}

	public Employee validateEmployee(Employee employee) throws PasswordInvalidException, SQLException, FileNotFoundException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Employee foundEmployee = null;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
			statement.setString(1,employee.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if(!BCrypt.checkpw(employee.getPass(), resultSet.getString(3))) {
					throw new PasswordInvalidException();
				}
				foundEmployee = new Employee(
						resultSet.getString(1),
						resultSet.getString(2),
						null);
			}
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return foundEmployee;
	}
}
