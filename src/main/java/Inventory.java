
import java.sql.Date;

public class Inventory {
    
    private int SKU;
    private String Nama;
    private int Harga;
    private Date tanggal_masuk;
    private Date tanggal_keluar;
    private Date tanggal_kembali;
    private String Satuan;
    private String Peminjam;
    
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
    
    public int getSKU() {
        return SKU;
    }
    
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
    
    public void setHarga(int Harga) throws IllegalArgumentException {
        if (Harga / 1000000000 == 0) {
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
    
    public void setSatuan(String Satuan) throws IllegalArgumentException {
        if (Satuan.length() <= 10) {
            this.Satuan = Satuan;
        } else {
            throw new IllegalArgumentException("Hanya 10 digits");
        }
    }
    
    public String getPeminjam() {
        return Peminjam;
    }
    
    public void setPeminjam(String Peminjam) throws IllegalArgumentException {
        if (Peminjam.length() <= 8) {
            this.Peminjam = Peminjam;            
        } else {
            throw new IllegalArgumentException("ID terlalu panjang");
        }
        
    }
}
