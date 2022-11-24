package database;

public class PasswordInvalidException extends Exception{
	public PasswordInvalidException() {
		super("Invalid Password");
	}
}
