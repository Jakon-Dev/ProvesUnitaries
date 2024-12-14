package exceptions;

/**
 * Indicates there is not enough money in the app wallet.
 */


public class NotEnoughWalletException extends RuntimeException {
    // Custom message constructor
    public NotEnoughWalletException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public NotEnoughWalletException(String message, Throwable cause) {
        super(message, cause);
    }

    // Cause constructor
    public NotEnoughWalletException(Throwable cause) {
        super("Wallet doesn't have enough money to proceed.", cause);
    }

    // No parameter constructor
    public NotEnoughWalletException() {
        super("Wallet doesn't have enough money to proceed.");
    }

}