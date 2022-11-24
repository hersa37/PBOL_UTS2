package database;

import org.mindrot.jbcrypt.BCrypt;

public class Employee {
    private String id;
    private String nama;
    private String pass;
    
    public Employee(String id){
        this.setId(id);
    }

    public Employee(String id, String nama, String pass) {
        this.setId(id);
        this.setNama(nama);
        this.setPass(pass);
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

    String getPass(){
        return pass;
    }

    void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nama='" + nama + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
