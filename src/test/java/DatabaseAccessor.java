import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
/*TODO
*  - Use BCrypt to encrypt passwords*/

public class DatabaseAccessor {

	public DatabaseAccessor(String user, String password) {

	}

	public DatabaseAccessor() {
		this("College_troublemad", "3c6cc66453d1ce9f4dd76803bc7b1eed459641f4");
	}

	public Connection getConnection() throws FileNotFoundException {
		Scanner scan = new Scanner(new File("credentials"));
		scan.useDelimiter(Pattern.compile("\n"));
		String[] creds = new String[2];
		/*TODO
		*  -Make sure credentials are properly stored and hashed*/
		int i = 0;
		while(scan.hasNext()) {
			creds[i] = scan.next();
			System.out.println(creds[i++]);
		}

		Connection connection = null;
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			/*TODO
			*  - Check for right table
			*  - make sure user is hashed*/
			connection = DriverManager.getConnection("jdbc:mysql://zf2.h.filess.io:3305/College_troublemad", creds[0], creds[1]);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}


	public static void main(String[] args) throws SQLException, FileNotFoundException {
		Connection con = (new DatabaseAccessor()).getConnection();
		con.close();
	}
}
