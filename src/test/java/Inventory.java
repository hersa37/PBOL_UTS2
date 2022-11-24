import java.sql.Date;
import java.util.regex.PatternSyntaxException;

public class Inventory {

	private String sku;
	private String nama;
	private String deskripsi;
	private int harga;
	private Date tanggal_masuk;
	private String satuan;
	private String lokasi;
	private String penanggungJawab;

	public Inventory(String sku, String nama, String deskripsi, int harga, Date tanggal_masuk, String satuan, String lokasi, String penanggungJawab) throws Exception{
		this.setSku(sku);
		this.setNama(nama);
		this.setDeskripsi(deskripsi);
		this.setHarga(harga);
		this.setTanggal_masuk(tanggal_masuk);
		this.setSatuan(satuan);
		this.setLokasi(lokasi);
		this.setPenanggungJawab(penanggungJawab);
		this.lokasi = lokasi;
		this.penanggungJawab = penanggungJawab;
	}

	public Inventory(String sku) {
		this.sku = sku;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) throws PatternSyntaxException {
		if (sku.matches("(a-Z)(a-Z)(0-9)(0-9)(0-9)")) {
			this.sku = sku;
		} else throw new PatternSyntaxException("Wrong format", "(a-Z)(a-Z)(0-9)(0-9)(0-9)", -1);
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) throws StringLengthException {
		if (deskripsi.length() < 255) {
			this.deskripsi = deskripsi;
		} else throw new StringLengthException(255, deskripsi.length());
	}

	public int getHarga() {
		return harga;
	}

	public void setHarga(int harga) throws IllegalArgumentException{
		if (harga >= 0) {
			this.harga = harga;
		} else throw new IllegalArgumentException("Price can't be less than 0");
	}

	public Date getTanggal_masuk() {
		return tanggal_masuk;
	}

	public void setTanggal_masuk(Date tanggal_masuk) {
		this.tanggal_masuk = tanggal_masuk;
	}

	public String getSatuan() {
		return satuan;
	}

	public void setSatuan(String satuan) {
		this.satuan = satuan;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getPenanggungJawab() {
		return penanggungJawab;
	}

	public void setPenanggungJawab(String penanggungJawab) {
		this.penanggungJawab = penanggungJawab;
	}

	@Override
	public String toString() {
		return "database.Inventory{" +
				"sku='" + sku + '\'' +
				", deskripsi='" + deskripsi + '\'' +
				", harga=" + harga +
				", tanggal_masuk=" + tanggal_masuk +
				", satuan='" + satuan + '\'' +
				", lokasi='" + lokasi + '\'' +
				", penanggungJawab='" + penanggungJawab + '\'' +
				'}';
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}
}
