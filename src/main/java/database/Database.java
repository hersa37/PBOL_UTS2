package database;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
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


	public static void saveCredentials(String url, String id, String pass) throws IOException {
		Writer writer = new FileWriter("credentials");
		writer.write(url+"\n"+id+"\n"+pass);
		writer.close();
	}
	public Connection getConnection() throws FileNotFoundException, SQLException {

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
		Connection conn;
		conn = DriverManager.getConnection(creds[0], creds[1], creds[2]);
		/*
		conn = null dihandle method yang panggil
		 */

		return conn;
	}

	public boolean testConnection(String url, String id, String pass) throws SQLException{
		return (DriverManager.getConnection(url, id, pass) != null);
	}

	public void addInventory(Inventory inventory) throws SQLException, FileNotFoundException{
		try (Connection connection = this.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO Inventory (SKU, Nama, Harga, Tanggal_Masuk, Tanggal_Keluar, Tanggal_Kembali, Satuan, Peminjam) VALUE (?,?,?,?,?,?,?,?)")) {
			statement.setInt(1, inventory.getSKU());
			statement.setString(2, inventory.getNama());
			statement.setInt(3, inventory.getHarga());
			statement.setDate(4, inventory.getTanggal_masuk());
			statement.setDate(5, inventory.getTanggal_keluar());
			statement.setDate(6, inventory.getTanggal_kembali());
			statement.executeUpdate();
		}
	}
	public void updateInventory(Inventory inventory) throws SQLException, FileNotFoundException{
		try (Connection connection = this.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE Inventory SET Nama = ?, Harga = ?, Tanggal_Masuk = ?, Tanggal_Keluar = ?, Tanggal_Kembali = ?, Satuan = ?, Peminjam = ? WHERE SKU = ?" )) {
			statement.setString(1, inventory.getNama());
			statement.setInt(2, inventory.getHarga());
			statement.setDate(3, inventory.getTanggal_masuk());
			statement.setDate(4, inventory.getTanggal_keluar());
			statement.setDate(5, inventory.getTanggal_kembali());
			statement.setString(6, inventory.getSatuan());
			statement.setString(7, inventory.getPeminjam());
			statement.setInt(8, inventory.getSKU());
			statement.executeUpdate();
		}
	}

	public LinkedList<Inventory> getInventories() throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list;
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

	public LinkedList<Inventory> getEmployeeInventory(Employee employeeInventory) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("SELECT * FROM Inventory WHERE Peminjam = ?");
			statement.setString(1, employeeInventory.getId());
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

	public Inventory findInventory(Inventory inventory) throws FileNotFoundException, SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Inventory foundInventory = null;
		try {
			connection = this.getConnection();
			statement  = connection.prepareStatement("SELECT * FROM Inventory WHERE SKU = ?");
			statement.setInt(1,inventory.getSKU());
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				foundInventory = new Inventory(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getInt(3),
						resultSet.getDate(4),
						resultSet.getDate(5),
						resultSet.getDate(6),
						resultSet.getString(7),
						resultSet.getString(8)
				);
			}
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return foundInventory;
	}

	public void deleteInventory(Inventory inventory) throws SQLException, FileNotFoundException{

		try(Connection connection = this.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM Inventory WHERE SKU = ?")) {
			statement.setInt(1, inventory.getSKU());
			statement.executeUpdate();
		}
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
		try (Connection connection = this.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO employees (id, name, password) values (?,?,?)")) {
			statement.setString(1, employee.getId());
			statement.setString(2, employee.getNama());
			statement.setString(3, BCrypt.hashpw(employee.getPass(), BCrypt.gensalt()));
			statement.execute();
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
				employee.setPass(null);
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

	public void printToFile(Employee employee, LinkedList<Inventory> inventories) {

	}
}
