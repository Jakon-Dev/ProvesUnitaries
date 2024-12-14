package exceptions;

/**
 * Indicates an error whenever the car cannot be started due to technical issues.
 */


public class PMVPhisicalException extends Exception {

    // Custom message constructor
    public PMVPhisicalException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public PMVPhisicalException(String message, Throwable cause) {
        super(message, cause);
    }

    // Cause constructor
    public PMVPhisicalException(Throwable cause) {
        super("There was a technical issue that prevented the vehicle from starting", cause);
    }

    // No parameter constructor
    public PMVPhisicalException() {
        super("There was a technical issue that prevented the vehicle from starting");
    }
}