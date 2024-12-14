package exceptions;

/**
 * Exception that indicates an error when pairing information for the
 * server isn't found.
 */

public class PairingNotFoundException extends Exception {
    // Custom message constructor
    public PairingNotFoundException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public PairingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Cause constructor
    public PairingNotFoundException(Throwable cause) {
        super("Pairing information not found.", cause);
    }

    // No parameter constructor
    public PairingNotFoundException() {
        super("Pairing information not found.");
    }

}