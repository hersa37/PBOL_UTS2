package database;

public class Employee {
    private String id;
    private String nama;
    
    public Employee(String id){
        this.setId(id);
    }

    public Employee(String id, String nama) {
        this.setId(id);
        this.setNama(nama);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws IllegalArgumentException {
        if (id.length() == 8) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Panjang ID harus sama dengan 8.");
        }
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) throws IllegalArgumentException {
        if (nama.length() <= 40) {
           this.nama = nama; 
        } else {
            throw new IllegalArgumentException("Nama terlalu panjang");
        }
        
    }
    
}
