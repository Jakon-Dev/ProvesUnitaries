package exceptions;

/**
 * Exception provided by the API. Indicates an error that occurs when attempting to connect a socket to a remote address
 * and port. Typically, this is because the connection was remotely rejected (e.g., no process was listening on the remote
 * address/port). It is located in the java.net package. We will also use it for exceptions related to Bluetooth usage.
 */

public class ConnectException extends java.net.ConnectException {

    // No parameter constructor
    public ConnectException() {
        super("Error connecting: connection refused or unavailable.");
    }

    // Custom message constructor
    public ConnectException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public ConnectException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }

    //Bluetooth ConnectionException type
    public static ConnectException noBluetoothConnection() {
        return new ConnectException("Error connecting: Bluetooth connection refused or unavaliable");
    }

    // Cause constructor
    public ConnectException(Throwable cause) {
        super("Error connecting: connection refused or unavailable.");
        initCause(cause);
    }
}