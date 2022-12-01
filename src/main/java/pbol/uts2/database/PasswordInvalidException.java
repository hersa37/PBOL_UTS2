package pbol.uts2.database;

public class PasswordInvalidException extends Exception{
	public PasswordInvalidException() {
		super("Invalid Password");
	}
}
