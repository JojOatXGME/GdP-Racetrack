package gdp.racetrack;

/**
 * This exception is thrown if a player does a illegal turn.
 */
public class IllegalTurnException extends RuntimeException {
	private static final long serialVersionUID = -5141627377021338495L;

	IllegalTurnException() {
		super();
	}

	IllegalTurnException(String message) {
		super(message);
	}

	IllegalTurnException(String message, Throwable cause) {
		super(message, cause);
	}

}
