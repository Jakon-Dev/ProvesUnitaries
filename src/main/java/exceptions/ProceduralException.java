package exceptions;

/**
 * Indicates an error whenever the procedural order is altered
 */


public class ProceduralException extends RuntimeException {

    // Custom message constructor
    public ProceduralException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public ProceduralException(String message, Throwable cause) {
        super(message, cause);
    }

    // Cause constructor
    public ProceduralException(Throwable cause) {
        super("There has been a procedural error", cause);
    }

    // No parameter constructor
    public ProceduralException() {
        super("There has been a procedural error");
    }
}