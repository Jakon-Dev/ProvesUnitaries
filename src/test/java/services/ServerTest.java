package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import micromobility.payment.ServiceID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ServerTest {

    private Server serverMock;

    @BeforeEach
    void setUp() {
        serverMock = mock(Server.class);
    }

    @Test
    void testCheckPMVAvail() throws Exception {
        VehicleID vehicleID = new VehicleID("V12345");
        doNothing().when(serverMock).checkPMVAvail(vehicleID);
        serverMock.checkPMVAvail(vehicleID);
        verify(serverMock, times(1)).checkPMVAvail(vehicleID);
    }

    @Test
    void testRegisterPayment() throws Exception {
        ServiceID serviceID = new ServiceID("S12345");
        UserAccount user = new UserAccount("user123");
        BigDecimal amount = BigDecimal.valueOf(100.0);
        doNothing().when(serverMock).registerPayment(serviceID, user, amount, 'W');
        serverMock.registerPayment(serviceID, user, amount, 'W');
        verify(serverMock, times(1)).registerPayment(serviceID, user, amount, 'W');
    }

    @Test
    void testRegisterPairing() throws Exception {
        UserAccount user = new UserAccount("user123");
        VehicleID vehicle = new VehicleID("V12345");
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);
        LocalDateTime date = LocalDateTime.now();

        doNothing().when(serverMock).registerPairing(user, vehicle, station, location, date);
        serverMock.registerPairing(user, vehicle, station, location, date);
        verify(serverMock, times(1)).registerPairing(user, vehicle, station, location, date);
    }

    @Test
    void testStopPairing() throws Exception {
        UserAccount user = new UserAccount("user123");
        VehicleID vehicle = new VehicleID("V12345");
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);
        LocalDateTime date = LocalDateTime.now();
        float avgSpeed = 30.5f;
        float distance = 15.0f;
        int duration = 20;
        BigDecimal importValue = BigDecimal.valueOf(50.0);

        when(serverMock.stopPairing(user, vehicle, station, location, date, avgSpeed, distance, duration, importValue))
                .thenReturn(true);
        boolean result = serverMock.stopPairing(user, vehicle, station, location, date, avgSpeed, distance, duration, importValue);
        verify(serverMock, times(1)).stopPairing(user, vehicle, station, location, date, avgSpeed, distance, duration, importValue);
        assertTrue(result, "El método stopPairing debería devolver true.");
    }

    @Test
    void testRegisterLocation() throws Exception {
        VehicleID vehicle = new VehicleID("V12345");
        StationID station = new StationID("ST123");

        doNothing().when(serverMock).registerLocation(vehicle, station);
        serverMock.registerLocation(vehicle, station);
        verify(serverMock, times(1)).registerLocation(vehicle, station);
    }
}

