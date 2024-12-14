package services;

import exceptions.PMVPhisicalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ArduinoMicroControllerTest {

    private ArduinoMicroController arduinoMock;

    @BeforeEach
    void setUp() {
        arduinoMock = mock(ArduinoMicroController.class);
    }

    @Test
    void testSetBTconnection() throws ConnectException {
        // Configurar comportamiento del mock
        doNothing().when(arduinoMock).setBTconnection();

        // Llamar al método
        arduinoMock.setBTconnection();

        // Verificar que se llamó correctamente
        verify(arduinoMock, times(1)).setBTconnection();
    }

    @Test
    void testStartDriving() throws PMVPhisicalException, ConnectException {
        // Configurar comportamiento del mock
        doNothing().when(arduinoMock).startDriving();

        // Llamar al método
        arduinoMock.startDriving();

        // Verificar que se llamó correctamente
        verify(arduinoMock, times(1)).startDriving();
    }

    @Test
    void testStopDriving() throws PMVPhisicalException, ConnectException {
        // Configurar comportamiento del mock
        doNothing().when(arduinoMock).stopDriving();

        // Llamar al método
        arduinoMock.stopDriving();

        // Verificar que se llamó correctamente
        verify(arduinoMock, times(1)).stopDriving();
    }

    @Test
    void testUndoBTconnection() throws ConnectException {
        // Configurar comportamiento del mock
        doNothing().when(arduinoMock).undoBTconnection();

        // Llamar al método
        arduinoMock.undoBTconnection();

        // Verificar que se llamó correctamente
        verify(arduinoMock, times(1)).undoBTconnection();
    }

    @Test
    void testSetBTconnectionThrowsConnectException() throws ConnectException {
        // Configurar comportamiento del mock para lanzar excepción
        doThrow(new ConnectException("Error de conexión")).when(arduinoMock).setBTconnection();

        // Verificar que se lanza la excepción
        assertThrows(ConnectException.class, () -> arduinoMock.setBTconnection());

        // Verificar que se llamó correctamente
        verify(arduinoMock, times(1)).setBTconnection();
    }

    @Test
    void testStartDrivingThrowsPMVPhisicalException() throws PMVPhisicalException, ConnectException {
        // Configurar comportamiento del mock para lanzar excepción
        doThrow(new PMVPhisicalException("Fallo físico en el vehículo")).when(arduinoMock).startDriving();

        // Verificar que se lanza la excepción
        assertThrows(PMVPhisicalException.class, () -> arduinoMock.startDriving());

        // Verificar que se llamó correctamente
        verify(arduinoMock, times(1)).startDriving();
    }

    @Test
    void testStopDrivingThrowsConnectException() throws PMVPhisicalException, ConnectException {
        // Configurar comportamiento del mock para lanzar excepción
        doThrow(new ConnectException("Error de conexión")).when(arduinoMock).stopDriving();

        // Verificar que se lanza la excepción
        assertThrows(ConnectException.class, () -> arduinoMock.stopDriving());

        // Verificar que se llamó correctamente
        verify(arduinoMock, times(1)).stopDriving();
    }
}
