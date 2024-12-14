package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UnbondedBTSignalTest {

    private UnbondedBTSignal btSignalMock;

    @BeforeEach
    void setUp() {
        btSignalMock = mock(UnbondedBTSignal.class);
    }

    @Test
    void testBTbroadcast() throws ConnectException {
        // Configurar comportamiento del mock
        doNothing().when(btSignalMock).BTbroadcast();

        // Llamar al método
        btSignalMock.BTbroadcast();

        // Verificar que el método fue llamado correctamente
        verify(btSignalMock, times(1)).BTbroadcast();
    }

    @Test
    void testBTbroadcastThrowsConnectException() throws ConnectException {
        // Configurar comportamiento del mock para lanzar excepción
        doThrow(new ConnectException("Error al emitir señal Bluetooth"))
                .when(btSignalMock).BTbroadcast();

        // Verificar que se lanza la excepción
        assertThrows(ConnectException.class, () -> btSignalMock.BTbroadcast());

        // Verificar que el método fue llamado correctamente
        verify(btSignalMock, times(1)).BTbroadcast();
    }
}
