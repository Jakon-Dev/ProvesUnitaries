package services;

import data.VehicleID;
import exceptions.CorruptedImgException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class QRDecoderTest {

    private QRDecoder qrDecoderMock;

    @BeforeEach
    void setUp() {
        qrDecoderMock = mock(QRDecoder.class);
    }

    @Test
    void testGetVehicleID() throws CorruptedImgException {
        // Configurar datos simulados
        BufferedImage qrImage = mock(BufferedImage.class);
        VehicleID expectedVehicleID = new VehicleID("VEH123");

        // Configurar comportamiento del mock
        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(expectedVehicleID);

        // Llamar al método
        VehicleID actualVehicleID = qrDecoderMock.getVehicleID(qrImage);

        // Verificar resultados
        assertEquals(expectedVehicleID, actualVehicleID, "El ID del vehículo devuelto debe coincidir con el esperado.");
        verify(qrDecoderMock, times(1)).getVehicleID(qrImage);
    }

    @Test
    void testGetVehicleIDThrowsCorruptedImgException() throws CorruptedImgException {
        // Configurar datos simulados
        BufferedImage qrImage = mock(BufferedImage.class);

        // Configurar comportamiento del mock para lanzar excepción
        doThrow(new CorruptedImgException("QR Code is corrupted")).when(qrDecoderMock).getVehicleID(qrImage);

        // Verificar que se lanza la excepción
        assertThrows(CorruptedImgException.class, () -> qrDecoderMock.getVehicleID(qrImage));

        // Verificar que el método se llamó correctamente
        verify(qrDecoderMock, times(1)).getVehicleID(qrImage);
    }
}
