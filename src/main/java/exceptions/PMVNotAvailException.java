package exceptions;

/**
 * Exception thrown when the requested vehicle is not available for pairing.
 */
public class PMVNotAvailException extends Exception {

    // Custom message constructor
    public PMVNotAvailException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public PMVNotAvailException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }

    // Cause constructor
    public PMVNotAvailException(Throwable cause) {
        super("The requested Personal Mobility Vehicle (PMV) is not available. It may already be paired with another user.", cause);
    }

    // No parameter constructor
    public PMVNotAvailException() {
        super("The requested Personal Mobility Vehicle (PMV) is not available. It may already be paired with another user.");
    }
}