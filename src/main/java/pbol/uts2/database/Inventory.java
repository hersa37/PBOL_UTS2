package pbol.uts2.database;

import java.sql.Date;

/**
 * Class untuk membersihkan input dari user sebelum memasukkan record ke database
 */
public class Inventory {

	private int SKU;
	private String Nama;
	private int Harga;
	private Date tanggal_masuk;
	private Date tanggal_keluar;
	private Date tanggal_kembali;
	private String Satuan;
	private String Peminjam;

	/**
	 * Constructor dengan parameter lengkap
	 *
	 * @param SKU
	 * @param Nama
	 * @param Harga
	 * @param tanggal_masuk
	 * @param tanggal_keluar
	 * @param tanggal_kembali
	 * @param Satuan
	 * @param Peminjam
	 */
	public Inventory(int SKU, String Nama, int Harga, Date tanggal_masuk, Date tanggal_keluar, Date tanggal_kembali, String Satuan, String Peminjam) {
		this.setSKU(SKU);
		this.setNama(Nama);
		this.setHarga(Harga);
		this.setTanggal_masuk(tanggal_masuk);
		this.setTanggal_keluar(tanggal_keluar);
		this.setTanggal_kembali(tanggal_kembali);
		this.setSatuan(Satuan);
		this.setPeminjam(Peminjam);
	}

	/**
	 * Constructor dengan parameter SKU. Digunakan saat mencari berdasarkan SKU
	 *
	 * @param sku
	 */
	public Inventory(int sku) {
		this.SKU = sku;
	}

	public int getSKU() {
		return SKU;
	}

	/**
	 * Setter yang memastikan bahwa SKU yang dimasukkan maksimal 4 digit.
	 *
	 * @param SKU yang dimasukkan
	 * @throws IllegalArgumentException saat lebih dari 4 digit
	 */
	public void setSKU(int SKU) throws IllegalArgumentException {
		if (SKU / 10000 == 0) {
			this.SKU = SKU;
		} else {
			throw new IllegalArgumentException("Harus 4 digits");
		}
	}

	public String getNama() {
		return Nama;
	}

	/**
	 * Setter untuk memastikan ponjang nama tidka lebih dari 10
	 *
	 * @param Nama inventory yang dimasukkan
	 * @throws IllegalArgumentException saat panjang nama lebih dari 10
	 */
	public void setNama(String Nama) throws IllegalArgumentException {
		if (Nama.length() <= 10) {
			this.Nama = Nama;
		} else {
			throw new IllegalArgumentException("");
		}
	}

	public int getHarga() {
		return Harga;
	}

	/**
	 * Membatasi harga menjadi maksimal 1 milyar
	 *
	 * @param Harga inventory
	 * @throws IllegalArgumentException saat melebihi batas harga
	 */
	public void setHarga(int Harga) throws IllegalArgumentException {
		if (Harga / 1_000_000_000 == 0) {
			this.Harga = Harga;
		} else {
			throw new IllegalArgumentException("");
		}
	}

	public Date getTanggal_masuk() {
		return tanggal_masuk;
	}

	public void setTanggal_masuk(Date tanggal_masuk) {
		this.tanggal_masuk = tanggal_masuk;
	}

	public Date getTanggal_keluar() {
		return tanggal_keluar;
	}

	public void setTanggal_keluar(Date tanggal_keluar) {
		this.tanggal_keluar = tanggal_keluar;
	}

	public Date getTanggal_kembali() {
		return tanggal_kembali;
	}

	public void setTanggal_kembali(Date tanggal_kembali) {
		this.tanggal_kembali = tanggal_kembali;
	}

	public String getSatuan() {
		return Satuan;
	}

	/**
	 * Memastikan satuan tidak lebih dari 10 karakter
	 *
	 * @param Satuan inventory
	 * @throws IllegalArgumentException saat lebih dari 10 karakter
	 */
	public void setSatuan(String Satuan) throws IllegalArgumentException {
		if (Satuan == null || Satuan.length() <= 10) {
			this.Satuan = Satuan;
		} else {
			throw new IllegalArgumentException("Hanya 10 karakter");
		}
	}

	public String getPeminjam() {
		return Peminjam;
	}

	/**
	 * Memastikan ID peminjam adalah 8 atau tidak ada.
	 *
	 * @param Peminjam id peminjam
	 * @throws IllegalArgumentException saat id tidak 8/null
	 */
	public void setPeminjam(String Peminjam) throws IllegalArgumentException {
		if (Peminjam == null || Peminjam.length() == 8) {
			this.Peminjam = Peminjam;
		} else {
			throw new IllegalArgumentException("ID terlalu panjang");
		}
	}

	@Override
	public String toString() {
		return "Inventory{" +
				"SKU=" + SKU +
				", Nama='" + Nama + '\'' +
				", Harga=" + Harga +
				", tanggal_masuk=" + tanggal_masuk +
				", tanggal_keluar=" + tanggal_keluar +
				", tanggal_kembali=" + tanggal_kembali +
				", Satuan='" + Satuan + '\'' +
				", Peminjam='" + Peminjam + '\'' +
				'}';
	}
}
