package exceptions;

/**
 * Exception thrown when invalid arguments are provided for pairing.
 */
public class InvalidPairingArgsException extends Exception {

    // No parameter constructor
    public InvalidPairingArgsException() {
        super("Pairing arguments are invalid");
    }

    // Custom message constructor
    public InvalidPairingArgsException(String message) {
        super(message);
    }

    // Custom message and cause constructor
    public InvalidPairingArgsException(String message, Throwable cause) {
        super(message, cause);
    }

    // Cause constructor
    public InvalidPairingArgsException(Throwable cause) {
        super("Pairing arguments are invalid", cause);
    }

    // Station is invalid stage
    public static InvalidPairingArgsException invalidStation() {
        return new InvalidPairingArgsException("The station ID provided does not exist or does not match the vehicle's location.");
    }

    // Geographic point is invalid stage
    public static InvalidPairingArgsException invalidGeographicPoint() {
        return new InvalidPairingArgsException("The geographic location provided is invalid.");
    }

    // Vehicle is not related to the referred station stage.
    public static InvalidPairingArgsException invalidVehicleStationRelation() {
        return new InvalidPairingArgsException("The vehicle is not located in the specified station.");
    }
}