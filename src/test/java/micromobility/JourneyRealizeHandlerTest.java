package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.VehicleID;
import exceptions.*;
import micromobility.payment.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ArduinoMicroController;
import services.QRDecoder;
import services.Server;
import services.UnbondedBTSignal;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
// En estos tests también se comprobaran parte de las interficies y sus "Exception"'s
class JourneyRealizeHandlerTest {

    private Server serverMock;
    private QRDecoder qrDecoderMock;
    private ArduinoMicroController arduinoMock;
    private UnbondedBTSignal btSignalMock;
    private JourneyRealizeHandler handler;

    @BeforeEach
    void setUp() {
        serverMock = mock(Server.class);
        qrDecoderMock = mock(QRDecoder.class);
        arduinoMock = mock(ArduinoMicroController.class);
        btSignalMock = mock(UnbondedBTSignal.class);
        handler = new JourneyRealizeHandler(serverMock, qrDecoderMock, arduinoMock, btSignalMock);
    }

    @Test
    void scanQR_ShouldPairVehicleSuccessfully() throws Exception {
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(new VehicleID("VEH123"));
        doNothing().when(serverMock).checkPMVAvail(any(VehicleID.class));
        doNothing().when(serverMock).registerPairing(any(), any(), any(), any(), any());
        doNothing().when(arduinoMock).setBTconnection();

        handler.scanQR(qrImage, station, location);

        assertEquals("VEH123", handler.vehicle.getVehicleID());
        assertEquals(PMVState.NotAvailable, handler.vehicle.getState());
        assertTrue(handler.journey.isInProgress());
    }

    @Test
    void scanQR_ShouldThrowExceptionWhenVehicleNotAvailable() throws Exception {
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        VehicleID vehicleID = new VehicleID("VEH123");

        // Configurar mocks para simular el flujo
        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(vehicleID);
        doThrow(new PMVNotAvailException("The requested PMV is not available."))
                .when(serverMock).checkPMVAvail(vehicleID);

        // Verificar que la excepción se lanza correctamente
        assertThrows(PMVNotAvailException.class, () -> handler.scanQR(qrImage, station, location));

        // Verificar que se llamaron los métodos correctos
        verify(qrDecoderMock, times(1)).getVehicleID(qrImage);
        verify(serverMock, times(1)).checkPMVAvail(vehicleID);
        verify(arduinoMock, never()).setBTconnection(); // Nunca se debe llamar si falla `checkPMVAvail`
    }


    @Test
    void startDriving_ShouldSetVehicleUnderWay() throws Exception {
        // Simulación de los datos necesarios
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        // Configurar mocks
        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(new VehicleID("VEH123"));
        doNothing().when(serverMock).checkPMVAvail(any(VehicleID.class));
        doNothing().when(serverMock).registerPairing(any(), any(), any(), any(), any());
        doNothing().when(arduinoMock).setBTconnection();

        // Llamar a scanQR para inicializar journey y vehicle
        handler.scanQR(qrImage, station, location);

        // Simular comportamiento de startDriving
        doNothing().when(arduinoMock).startDriving();

        // Llamar al método startDriving
        handler.startDriving();

        // Validar que el estado del vehículo es UnderWay
        assertEquals(PMVState.UnderWay, handler.vehicle.getState());
    }


    @Test
    void startDriving_ShouldThrowExceptionWhenVehicleNotAvailable() {
        PMVehicle vehicle = new PMVehicle("VEH123", PMVState.Available, new GeographicPoint(41.40338F, 2.17403F));
        handler.setVehicle(vehicle);

        assertThrows(ProceduralException.class, handler::startDriving);
    }

    @Test
    void stopDriving_ShouldCalculateMetricsAndUpdateState() throws Exception {
        PMVehicle vehicle = new PMVehicle("VEH123", PMVState.UnderWay, new GeographicPoint(41.40338F, 2.17403F));
        handler.setVehicle(vehicle);
        JourneyService journey = new JourneyService(LocalDateTime.now().minusMinutes(10));
        handler.setJourney(journey);

        StationID endStation = new StationID("ST123");
        GeographicPoint endLocation = new GeographicPoint(41.40438F, 2.17503F);

        doNothing().when(arduinoMock).stopDriving();
        when(serverMock.stopPairing(any(), any(), any(), any(), any(), anyFloat(), anyFloat(), anyInt(), any())).thenReturn(true);

        handler.stopDriving(endStation, endLocation);

        assertEquals(PMVState.Available, vehicle.getState());
        assertFalse(journey.isInProgress());
    }

    @Test
    void stopDriving_ShouldThrowExceptionWhenJourneyNotInProgress() {
        PMVehicle vehicle = new PMVehicle("VEH123", PMVState.Available, new GeographicPoint(41.40338F, 2.17403F));
        handler.setVehicle(vehicle);
        handler.setJourney(null);

        StationID endStation = new StationID("ST123");
        GeographicPoint endLocation = new GeographicPoint(41.40438F, 2.17503F);

        assertThrows(ProceduralException.class, () -> handler.stopDriving(endStation, endLocation));
    }

