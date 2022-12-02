package pbol.uts2.database;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Database {

	/**
	 * Method untuk menyimpan detail koneksi ke database
	 *
	 * @param url  database
	 * @param id   database
	 * @param pass database
	 * @throws IOException saat file tidak dapat dibuat
	 */
	public void saveCredentials(String url, String id, String pass) throws IOException {
		Writer writer = new FileWriter("credentials");
		writer.write(url + "\n" + id + "\n" + pass);
		writer.close();
	}

	/**
	 * Method untuk membuat koneksi dengan database
	 *
	 * @return koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi tidak ditemukan
	 * @throws SQLException          saat terjadi masalah dengan koneksi database
	 */
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

	/**
	 * Method untuk mengetes koneksi ke database berdasarkan parameter yang dimasukkan
	 *
	 * @param url  percobaan ke database
	 * @param id   percobaan ke database
	 * @param pass percobaan ke database
	 * @throws SQLException saat terjadi masalah dengan koneksi database
	 */
	public void testConnection(String url, String id, String pass) throws SQLException {
		DriverManager.getConnection(url, id, pass).close();
	}

	/**
	 * Method untuk menambahkan barang ke database
	 *
	 * @param inventory Objek inventory yang ditambahkan
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi tidak ditemukan
	 */
	public void addInventory(Inventory inventory) throws SQLException, FileNotFoundException {
		try (Connection connection = this.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO Inventory (SKU, Nama, Harga, Tanggal_Masuk, Tanggal_Keluar, Tanggal_Kembali, Satuan, Peminjam) VALUE (?,?,?,?,?,?,?,?)")) {
			statement.setInt(1, inventory.getSKU());
			statement.setString(2, inventory.getNama());
			statement.setInt(3, inventory.getHarga());
			statement.setDate(4, inventory.getTanggal_masuk());
			statement.setDate(5, inventory.getTanggal_keluar());
			statement.setDate(6, inventory.getTanggal_kembali());
			statement.setString(7, inventory.getSatuan());
			statement.setString(8, inventory.getPeminjam());
			statement.executeUpdate();
		}
	}

	/**
	 * Method untuk mengubah semua atribut barang dalam database kecuali SKU
	 *
	 * @param inventory Barang dengan atribut yang sudah diubah
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi tidak ditemukan
	 */
	public void updateInventory(Inventory inventory) throws SQLException, FileNotFoundException {
		try (Connection connection = this.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE Inventory SET Nama = ?, Harga = ?, Tanggal_Masuk = ?, Tanggal_Keluar = ?, Tanggal_Kembali = ?, Satuan = ?, Peminjam = ? WHERE SKU = ?")) {
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

	/**
	 * Method untuk melakukan operasi checkout dan mengembalikan daftar barang yang belum dipinjam siapa-siapa
	 *
	 * @param inventory Barang yang akan di-checkout
	 * @return daftar barang yang tidak ada peminjamnya
	 * @throws SQLException          saat terdapat masalah dengan koneksi database
	 * @throws FileNotFoundException saat file detail koneksi tidak ditemukan
	 */
	public LinkedList<Inventory> checkoutInventory(Inventory inventory) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("UPDATE Inventory SET Tanggal_Keluar = ?, Tanggal_Kembali = ?, Peminjam = ? WHERE SKU = ?");
			statement.setDate(1, inventory.getTanggal_keluar());
			statement.setDate(2, inventory.getTanggal_kembali());
			statement.setString(3, inventory.getPeminjam());
			statement.setInt(4, inventory.getSKU());
			statement.executeUpdate();

			statement = connection.prepareStatement("SELECT * FROM Inventory WHERE Peminjam IS NULL");
			resultSet = statement.executeQuery();
			list = createInventoryList(resultSet);
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return list;
	}

	/**
	 * Method untuk mengembalikan barang dari user. Dilakukan dengan mengubah tanggal keluar, tanggal kembali, dan peminjam
	 * menjadi null.
	 *
	 * @param inventory yang dikembalikan.
	 * @return daftar barang yang masih dipinjam oleh user
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file koneksi ke database tidak dapat ditemukan
	 */
	public LinkedList<Inventory> returnInventory(Inventory inventory) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list;
		try {
			String peminjam = inventory.getPeminjam();
			inventory.setTanggal_keluar(null);
			inventory.setTanggal_kembali(null);
			inventory.setPeminjam(null);
			connection = this.getConnection();
			statement = connection.prepareStatement("UPDATE Inventory SET Tanggal_Keluar = ?, Tanggal_Kembali = ?, Peminjam = ? WHERE SKU = ?");
			statement.setDate(1, inventory.getTanggal_keluar());
			statement.setDate(2, inventory.getTanggal_kembali());
			statement.setString(3, inventory.getPeminjam());
			statement.setInt(4, inventory.getSKU());
			statement.executeUpdate();

			statement = connection.prepareStatement("SELECT * FROM Inventory WHERE Peminjam = ?");
			statement.setString(1, peminjam);
			resultSet = statement.executeQuery();
			list = createInventoryList(resultSet);
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return list;
	}

	/**
	 * Method untuk mendapatkan daftar semua inventory dari database
	 *
	 * @return daftar inventory
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi database tidak ditemukan
	 */
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

	/**
	 * Method untuk mendapatkan daftar inventory yang dipinjam employee tertentu
	 *
	 * @param employeeInventory yang meminjam inventory
	 * @return daftar barang yang dipinjam employee
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi database tidak ditemukan
	 */
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

	/**
	 * Method untuk mendapatkan daftar inventory yang tidak dipinjam siapa-siapa
	 *
	 * @return daftar barang yang tidak dipinjam ke siapa-siapa
	 * @throws NullPointerException  saat tidak ada barang yang masuk ke daftar
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi database tidak ditemukan
	 */
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

	/**
	 * Method untuk mencari inventory berdasarkan SKU
	 *
	 * @param inventory yang dicari
	 * @return objek inventory dari database
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi database tidak ditemukan
	 */
	public Inventory findInventory(Inventory inventory) throws FileNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Inventory foundInventory = null;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("SELECT * FROM Inventory WHERE SKU = ?");
			statement.setInt(1, inventory.getSKU());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
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

	/**
	 * Method untuk menghapus barang dari database berdasarkan SKU
	 *
	 * @param inventory yang akan dihapus
	 * @return daftar barang yang masih ada
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi database tidak ditemukan
	 */
	public LinkedList<Inventory> removeInventory(Inventory inventory) throws FileNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Inventory> list;
		try {

			connection = this.getConnection();
			statement = connection.prepareStatement("DELETE FROM Inventory WHERE SKU = ?");
			statement.setInt(1, inventory.getSKU());
			statement.executeUpdate();

			statement = connection.prepareStatement("SELECT * FROM Inventory");
			resultSet = statement.executeQuery();
			list = createInventoryList(resultSet);
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return list;
	}

	/**
	 * Method untuk menghasilkan daftar barang berdasarkan hasil query
	 *
	 * @param resultSet dari hasil query
	 * @return daftar barang
	 * @throws SQLException saat terjadi masalah dengan koneksi ke database
	 */
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

	/**
	 * Method untuk menambahkan employee ke database
	 *
	 * @param employee yang ditambahkan
	 * @throws SQLException          saat terjadi masalah dengan koneksi ke database
	 * @throws FileNotFoundException saat file detail koneksi database tidak ditemukan
	 */
	public void addEmployee(Employee employee) throws SQLException, FileNotFoundException {
		try (Connection connection = this.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO employees (id, name, password) values (?,?,?)")) {
			statement.setString(1, employee.getId());
			statement.setString(2, employee.getNama());
			statement.setString(3, BCrypt.hashpw(employee.getPass(), BCrypt.gensalt()));
			statement.execute();
		}
	}

	/**
	 * Method untuk memvalidasi employee yang mencoba masuk ke database. Password di database disimpan sebagai hash
	 * menggunakan {@link BCrypt} sehingga password dipastikan sama menggunakan {@link BCrypt#checkpw(String, String)}
	 *
	 * @param employee yang divalidasi
	 * @return employee yang sudah divalidasi
	 * @throws PasswordInvalidException saat password salah
	 * @throws SQLException             saat terjadi masalah dengan koneksi database
	 * @throws FileNotFoundException    saat file koneksi database tidak ditemukan
	 */
	public Employee validateEmployee(Employee employee) throws PasswordInvalidException, SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Employee foundEmployee = null;
		try {
			connection = this.getConnection();
			statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
			statement.setString(1, employee.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (!BCrypt.checkpw(employee.getPass(), resultSet.getString(3))) {
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
}
