package dataAccess;

public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = 6246087753700132201L;

	public DataAccessException(String message) {
		super(message);		
	}
}
