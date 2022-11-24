
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FirstCommit {
	public static void main(String[] args) throws SQLException {
		FirstCommit test = new FirstCommit();
                Connection con = test.getConnection();
                con.close();
	}
        
        
    Connection getConnection() {
      
        String usr = "College_troublemad";
        String pwd = "3c6cc66453d1ce9f4dd76803bc7b1eed459641f4";

        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://zf2.h.filess.io:3305/College_troublemad", usr, pwd);
        } catch (SQLException ex) {
            System.out.println("maaf koneksi tidak berhasil.");
            System.out.println(ex.getMessage());
        }
        if (conn != null) {
            System.out.println("koneksi ke database berhasil terbentuk");
        } else {
            System.out.println("maaf koneksi ke database gagal terbentuk");
        }
        return conn;
    }
}
