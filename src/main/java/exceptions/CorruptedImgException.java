package exceptions;

/**
 * Exception indicating that the captured image is not valid.
 */


public class CorruptedImgException extends RuntimeException {

    // Custom message constructor
    public CorruptedImgException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public CorruptedImgException(String message, Throwable cause) {
        super(message, cause);
    }

    // No parameter constructor
    public CorruptedImgException() {
        super("The image is invalid");
    }

    // Cause constructor
    public CorruptedImgException(Throwable cause) {
        super("The image is invalid", cause);
    }
}