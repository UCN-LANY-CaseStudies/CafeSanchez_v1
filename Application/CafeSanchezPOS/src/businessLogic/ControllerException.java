package businessLogic;

public class ControllerException extends Exception {

	private static final long serialVersionUID = -9148850459930565908L;

	public ControllerException(String message) {
		super(message);

	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);

	}
}
