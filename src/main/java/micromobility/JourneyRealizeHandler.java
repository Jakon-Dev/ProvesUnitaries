package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exceptions.*;
import micromobility.payment.ServiceID;
import micromobility.payment.Wallet;
import services.ArduinoMicroController;
import services.QRDecoder;
import services.Server;
import services.UnbondedBTSignal;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public class JourneyRealizeHandler {
    private Server server;
    private QRDecoder qrDecoder;
    private ArduinoMicroController arduinoController;
    private UnbondedBTSignal btSignal;
    PMVehicle vehicle;
    JourneyService journey;

    /**
     * Clase que maneja el flujo principal del caso de uso "Realizar desplazamiento".
     * Coordina el emparejamiento de vehículos, inicio y fin del trayecto, y el registro de pagos.
     */

    /**
     * Constructor que inicializa las dependencias necesarias para gestionar un desplazamiento.
     * @param server Instancia del servidor para registrar datos.
     * @param qrDecoder Decodificador de códigos QR para obtener ID de vehículos.
     * @param arduinoController Controlador Arduino para gestionar el vehículo.
     * @param btSignal Señal Bluetooth no emparejada para identificar estaciones.
     */

    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, ArduinoMicroController arduinoController, UnbondedBTSignal btSignal) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.arduinoController = arduinoController;
        this.btSignal = btSignal;
    }

    /**
     * Escanea un código QR para emparejar un vehículo y registrar el inicio del trayecto.
     * @param qrImage Imagen del código QR del vehículo.
     * @param station ID de la estación actual.
     * @param location Ubicación geográfica de la estación.
     * @throws CorruptedImgException Si el QR está dañado o es inválido.
     * @throws PMVNotAvailException Si el vehículo no está disponible.
     * @throws InvalidPairingArgsException Si los datos del emparejamiento son inválidos.
     * @throws ConnectException Si ocurre un error de conexión con el servidor.
     */

    public void scanQR(BufferedImage qrImage, StationID station, GeographicPoint location)
            throws CorruptedImgException, PMVNotAvailException, InvalidPairingArgsException, ConnectException {
        try {
            // Emitir la señal Bluetooth para identificar la estación
            btSignal.BTbroadcast();

            // Decodificar el código QR y obtener el ID del vehículo
            VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);

            // Verificar disponibilidad del vehículo en el servidor
            server.checkPMVAvail(vehicleID);

            // Registrar el emparejamiento en el servidor
            server.registerPairing(new UserAccount(vehicleID.getId()), vehicleID, station, location, LocalDateTime.now());

            // Establecer conexión Bluetooth con el vehículo
            arduinoController.setBTconnection();

            // Actualizar estado del vehículo y servicio de trayecto
            vehicle = new PMVehicle(vehicleID.getId(), PMVState.NotAvailable, location);
            vehicle.setNotAvailb();
            journey = new JourneyService(LocalDateTime.now());
        } catch (Exception e) {
            // Si algo falla, asegúrate de deshacer la conexión Bluetooth
            arduinoController.undoBTconnection();
            throw e;
        }
    }

    /**
     * Inicia el trayecto, cambiando el estado del vehículo a "UnderWay".
     * @throws PMVPhisicalException Si ocurre un fallo físico en el vehículo.
     * @throws ConnectException Si hay un error de conexión con el vehículo.
     * @throws ProceduralException Si el vehículo no está listo o no hay un trayecto inicializado.
     */

    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (vehicle == null || vehicle.getState() != PMVState.NotAvailable) {
            throw new ProceduralException("Vehicle is not available for starting the journey.");
        }

        if (journey == null) {
            throw new ProceduralException("Journey has not been initialized. Please pair the vehicle first.");
        }

        arduinoController.startDriving();
        vehicle.setUnderWay();
        journey.setServiceInit(LocalDateTime.now());
    }

    /**
     * Finaliza el trayecto, calcula métricas y registra los datos en el servidor.
     * @param endStation ID de la estación final.
     * @param endLocation Ubicación geográfica de la estación final.
     * @throws ConnectException Si hay problemas de conexión al finalizar.
     * @throws ProceduralException Si no hay un trayecto activo para finalizar.
     * @throws InvalidPairingArgsException Si los argumentos de emparejamiento son inválidos.
     * @throws PMVPhisicalException Si ocurre un fallo físico en el vehículo.
     */

    public void stopDriving(StationID endStation, GeographicPoint endLocation)
            throws ConnectException, ProceduralException, InvalidPairingArgsException, PMVPhisicalException {
        if (vehicle == null || journey == null || !journey.isInProgress()) {
            throw new ProceduralException("Cannot stop driving without an active journey.");
        }

        try {
            vehicle.setAvailb();
            vehicle.setLocation(endLocation);
            arduinoController.stopDriving();

            LocalDateTime now = LocalDateTime.now();
            float distance = calculateDistance(vehicle.getLocation(), endLocation);
            int duration = calculateDuration(journey.getInitDate(), now);
            if (duration <= 0) {
                throw new IllegalArgumentException("Duration must be greater than 0.");
            }
            float avgSpeed = distance / duration;
            BigDecimal importValue = calculateImport(distance, duration, avgSpeed);

            journey.setServiceFinish(now, distance, duration, avgSpeed, importValue);
            server.stopPairing(new UserAccount(vehicle.getVehicleID()), new VehicleID(vehicle.getVehicleID()),
                    endStation, endLocation, now, avgSpeed, distance, duration, importValue);

            server.registerLocation(new VehicleID(vehicle.getVehicleID()), endStation);
        } finally {
            // Asegurarse de deshacer la conexión Bluetooth incluso si algo falla
            arduinoController.undoBTconnection();
        }
    }

    /**
     * Procesa el pago del trayecto según el método seleccionado.
     * @param opt Método de pago ('W' para Wallet).
     * @param wallet Instancia de Wallet para deducir el importe.
     * @throws NotEnoughWalletException Si el saldo no es suficiente.
     * @throws ProceduralException Si el trayecto no ha finalizado.
     * @throws ConnectException Si hay problemas al registrar el pago en el servidor.
     */

    public void selectPaymentMethod(char opt, Wallet wallet)
            throws NotEnoughWalletException, ProceduralException, ConnectException {
        if (journey == null || journey.isInProgress()) {
            throw new ProceduralException("Cannot process payment without a completed journey.");
        }

        BigDecimal amount = journey.getImportValue();
        if (opt == 'W') {
            wallet.deduct(amount);
            server.registerPayment(new ServiceID(journey.hashCode()), new UserAccount(vehicle.getVehicleID()), amount, 'W');
        } else {
            throw new ProceduralException("Unsupported payment method.");
        }
    }

    /**
     * Calcula la distancia entre dos puntos geográficos usando la fórmula de distancia euclidiana.
     * @param start Punto inicial.
     * @param end Punto final.
     * @return Distancia en unidades arbitrarias.
     */

    private float calculateDistance(GeographicPoint start, GeographicPoint end) {
        return (float) Math.sqrt(Math.pow(start.getLatitude() - end.getLatitude(), 2) +
                Math.pow(start.getLongitude() - end.getLongitude(), 2));
    }

    /**
     * Calcula la duración de un trayecto en minutos.
     * @param start Hora de inicio.
     * @param end Hora de finalización.
     * @return Duración en minutos.
     */

    private int calculateDuration(LocalDateTime start, LocalDateTime end) {
        return (int) java.time.Duration.between(start, end).toMinutes();
    }

    /**
     * Calcula el importe total del trayecto basado en distancia, duración y velocidad promedio.
     * @param distance Distancia recorrida.
     * @param duration Duración en minutos.
     * @param avgSpeed Velocidad promedio.
     * @return Importe calculado.
     */

    private BigDecimal calculateImport(float distance, int duration, float avgSpeed) {
        return BigDecimal.valueOf(distance * 0.5 + duration * 0.1 + avgSpeed * 0.2);
    }

    /**
     * Asigna un vehículo a esta instancia para gestionar su estado.
     * @param vehicle Instancia del vehículo.
     */

    public void setVehicle(PMVehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Asigna un servicio de trayecto a esta instancia para gestionar sus datos.
     * @param journey Instancia del servicio de trayecto.
     */

    public void setJourney(JourneyService journey) {
        this.journey = journey;
    }
}