    @Test
    void selectPaymentMethod_ShouldDeductFromWallet() throws Exception {
        // Simulación de datos necesarios
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        // Configurar mocks para scanQR
        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(new VehicleID("VEH123"));
        doNothing().when(serverMock).checkPMVAvail(any(VehicleID.class));
        doNothing().when(serverMock).registerPairing(any(), any(), any(), any(), any());
        doNothing().when(arduinoMock).setBTconnection();

        // Llamar a scanQR para inicializar journey y vehicle
        handler.scanQR(qrImage, station, location);

        // Configurar JourneyService para representar un trayecto finalizado
        JourneyService journey = new JourneyService(LocalDateTime.now());
        journey.setServiceFinish(LocalDateTime.now(), 5.0f, 10, 30.0f, BigDecimal.valueOf(20));
        handler.setJourney(journey);

        Wallet wallet = new Wallet(BigDecimal.valueOf(50));

        // Mock para registerPayment
        doNothing().when(serverMock).registerPayment(any(), any(), any(), eq('W'));

        // Llamar al método selectPaymentMethod
        handler.selectPaymentMethod('W', wallet);

        // Validar que se dedujo el importe del monedero
        assertEquals(BigDecimal.valueOf(30), wallet.getBalance());
    }


    @Test
    void selectPaymentMethod_ShouldThrowExceptionWhenJourneyInProgress() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(50));
        JourneyService journey = new JourneyService(LocalDateTime.now());
        handler.setJourney(journey);

        assertThrows(ProceduralException.class, () -> handler.selectPaymentMethod('W', wallet));
    }

    @Test
    void scanQR_ShouldBroadcastBluetoothSignal() throws Exception {
        // Simulación de datos necesarios
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        // Configurar mocks
        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(new VehicleID("VEH123"));
        doNothing().when(serverMock).checkPMVAvail(any(VehicleID.class));
        doNothing().when(serverMock).registerPairing(any(), any(), any(), any(), any());
        doNothing().when(arduinoMock).setBTconnection();
        doNothing().when(btSignalMock).BTbroadcast();

        // Ejecutar scanQR
        handler.scanQR(qrImage, station, location);

        // Verificar que BTbroadcast fue invocado
        verify(btSignalMock).BTbroadcast();
    }

    @Test
    void scanQR_ShouldThrowExceptionWhenQRImageIsCorrupted() throws Exception {
        // Simulación de datos necesarios
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        // Configurar mocks para lanzar la excepción CorruptedImgException
        when(qrDecoderMock.getVehicleID(qrImage)).thenThrow(new CorruptedImgException());

        // Verificar que se lanza la excepción al ejecutar scanQR
        assertThrows(CorruptedImgException.class, () -> handler.scanQR(qrImage, station, location));

        // Verificar que no se realizan acciones posteriores
        verify(serverMock, never()).checkPMVAvail(any());
        verify(serverMock, never()).registerPairing(any(), any(), any(), any(), any());
        verify(arduinoMock, never()).setBTconnection();
    }

    @Test
    void stopDriving_ShouldUndoBTConnectionAfterStopping() throws Exception {
        PMVehicle vehicle = new PMVehicle("VEH123", PMVState.UnderWay, new GeographicPoint(41.40338F, 2.17403F));
        handler.setVehicle(vehicle);
        JourneyService journey = new JourneyService(LocalDateTime.now().minusMinutes(10));
        handler.setJourney(journey);

        StationID endStation = new StationID("ST123");
        GeographicPoint endLocation = new GeographicPoint(41.40438F, 2.17503F);

        doNothing().when(arduinoMock).stopDriving();
        doNothing().when(arduinoMock).undoBTconnection();
        when(serverMock.stopPairing(any(), any(), any(), any(), any(), anyFloat(), anyFloat(), anyInt(), any())).thenReturn(true);

        // Ejecutar el método
        handler.stopDriving(endStation, endLocation);

        // Verificar que se llama undoBTconnection
        verify(arduinoMock).undoBTconnection();

        // Validar que el estado del vehículo y el trayecto es correcto
        assertEquals(PMVState.Available, vehicle.getState());
        assertFalse(journey.isInProgress());
    }

    @Test
    void stopDriving_ShouldUndoBTConnectionOnException() throws Exception {
        PMVehicle vehicle = new PMVehicle("VEH123", PMVState.UnderWay, new GeographicPoint(41.40338F, 2.17403F));
        handler.setVehicle(vehicle);
        JourneyService journey = new JourneyService(LocalDateTime.now().minusMinutes(10));
        handler.setJourney(journey);

        StationID endStation = new StationID("ST123");
        GeographicPoint endLocation = new GeographicPoint(41.40438F, 2.17503F);

        // Simular que stopDriving lanza una excepción
        doThrow(new PMVPhisicalException()).when(arduinoMock).stopDriving();
        doNothing().when(arduinoMock).undoBTconnection();

        // Ejecutar el método y capturar la excepción
        assertThrows(PMVPhisicalException.class, () -> handler.stopDriving(endStation, endLocation));

        // Verificar que se llamó undoBTconnection incluso después del fallo
        verify(arduinoMock).undoBTconnection();
    }
}
