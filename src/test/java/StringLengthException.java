public class StringLengthException extends Exception{
	private int MAX;
	private int length;

	public StringLengthException(int max, int length){
		super(" too long for size ");
	}

	public String getMessage() {
		return length + super.getMessage() + MAX;
	}
}
