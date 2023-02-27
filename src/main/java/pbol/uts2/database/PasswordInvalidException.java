package pbol.uts2.database;

/**
 * Exception untuk mengindikasi password salah
 */
public class PasswordInvalidException extends Exception {
	public PasswordInvalidException() {
		super("Invalid Password");
	}
}
