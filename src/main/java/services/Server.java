package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exceptions.InvalidPairingArgsException;
import exceptions.PMVNotAvailException;
import exceptions.PairingNotFoundException;
import micromobility.JourneyService;
import micromobility.payment.ServiceID;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

/**
 * Interfaz para manejar interacciones con el servidor.
 */
public interface Server {

    /**
     * Verifica si un vehículo está disponible.
     * @param veh ID del vehículo.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void checkPMVAvail(VehicleID veh) throws ConnectException, PMVNotAvailException;

    /**
     * Registra un emparejamiento entre un usuario y un vehículo.
     * @param user Usuario que realiza el emparejamiento.
     * @param veh ID del vehículo.
     * @param st ID de la estación.
     * @param loc Ubicación de la estación.
     * @param date Fecha y hora del emparejamiento.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void registerPairing(data.UserAccount user, VehicleID veh, data.StationID st, data.GeographicPoint loc, java.time.LocalDateTime date) throws ConnectException;

    /**
     * Finaliza un emparejamiento y registra un servicio de trayecto.
     * @throws exceptions.PairingNotFoundException Si el emparejamiento no se encuentra.
     */
    boolean stopPairing(UserAccount user, VehicleID vehicle, StationID station,
                        GeographicPoint location, LocalDateTime date, float avgSpeed,
                        float distance, int duration, BigDecimal importValue);

    /**
     * Registra la ubicación actual de un vehículo en una estación.
     * @param veh ID del vehículo.
     * @param st ID de la estación.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void registerLocation(VehicleID veh, data.StationID st) throws ConnectException;

    /**
     * Registra un pago realizado por un usuario.
     * @param sid ID del servicio.
     * @param user Usuario que realiza el pago.
     * @param amount Monto del pago.
     * @param method Método de pago.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void registerPayment(micromobility.payment.ServiceID sid, data.UserAccount user, java.math.BigDecimal amount, char method) throws ConnectException;
}